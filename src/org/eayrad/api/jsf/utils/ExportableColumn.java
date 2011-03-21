package org.eayrad.api.jsf.utils;

public class ExportableColumn {

	private String label;
	private String getMethod;
	private String setMethod;
	private Class type;

	public ExportableColumn() {
	}

	public ExportableColumn(String label, String getMethod) {
		this.label = label;
		this.getMethod = getMethod;
	}

	public ExportableColumn(String label, String getMethod, String setMethod, Class type) {
		this.label = label;
		this.getMethod = getMethod;
		this.setMethod = setMethod;
		this.type = type;
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSetMethod() {
		return setMethod;
	}

	public void setSetMethod(String setMethod) {
		this.setMethod = setMethod;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

}
