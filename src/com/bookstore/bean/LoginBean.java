package com.bookstore.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bookstore.dao.UserDao;
import com.bookstore.model.User;

@ManagedBean
@ViewScoped
public class LoginBean {

	private User user = new User();
	
	public User getUser() {
		return user;
	}

	public String execute() {
		System.out.println("Fazendo login do usuario: " + this.user.getEmail());
		
		FacesContext context = FacesContext.getCurrentInstance();
		boolean existUser = new UserDao().exist(this.user);
		
		if(existUser) {
			context.getExternalContext().getSessionMap().put("userLogged", this.user);
			
			return "book?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Incorrect email/password."));
		
		return "login?faces-redirect=true";
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("userLogged");
		
		return "login?faces-redirect=true";
	}
}
