package com.letitgobaby.lifeshare.domain.model.user;

import java.util.List;

import com.letitgobaby.lifeshare.system.persistence.BaseQueryDSLSupport;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserQueryDslRepositoryImpl implements BaseQueryDSLSupport<User> {
  
  private final JPAQueryFactory queryFactory;

  UserQueryDslRepositoryImpl(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  @Override
  public List<User> findAll() {
    return this.queryFactory.selectFrom(QUser.user).fetch();
  }

  public User findByUserName(String userName) {
    return this.queryFactory.selectFrom(QUser.user)
      .where(QUser.user.userName.eq(userName))
      .fetchOne();
  }

}
