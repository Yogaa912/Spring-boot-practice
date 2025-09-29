package com.springPractice.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterData {

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度必须在4到20位之间")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须在8到20位之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "密码必须包含大小写字母和数字")
    private String password;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}