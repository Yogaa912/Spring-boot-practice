package com.example;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceMockTest {
    @Test
    void testGetUserNameWithMock() {
        // 1. 创建 Mock 对象
        UserRepository mockRepo = mock(UserRepository.class);

        // 2. 定义 Mock 行为：当 getUserById(1) 被调用时，返回 "MockUser-1"
        when(mockRepo.getUserById(1)).thenReturn("MockUser-1");

        // 3. 传入 Mock 对象，而不是真实的 UserRepository
        UserService userService = new UserService(mockRepo);

        // 4. 执行测试
        String name = userService.getUserName(1);

        // 5. 断言结果是否符合预期
        assertEquals("MockUser-1", name);

        // 6. 验证 mockRepo 是否被调用过一次
        verify(mockRepo, times(1)).getUserById(1);
    }
}
