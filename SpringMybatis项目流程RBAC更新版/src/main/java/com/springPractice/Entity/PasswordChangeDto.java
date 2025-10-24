package com.springPractice.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeDto {
	// 这个字段从后端 Session 获取的, 通常在 Service 层验证它的有效性
	private Integer accountId;
	
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    
    @NotEmpty(message = "新密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须在8到20位之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "密码必须包含大小写字母和数字")
    private String newPassword;

    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;
}