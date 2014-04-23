package org.agileworks.fastldr.args;

/**
 * Command line arguments passed over to FastLdr
 */
public class CmdArguments {
    /**
     * Unknown type of file to get processed - neither PAR nor CONTROL clause
     * found
     */
    public static final int TYPE_UNKNOWN = -1;
    /**
     * PAR file is the selected method. PAR contains list of CONTROL files PAR
     * file may contain one or more lines such as: CONTROL=YourFileName.ctl
     */
    public static final int TYPE_PAR = 0;
    /**
     * CONTROL file is the selected method. This file contains the real code to
     * get processed by fastldr.
     */
    public static final int TYPE_CONTROL = 1;

    private static final String ARG_PARFILE = "PARFILE";
    private static final String ARG_CONTROL = "CONTROL";
    private static final String ARG_LOG = "LOG";
    private static final String ARG_BAD = "BAD";
    private static final String ARG_DISCARD = "DISCARD";
    private static final String ARG_SKIP = "SKIP";
    private static final String ARG_LOAD = "LOAD";

    private UserID userID = null;
    private String parFile = null;
    private String controlFile = null;
    private String logFile = "";
    private String badFile = "";
    private String discardFile = "";
    private int skip = 0;
    private int load = -1;

    /**
     * Gets the type of file to get processed by fastldr. If both CONTROL and
     * PARFILE arguments are specified, PARFILE takes precedence.
     *
     * @return Type of the file to get processed by fastldr
     */
    public int getType() {
        if (parFile != null)
            return TYPE_PAR;
        else if (controlFile != null)
            return TYPE_CONTROL;
        else
            return TYPE_UNKNOWN;
    }

    /**
     * Sets a command line argument specified by a key
     *
     * @param key   Command line argument to set
     * @param value Value of the command line argument
     */
    public void setArg(String key, String value) {
        try {
            if (key.equals(ARG_PARFILE))
                this.parFile = value;
            else if (key.equals(ARG_CONTROL))
                this.controlFile = value;
            else if (key.equals(ARG_LOG))
                this.logFile = value;
            else if (key.equals(ARG_BAD))
                this.badFile = value;
            else if (key.equals(ARG_DISCARD))
                this.discardFile = value;
            else if (key.equals(ARG_SKIP))
                this.skip = Integer.parseInt(value);
            else if (key.equals(ARG_LOAD))
                this.load = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // skip possible number format exception = defaults are already set
        }
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public String getParFile() {
        return parFile;
    }

    public String getControlFile() {
        return controlFile;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getBadFile() {
        return badFile;
    }

    public String getDiscardFile() {
        return discardFile;
    }

    public int getSkip() {
        return skip;
    }

    public int getLoad() {
        return load;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Type: ");
        sb.append(this.getType());
        sb.append("\n");

        sb.append("Arguments: ");

        sb.append("USERID");
        sb.append("=");
        sb.append(userID);
        sb.append("; ");

        sb.append(ARG_PARFILE);
        sb.append("=");
        sb.append(parFile);
        sb.append("; ");

        sb.append(ARG_CONTROL);
        sb.append("=");
        sb.append(controlFile);
        sb.append("; ");

        sb.append(ARG_LOG);
        sb.append("=");
        sb.append(logFile);
        sb.append("; ");

        sb.append(ARG_BAD);
        sb.append("=");
        sb.append(badFile);
        sb.append("; ");

        sb.append(ARG_DISCARD);
        sb.append("=");
        sb.append(discardFile);
        sb.append("; ");

        sb.append(ARG_LOAD);
        sb.append("=");
        sb.append(load);
        sb.append("; ");

        sb.append(ARG_SKIP);
        sb.append("=");
        sb.append(skip);
        sb.append("; ");

        return sb.toString();
    }
}
