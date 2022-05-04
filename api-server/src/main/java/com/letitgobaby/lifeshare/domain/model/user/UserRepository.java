package com.letitgobaby.lifeshare.domain.model.user;

import javax.persistence.EntityManager;

import com.letitgobaby.lifeshare.system.persistence.HibernateQueryDSLSupport;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends HibernateQueryDSLSupport<User> {

  private final JPAQueryFactory queryFactory;

  public UserRepository(EntityManager entityManager, JPAQueryFactory queryFactory) {
    super(entityManager);
    this.queryFactory = queryFactory;
  }

  public User findByUserName(String userName) {
    return this.queryFactory.selectFrom(QUser.user)
      .where(QUser.user.userName.eq(userName))
      .fetchOne();
  }

}
