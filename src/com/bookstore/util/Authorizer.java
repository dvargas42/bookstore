package com.bookstore.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.bookstore.model.User;

public class Authorizer implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("Fase: " + event.getPhaseId());
		
		FacesContext context = event.getFacesContext();
		String pageName = context.getViewRoot().getViewId();

		System.out.println(pageName);

		if ("/login.xhtml".equals(pageName)) {
			return;
		}
		
		User loggedUser = (User) context.getExternalContext().getSessionMap().get("userLogged");
		
		if (loggedUser != null) {
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
