package com.example;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ByteBuddyMockitoExampleTest {

    // 定义一个接口
    interface Service {
        String getData();
    }

    @Test
    public void testMockUsingByteBuddy() {
        // 使用 Mockito 创建一个 Mock 对象 = 就是将要 mock 的类型
        Service mockService = mock(Service.class);
        
        // 配置 Mock 行为，方法 = mock 对象定义的方法
        when(mockService.getData()).thenReturn("Mocked Data");

        // 调用 Mock 方法，之前是要把 mock 对象传入其他的方法，这里直接测试
        String result = mockService.getData();

        // 验证结果
        assertEquals("Mocked Data", result);
    }
}