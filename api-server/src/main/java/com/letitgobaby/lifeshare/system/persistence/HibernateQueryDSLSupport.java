package com.letitgobaby.lifeshare.system.persistence;

import javax.persistence.EntityManager;

public abstract class HibernateQueryDSLSupport<T> {
  
  private EntityManager entityManager;

  public HibernateQueryDSLSupport(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public T save(T object) {
    entityManager.persist(object);
    entityManager.flush();
    return object;
  }

}
