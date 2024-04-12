package com.bookstore.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> {

	private final Class<T> clazz;
	
	public DAO(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void add(T t) {
		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();
		// abre transacao
		em.getTransaction().begin();
		// persiste o objeto
		em.persist(t);
		// commita a transacao
		em.getTransaction().commit();
		// fecha a entity manager
		em.close();
	}
	
	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(t));
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeAll(T t) {
		
	}
	
	public void update(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<T> listAll() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));
		
		List<T> list = em.createQuery(query).getResultList();
		
		em.close();
		return list;
	}
	
	public T getById(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instance = em.find(clazz, id);
		em.close();
		
		return instance;
	}
	
	public int countAll() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from book n")
				.getSingleResult();
		em.close();
		
		return (int) result;
	}
	
	public List<T> listPagedAll(int firstResult, int maxResults) {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));
		
		List<T> list = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
		
		em.close();
		return list;
		
	}
}
