package com.letitgobaby.lifeshare.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.letitgobaby.lifeshare.domain.model.user.User;
import com.letitgobaby.lifeshare.domain.model.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

  @Mock
  UserRepository userRepository;

  // @BeforeAll
  // public void setUp() throws Exception {
  //   // userRepository = new UserQueryDslRepositoryImpl()
  // }
  
	@Test
	public void user_저장_확인() {
		String userName = "test";
		String nickName = "nick";
		String passwd = "password";
		String phone = "010-1234-5678";
		String email = "test@test.com";
		User newUser = new User().regist(userName, nickName, passwd, phone, email);
		this.userRepository.save(newUser);

		List<User> result = this.userRepository.findAll();

		assertEquals(result.get(0).getUserName(), userName);
		assertEquals(result.get(0).getPhoneNumber(), phone);
		assertEquals(result.get(0).getEmail(), email);
	}

}
