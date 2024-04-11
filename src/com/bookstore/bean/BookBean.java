package com.bookstore.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.bookstore.dao.DAO;
import com.bookstore.model.Author;
import com.bookstore.model.Book;

@ManagedBean
@ViewScoped
public class BookBean {

	private Book book = new Book();
	private Integer authorId;

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Book getBook() {
		return book;
	}
	
	public List<Book> getBooks() {
		return new DAO<Book>(Book.class).listAll();
	}
	
	public List<Author> getAuthors() {
		List<Author> listAll = new DAO<Author>(Author.class).listAll();
		return listAll;
	}
	
	public List<Author> getBookAuthors() {
		return this.book.getAuthors();
	}
	
	public void recordAuthor() {
		Author author = new DAO<Author>(Author.class).getById(this.authorId);
		this.book.addAuthor(author);
	}

	public void record() {	
		if(book.getAuthors().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("author", new FacesMessage("Book should have a Author."));
			return;
		}
		
		if(this.book.getId() == null) {
			new DAO<Book>(Book.class).add(this.book);
			
		} else {
			new DAO<Book>(Book.class).update(this.book);
		}
		
		this.book = new Book();
	}
	
	public void remove(Book book) {
		new DAO<Book>(Book.class).remove(book);
	}
	
	public void removeBookAuthor(Author author) {
		this.book.removeAuthor(author);
	}
	
	public String clearForm() {
		return "book?faces-redirect=true";
		
	}
	
	public void load(Book book) {
		this.book = book;
	}
	
	public String formAuthor() {
		return "author?faces-redirect=true";
	}
	
	public void startWithOneDigit(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
}
