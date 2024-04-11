package com.bookstore.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.bookstore.dao.DAO;
import com.bookstore.model.Author;

@ManagedBean
@ViewScoped
public class AuthorBean {
	
	private Author author = new Author();
	
	private Integer authorId;
	
	public Author getAuthor() {
		return author;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	public void getAuthorById() {
		this.author = new DAO<Author>(Author.class).getById(authorId);
	}

	public List<Author> getAuthors() {
		List<Author> authors = new DAO<Author>(Author.class).listAll();
		return authors;
	}
	
	public String record() {
		System.out.println(this.author.getId());
		if(this.author.getId() == null) {
			System.out.println("Teste");
			new DAO<Author>(Author.class).add(author);
			
		} else {
			new DAO<Author>(Author.class).update(author);
		}
		
		this.author = new Author();
		
		return "book?faces-redirect=true";
	}
	
	public void remove(Author author) {
		new DAO<Author>(Author.class).remove(author);
	}
	
	public void load(Author author) {
		this.author = author;
	}
}
