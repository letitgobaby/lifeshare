package com.letitgobaby.lifeshare.system.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class HibernateQueryDSLSupport<T> implements JpaRepository<T, Long> {
  
  private final JPAQueryFactory queryFactory;

  public HibernateQueryDSLSupport(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public JPAQueryFactory query() {
    return this.queryFactory;
  }

}
