package com.letitgobaby.lifeshare.domain.model.user;

import com.letitgobaby.lifeshare.system.persistence.HibernateQueryDSLSupport;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

@Repository
public abstract class UserRepository extends HibernateQueryDSLSupport<User> {

  public UserRepository(JPAQueryFactory queryFactory) {
    super(queryFactory);
  }

  public User findByUserName(String userName) {
    return query().selectFrom(QUser.user)
      .where(QUser.user.userName.eq(userName))
      .fetchOne();
  }

}
