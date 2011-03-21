/**
 * To use this function, add the following code inside the section <application/> of your faces-config.xml project file:
 * <system-event-listener>
			<system-event-listener-class>org.eayrad.api.jsf.view.LabelProvider</system-event-listener-class>
			<system-event-class>javax.faces.event.PreValidateEvent</system-event-class>
			<source-class>javax.faces.component.html.HtmlOutputLabel</source-class>
		</system-event-listener>
 */
package org.eayrad.api.jsf.view;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreValidateEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

@ListenerFor(sourceClass=HtmlOutputLabel.class, systemEventClass=PreValidateEvent.class)
public class LabelProvider implements SystemEventListener {

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		HtmlOutputLabel label = (HtmlOutputLabel) event.getSource();
		UIComponent target = label.findComponent(label.getFor());
		String sLabel = label.getValue().toString().replace(":", "").replace(" ", "");
		if (target != null && target.getAttributes().get("label") == null)
			target.getAttributes().put("label", sLabel);
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return true;
	}



}
