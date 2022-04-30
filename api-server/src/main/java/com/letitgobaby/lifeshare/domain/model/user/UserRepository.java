package com.letitgobaby.lifeshare.domain.model.user;

public interface UserRepository {
  
  User findById(Long id);

  User findByNickName(String nickName);

  User findByUserName(String userName);

  User findByEmail(String email);

  void regist(User user);

  // void update(User user);

  // void withdraw(User user);

}
