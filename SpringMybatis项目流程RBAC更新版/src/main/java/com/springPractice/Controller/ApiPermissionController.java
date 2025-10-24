package com.springPractice.Controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springPractice.Entity.Account;
import com.springPractice.Entity.Permission;
import com.springPractice.Entity.PermissionChangeDto;
import com.springPractice.Entity.PermissionCreateDto;
import com.springPractice.service.PermissionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permission") // 建议给API接口加上统一前缀 /v1/
public class ApiPermissionController {
	// 注入 permissionSrv
	@Autowired
	PermissionService permissionSrv;
	
	private static final Logger log = LoggerFactory.getLogger(ApiPermissionController.class);
	
	@GetMapping("/list")
	public PageInfo<Permission> list(
			@RequestParam(defaultValue = "1") int pageNum) {
	    // 最好一次性返回所有的数据
		PageInfo<Permission> pageInfo = permissionSrv.findByPage(pageNum, 100);
		// ↑ 把后面传过来的list展示在前端
		// 把三个页面需要的 apiController 分开
		return pageInfo;
	}
	
	@PostMapping("permissionModify")
	@ResponseBody
	public ResponseEntity<?> permissionModify(
			@Valid @ModelAttribute PermissionChangeDto permissionDto, HttpSession session, 
            BindingResult bindingResult) {
		// 进行基础的字段校验
		if (bindingResult.hasErrors()) {
	    	System.out.println("【格式校验】对象 id: " + permissionDto.getId());
	        // 如果校验失败，从 BindingResult 中获取第一条错误信息返回给前端
	        // FieldError fieldError = bindingResult.getFieldError();
	        // 或者全部输出
	        String errorMsg = bindingResult.getFieldErrors().stream()
	        		.map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(Map.of("error",errorMsg));
	    }
		
		// 从 session 中获取当前操作者的信息
		Account operator = (Account)session.getAttribute("account");
		System.out.println("operator.getRole是 " +  operator.getRole());
		// 将字面量放在前面调用 equals() 可以避免空指针异常 (NullPointerException)
		if (!"admin".equals(operator.getRole())) {
			return ResponseEntity.badRequest().body(Map.of("error","普通用户没有权限修改"));
		}
		
		// Spring 已经帮你把所有数据都填充好了
		System.out.println("权限id: " + permissionDto.getId());
		System.out.println("权限uri: " + permissionDto.getUni());
        System.out.println("权限名称: " + permissionDto.getName());
        System.out.println("c 权限: " + permissionDto.getC());
        System.out.println("r 权限: " + permissionDto.getR());
        System.out.println("u 权限: " + permissionDto.getU());
        System.out.println("d 权限: " + permissionDto.getD());
        // 控制台会打印

        // ... 调用 Service 层去处理业务逻辑 ...return ResponseEntity.status(HttpStatus.CREATED).build();
		try {
			// 调用 service
			// 接下来要把发送过来的内容更新到数据库里
			boolean success = permissionSrv.changePermission(permissionDto, operator);
			if(success) {
				return ResponseEntity.ok(Map.of("message", "权限修改成功"));
			} else {
				return ResponseEntity.badRequest().body(Map.of("error", "操作失败"));
			}
		} catch(Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error", "服务器错误, 权限修改失败"));
		}
	}
	
	@PostMapping("permissionCreate")
	@ResponseBody
	public ResponseEntity<?> permissionCreate(
			@Valid @ModelAttribute PermissionCreateDto permissionDto, HttpSession session, 
            BindingResult bindingResult) {
		// 进行基础的字段校验
		if (bindingResult.hasErrors()) {
	    	System.out.println("【格式校验】对象 name: " + permissionDto.getName());
	        String errorMsg = bindingResult.getFieldErrors().stream()
	        		.map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(Map.of("error",errorMsg));
	    }
		
		// 从 session 中获取当前操作者的信息
		Account operator = (Account)session.getAttribute("account");
		System.out.println("operator.getRole是 " +  operator.getRole());
		// 将字面量放在前面调用 equals() 可以避免空指针异常 (NullPointerException)
		if (!"admin".equals(operator.getRole())) {
			return ResponseEntity.badRequest().body(Map.of("error","普通用户没有权限修改"));
		}
		
		// Spring 已经帮你把所有数据都填充好了
		System.out.println("权限uri: " + permissionDto.getUni());
        System.out.println("权限名称: " + permissionDto.getName());
        System.out.println("c 权限: " + permissionDto.getC());
        System.out.println("r 权限: " + permissionDto.getR());
        System.out.println("u 权限: " + permissionDto.getU());
        System.out.println("d 权限: " + permissionDto.getD());
        // 控制台会打印

		try {
			Permission p = permissionSrv.createPermission(permissionDto, operator);
			log.info("用户 '{}' 成功创建了权限 ID: {}", operator, p.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true, "message", "权限创建成功"));
		} catch(Exception e) {
			// 捕获 Service 层可能抛出的业务逻辑异常 (比如名称/URI 重复)
			log.warn("创建权限失败 (业务逻辑错误): {}", e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
	
	@PostMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<?> deletePermission(@PathVariable Integer id, HttpSession session) {
		// 需要标记一下是否真的删除成功, 需要一个状态类, 之前自己写了一个RespStat 类型, 此处也可以直接使用 Map
		
		// 从 session 中获取当前操作者的信息
		Account operator = (Account)session.getAttribute("account");
		if (!"admin".equals(operator.getRole())) {
			return ResponseEntity.badRequest().body(Map.of("error","用户没有权限"));
		}
		try {
			permissionSrv.deleteById(id);
			System.out.println("用户" + operator.getLoginName() + "删除了账号" + id);
			return ResponseEntity.ok(Map.of("success", true, "message", "删除操作成功"));
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
		}
	}
}