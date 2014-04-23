/**
 * 
 */
package org.agileworks.fastldr.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tajzivit
 * 
 */
public class Control {

	private Options options;
	private String charset;
	private File file;
	private String table;
	private boolean truncate;
	private String fieldsTerminatedBy;
	private String fieldsEnclosedBy;

	private List<Field> fields = new ArrayList<Field>();

	public void setOptions(Options options) {
		this.options = options;
	}

	public void addField(Field field) {
		fields.add(field);

	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean isTruncate() {
		return truncate;
	}

	public void setTruncate(boolean truncate) {
		this.truncate = truncate;
	}

	public Options getOptions() {
		return options;
	}

	public List<Field> getFields() {
		return fields;
	}

	public String getFieldsTerminatedBy() {
		return fieldsTerminatedBy;
	}

	public void setFieldsTerminatedBy(String fieldsTerminatedBy) {
		this.fieldsTerminatedBy = fieldsTerminatedBy;
	}

	public String getFieldsEnclosedBy() {
		return fieldsEnclosedBy;
	}

	public void setFieldsEnclosedBy(String fieldsEnclosedBy) {
		this.fieldsEnclosedBy = fieldsEnclosedBy;
	}

}
