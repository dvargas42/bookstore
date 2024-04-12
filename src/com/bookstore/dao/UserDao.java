package com.bookstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.bookstore.model.User;

public class UserDao {

	public boolean exist(User user) {
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<User> query = em.createQuery("select u from users u "
				+ "where u.email = :pEmail and u.password = :pPassword", User.class);
		
		query.setParameter("pEmail", user.getEmail());
		query.setParameter("pPassword", user.getPassword());
		
		User result;
		try {
			result = query.getSingleResult();
			
		} catch (Exception e) {
			result = null;
		}
		
		em.close();

		return result != null;
	}

}
