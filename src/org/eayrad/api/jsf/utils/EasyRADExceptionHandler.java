package org.eayrad.api.jsf.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletResponse;

public class EasyRADExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public EasyRADExceptionHandler() {}

	public EasyRADExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable t = context.getException();
			System.out.println("\n\nExceção disparada: "+t.getClass());
			t.printStackTrace();
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			if (t instanceof FileNotFoundException) {
				try {
					response.sendError(404);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				FacesContext.getCurrentInstance().responseComplete();
			}
		}
		if (getWrapped() != null)
			getWrapped().handle();
	}

}
