package org.eayrad.api.jsf.utils;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class EasyRADExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	public EasyRADExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = parent.getExceptionHandler();
		result = new EasyRADExceptionHandler(result);
		return result;
	}

}
