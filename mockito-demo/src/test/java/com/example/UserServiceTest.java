package com.example;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	
	@Test
	void testGetUserName() {
		// 初始化 Mockito，通过reflection自动化变量
		MockitoAnnotations.openMocks(this);
		
		// Mock getUserName 方法的返回值，这一步实现的就是之前手动写好的拦截调用然后返回
		when(userRepository.getUserName(1)).thenReturn("Mocked-User-1");
		
		// 调用 Mock 对象的方法
		String name = userRepository.getUserName(1);
		
		// 断言
		assertEquals("Mocked-User-1", name);
	}
}
