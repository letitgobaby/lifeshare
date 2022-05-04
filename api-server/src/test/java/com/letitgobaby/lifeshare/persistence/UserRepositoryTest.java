package com.letitgobaby.lifeshare.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.letitgobaby.lifeshare.domain.model.user.User;
import com.letitgobaby.lifeshare.domain.model.user.UserRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
public class UserRepositoryTest {

  // @Mock
  private UserRepository userRepository;

  @BeforeAll
  public void setUp() throws Exception {
    userRepository = mock(UserRepository.class);
  }

  @Test
  public void regist_user() {
		String userName = "test";
		String nickName = "nick";
		String passwd = "password";
		String phone = "010-1234-5678";
		String email = "test@test.com";

    when(userRepository.findByUserName(userName)).thenReturn(new User());
    
  }
  
	@Test
	public void user_저장_확인() {
		String userName = "test";
		String nickName = "nick";
		String passwd = "password";
		String phone = "010-1234-5678";
		String email = "test@test.com";
		User newUser = new User().regist(userName, nickName, passwd, phone, email);
		
    when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

    User savedUser = this.userRepository.save(newUser);

		assertEquals(savedUser.getUserName(), userName);
		assertEquals(savedUser.getPhoneNumber(), phone);
		assertEquals(savedUser.getEmail(), email);
	}

}
