package com.springPractice.Controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springPractice.Entity.Account;
import com.springPractice.Entity.PasswordChangeDto;
import com.springPractice.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account") // 建议给API接口加上统一前缀
public class ApiAccountController {
	// 注入 accountSrv
	@Autowired
	AccountService accountSrv;
	
	@GetMapping("/listAccounts")
	public PageInfo<Account> list(
			@RequestParam(defaultValue = "1") int pageNum) {
	    // 最好一次性返回所有的数据
		PageInfo<Account> pageInfo = accountSrv.findByPage(pageNum, 100);
		// ↑ 把后面传过来的list展示在前端
		// 把三个页面需要的 apiController 分开
		return pageInfo;
	}
	
	@PostMapping("updatePassword")
	@ResponseBody
	public ResponseEntity<?> updatePassword(
			@Valid @ModelAttribute PasswordChangeDto passwordChangeDto, 
			BindingResult bindingResult) {
		// 进行基础的字段校验
		if (bindingResult.hasErrors()) {
	    	System.out.println("【格式校验】 对象: " + passwordChangeDto.getAccountId());
	        // 如果校验失败，从 BindingResult 中获取第一条错误信息返回给前端
	        // FieldError fieldError = bindingResult.getFieldError();
	        // 或者全部输出
	        String errorMsg = bindingResult.getFieldErrors().stream()
	        		.map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(Map.of("error",errorMsg));
	    }
		
		// 后端校验
		String newPassword = passwordChangeDto.getNewPassword();
		if(newPassword == null || !newPassword.equals(passwordChangeDto.getConfirmPassword())) {
			return ResponseEntity.badRequest().body(Map.of("error", "输入的新密码不一致"));
			// 返回的 ResponseEntity 交给前端如何处理 往往这个返回给前端的包里是 map 形式的一一对应, 比如有 success, error 和 message
		}
		try {
			// 调用 service
			boolean success = accountSrv.changePassword(passwordChangeDto.getAccountId(), passwordChangeDto.getOldPassword(), newPassword);
			if(success) {
				return ResponseEntity.ok(Map.of("success", true, "message", "密码修改成功"));
			} else {
				return ResponseEntity.badRequest().body(Map.of("error", "旧密码不正确"));
			}
		} catch(Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error", "服务器错误, 密码修改失败"));
		}
		
	}
}