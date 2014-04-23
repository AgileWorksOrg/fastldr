package org.agileworks.fastldr;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jumpmind.symmetric.csv.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import org.agileworks.fastldr.domain.Control;

public class InterruptibleBatchPreparedStatementSetter extends AbstractInterruptibleBatchPreparedStatementSetter {
	private final CsvReader csvReader;
	private final Control control;
	private static final Logger LOG = LoggerFactory.getLogger(InterruptibleBatchPreparedStatementSetter.class);

	private int batchSize = 10000;
	
	public InterruptibleBatchPreparedStatementSetter(Control control, CsvReader csvReader) {
		this.control = control;
		this.csvReader = csvReader;
	}

	@Override
	protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
		try {
			boolean dataAvailable = csvReader.readRecord();
			if (dataAvailable) {
				for (int x = 1; x <= control.getFields().size(); x++) {
					ps.setObject(x, csvReader.get(x - 1));
					LOG.trace("Param: {}\tvalue:{}", x, csvReader.get(x - 1));
				}
			}

			return dataAvailable;
		} catch (IOException e) {
			throw new FastLdrException(e);
		}

	}

	@Override
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * Sets the batch size. It the size specified is <= 0, than the default value is used.
	 * @param size New size
	 */
	public void setBatchSize(int size) {
		if (size > 0)
			batchSize = size;
	}
}
