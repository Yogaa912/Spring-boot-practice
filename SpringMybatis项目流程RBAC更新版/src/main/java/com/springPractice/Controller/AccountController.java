package com.springPractice.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.springPractice.Entity.Account;
import com.springPractice.Entity.Config;
import com.springPractice.Entity.RegisterData;
import com.springPractice.service.AccountService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController implements ServletContextAware{
	private ServletContext servletContext;
	@Autowired
	AccountService accountSrv;
	@Autowired
	Config config;
	
	@GetMapping("login")
	public String login(Model model) {
		model.addAttribute("config",config);
		return "account/login";
	}
	@GetMapping("register")
	public String register() {
		return "account/register";
	}
	/**
	 * 用户登录的异步校验
	 * @param loginName
	 * @param password
	 * @return
	 */
	@PostMapping("validateAccount")
	@ResponseBody
	public String validateAccount(String loginName, String password, HttpServletRequest request) {
		Account account = accountSrv.findByLoginNameAndPassword(loginName, password);
		if(account == null) {
			return "登录失败";
		} else {
			// 写到 Session 里面
			request.getSession().setAttribute("account", account);
			return "success"; 
		}
		
	}
	/**
	@PostMapping("validateRegister")
	@ResponseBody
	public String validateRegister(String loginName, String password) {
		// 检查密码是否合法...
		
		// 检查账号是否存在
		Account account = accountSrv.findByLoginName(loginName);
		if(account != null) {
			// 已经存在账户
			return "failed";
		} else {
			// 注册登陆过程里, 先注册后面再次登录, 还是在这里一口气登录上呢
			// 写入数据库当前信息
			accountSrv.createAccount(loginName, password);
			return "success"; 
		}
	}
	*/
	
	@PostMapping("/handleRegister")
	@ResponseBody
	public Map<String, Object> handleRegister(@Valid RegisterData registerData, BindingResult bindingResult, HttpSession session) {
	    Map<String, Object> response = new HashMap<>();

	    // 1. 【格式校验】首先检查 Validation 的结果
	    if (bindingResult.hasErrors()) {
	    	System.out.println("【格式校验】 对象: " + registerData.getLoginName());
	        // 如果校验失败，从 BindingResult 中获取第一条错误信息返回给前端
	        FieldError fieldError = bindingResult.getFieldError();
	        response.put("success", false);
	        response.put("message", fieldError.getDefaultMessage());
	        return response;
	    }

	    // 2. 【业务校验和处理】调用 Service 层完成后续工作
	    try {
	        // 让 Service 层处理“用户名是否存在”以及“创建账户”的逻辑
	        Account newAccount = accountSrv.createAccount(registerData);
	        
	        // 注册成功后，直接将新用户信息存入Session，实现“注册并登录”
	        session.setAttribute("account", newAccount);
	        System.out.println("【业务校验和处理】成功");
	        response.put("success", true);
	        response.put("message", "注册成功！");

	    } catch (Exception e) { // 比如可以自定义一个 UsernameExistsException
	        // 捕获 Service 层可能抛出的业务异常（比如用户名已存在）
	        response.put("success", false);
	        response.put("message", e.getMessage());
	    }
	    return response;
	}
	
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		request.getSession().removeAttribute("account"); // 删除关于account的记录
		return "redirect:/index";
	}
	
	@GetMapping("/list")
	public String list() {
		return "account/list";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "account/profile";
	}
	
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> deleteAccount(@PathVariable Integer id, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		// 需要标记一下是否真的删除成功, 需要一个状态类, 之前自己写了一个RespStat 类型, 此处也可以直接使用 Map
		
		// 1. 从 session 中获取当前操作者的信息
		Account operator = (Account)session.getAttribute("account");
		if (!"admin".equals(operator.getRole())) {
			response.put("success", false);
			response.put("message", "用户没有权限");
			return response;
		}
		try {
			accountSrv.deleteById(id);
			response.put("success", true);
			System.out.println("用户" + operator.getLoginName() + "删除了账号" + id);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		// 这里的 try catch 目的是为了构造 response
		return response;
	}
	
	@Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
	
	@PostMapping("/uploadAvatar")
	public String uploadAvatar(@RequestParam("avatar") MultipartFile file, 
            				   @RequestParam("accountId") Integer accountId) { // @RequestParam是什么用处, accountId从哪里传送来的
		System.out.println("accountId is " + accountId);
		if(file.isEmpty()) {
			return "redirect:/error";
		}
        String urlPath = "";
		try {
			urlPath = accountSrv.saveAvatar(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 5. 将头像的URL路径保存到数据库中
        accountSrv.updateAvatarUrl(accountId, urlPath);
		// 成功后跳转回账户列表页面
        return "redirect:/index";
	}
}
