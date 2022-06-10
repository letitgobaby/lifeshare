package com.letitgobaby.lifeshare.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.letitgobaby.lifeshare.domain.model.user.User;
import com.letitgobaby.lifeshare.domain.model.user.UserRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

  @Mock
  UserRepository userRepository;

	@BeforeAll
	public void setUp() { }

  @Test
  public void findByUserName_TEST() {
		
		// given
		String userName = "test";
		String nickName = "nick";
		String passwd = "password";
		String phone = "010-1234-5678";
		String email = "test@test.com";
		User newUser = new User().regist(userName, nickName, passwd, phone, email);

    when(userRepository.findByUserName(userName)).thenReturn(newUser);

		// when
		User user = userRepository.findByUserName(userName);

		// then
		assertEquals(user.getUserName(), userName);
		assertEquals(user.getPhoneNumber(), phone);
		assertEquals(user.getEmail(), email);
  }
  
}
