package com.bookstore.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import com.bookstore.model.Author;
import com.bookstore.model.Book;

public class SeedDB {
	
	public static void main(String[] args) {
		 EntityManager em = new JPAUtil().getEntityManager();
		 em.getTransaction().begin();
		 
		 Author assis = createAuthor("Machado de Assis");
		 Author amado = createAuthor("Jorge Amado");
		 Author coelho = createAuthor("Paulo Coelho");
		 
		 em.persist(assis);
		 em.persist(amado);
		 em.persist(coelho);
		 
		 Book casmurro = createBook("978-8-52-504464-8", 
				 "Dom Casmurro", "10/01/1899", 24.90, assis);
		 Book memorias = createBook("978-8-50-815415-9",
				 "Memorias Postumas de Bras Cubas", "01/01/1881", 19.90, assis);
		 Book quincas = createBook("978-8-50-804084-1", 
				 "Quincas Borba", "01/01/1891", 16.90, assis);
		 
		 em.persist(casmurro);
		 em.persist(memorias);
		 em.persist(quincas);
		 

		 Book alquemista = createBook("978-8-57-542758-3", "O Alquimista",
				 "01/01/1988", 19.90, coelho);
		 Book brida = createBook("978-8-50-567258-7", "Brida", "01/01/1990",
				 12.90, coelho);
		 Book valkirias = createBook("978-8-52-812458-8", "As Valkirias",
				 "01/01/1992", 29.90, coelho);
		 Book maao = createBook("978-8-51-892238-9", "O Diario de um Mago",
				 "01/01/1987", 9.90, coelho);
		 
		em.persist(alquemista);
		em.persist(brida);
		em.persist(valkirias);
		em.persist(maao);
		
		Book capitaes = createBook("978-8-50-831169-1", 
				"Capitaes da Areia", "01/01/1937", 6.90, amado);
		Book flor = createBook("978-8-53-592569-9",
				"Dona Flor e Seus Dois Maridos", "01/01/1966", 18.90, amado);

		em.persist(capitaes);
		em.persist(flor);
	 
		em.getTransaction().commit();
		em.close();
	}
	
	private static Author createAuthor(String name) {
		Author author = new Author();
		author.setName(name);
		return author;
	}
	
	private static Book createBook(String isbn, String title, String date,
			double price, Author author) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setLaunchDate(parseData(date));
		book.setPrice(price);
		book.addAuthor(author);
		
		return book;
	}
	
	@SuppressWarnings("unused")
	private static Calendar parseData(String date) {
		try {
			Date parseDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseDate);
			return calendar;
		} catch (ParseException e){
			throw new IllegalArgumentException(e);
		}
	}
}
