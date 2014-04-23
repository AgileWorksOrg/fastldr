package org.agileworks.fastldr;

import org.agileworks.fastldr.domain.Control;
import org.agileworks.fastldr.domain.Field;
import org.agileworks.fastldr.args.CmdArguments;
import org.agileworks.fastldr.args.parser.CmdArgumentsParser;
import org.agileworks.fastldr.parser.FastLdr;
import org.jumpmind.symmetric.csv.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.*;
import java.util.Iterator;

/**
 * Main application class
 */
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private CmdArguments cmdArguments;
    private DataSource dataSource;

    private class LoaderResult {
        public int discarded;
        public int rejected;
        public int read;
        public int skipped;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: fastldr <parameter>");
            System.exit(1);
        }
        try {
            App app = new App();
            app.processCmdArguments(args[0]);
            app.doWork();
        } catch (FastLdrException e) {
            System.out.println("An error occurred:\n" + e.getMessage());
//			e.printStackTrace();
        }
    }

    private void doWork() throws FastLdrException {
        String files[];
        try {
            String inputFile = cmdArguments.getParFile();

            if (inputFile != null) { // PAR file attached
                files = ParFileParser.parse(new FileInputStream(inputFile));
                for (String file : files) {
                    processCtlFile(file);
                }
            } else { // no PAR file - we will process CONTROL file
                inputFile = cmdArguments.getControlFile();
                processCtlFile(inputFile);
            }

        } catch (FileNotFoundException e) {
            throw new FastLdrException(e);
        }
    }

    private void processCmdArguments(String args) throws FastLdrException {
        CmdArgumentsParser ap;
        ap = new CmdArgumentsParser(new StringReader(args));
        try {
            cmdArguments = ap.Start();
        } catch (org.agileworks.fastldr.args.parser.ParseException e) {
            throw new FastLdrException(e);
        }
    }

    private void processCtlFile(String file) throws FastLdrException {
        FastLdr fastldr;
        try {
            fastldr = new FastLdr(new FileInputStream(file));
            Control control = fastldr.Start();
            loadData(control);
        } catch (FileNotFoundException e) {
            throw new FastLdrException(e);
        } catch (org.agileworks.fastldr.parser.ParseException e) {
            throw new FastLdrException(e);
        }
    }

    private void loadData(final Control control) throws FastLdrException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        try {
            final CsvReader csvReader = gerCsvReader(control);

            final InterruptibleBatchPreparedStatementSetter statementSetter = new InterruptibleBatchPreparedStatementSetter(
                    control, csvReader);

            final LoaderResult result = new LoaderResult();

            // gets the amount of rows to load; preset to default if not specified
            statementSetter.setBatchSize(cmdArguments.getLoad());

            TransactionTemplate tt = new TransactionTemplate();
            tt.setTransactionManager(new DataSourceTransactionManager(getDataSource()));

            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    // truncate the table is the control file has this table-specific loading method specified
                    if (control.isTruncate())
                        jdbcTemplate.execute(createTruncateStatement(control));

                    int[] sqlResult = jdbcTemplate.batchUpdate(createInsertStatement(control), statementSetter);
                    for (int i : sqlResult) {
                        if (i == -2) {
                            result.read++;
                        } else if (i > 0) {
                            result.read += i;
                        }
                    }
                    LOG.info("Inserted {} rows.", result.read);
                }
            });
            writeResultLog(result);
        } catch (FileNotFoundException e) {
            throw new FastLdrException(e);
        } catch (IOException e) {
            throw new FastLdrException(e);
        }

    }

    private void writeResultLog(LoaderResult result) {
        PrintWriter pw;
        try {
            File file = new File(cmdArguments.getLogFile());
            file.delete();
            pw = new PrintWriter(file);
            pw.write("Total logical records skipped:          " + result.skipped + "\n");
            pw.write("Total logical records read:             " + result.read + "\n");
            pw.write("Total logical records rejected:         " + result.rejected + "\n");
            pw.write("Total logical records discarded:        " + result.discarded + "\n");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new FastLdrException(e);
        }
    }

    private CsvReader gerCsvReader(final Control control) throws FileNotFoundException, IOException {
        CsvReader csvReader = new CsvReader(new FileInputStream(control.getFile()), CharsetFactory.getCharset(control
                .getCharset()));

        if (control.getFieldsTerminatedBy() != null) {
            csvReader.setDelimiter(control.getFieldsTerminatedBy().charAt(0));
        }
        if (control.getFieldsEnclosedBy() != null) {
            csvReader.setRecordDelimiter(control.getFieldsEnclosedBy().charAt(0));
        }

        // skips as many lines as defined in OPTIONS; SKIP option inside the CTL file takes precedence
        int skip = control.getOptions().getSkip();
        if (skip < 0) // look up command line argument in case SKIP option is not specified in CTL file
            skip = cmdArguments.getSkip();
        if (skip < 0) // if the SKIP option was not set at all, set 0 as a default
            skip = 0;

        for (int i = 0; i < skip; i++)
            csvReader.readRecord();
        LOG.info("Skipped {} record" + (skip > 1 ? "(s)" : ""), csvReader.getCurrentRecord() + 1);

        return csvReader;
    }

    private String createInsertStatement(Control control) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(control.getTable()).append(" ");
        sb.append("(");
        for (Iterator<Field> iterator = control.getFields().iterator(); iterator.hasNext(); ) {
            Field field = iterator.next();
            sb.append(field.getName());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") VALUES(");
        for (Iterator<Field> iterator = control.getFields().iterator(); iterator.hasNext(); ) {
            Field field = iterator.next();
            if ("DATE".equalsIgnoreCase(field.getType())) {
                if (StringUtils.hasText(field.getProperty()))
                    sb.append("TO_DATE(?, '" + field.getProperty() + "')");
                else
                    sb.append("TO_DATE(?, 'DD-Mon-YY')");
            } else {
                sb.append("?");
            }
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String createTruncateStatement(Control control) {
        StringBuilder sb = new StringBuilder("TRUNCATE TABLE ");
        sb.append(control.getTable());
        return sb.toString();
    }

    private DataSource getDataSource() throws FastLdrException {
        if (null == dataSource) {
            dataSource = DataSourceFactory.getDataSource(cmdArguments);
        }
        return dataSource;
    }
}
