package com.springPractice.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 访问 http://主机名.端口号/context-path/Controller 的 URI/方法的 URI
 * http://localhost:80/boot/user/list
 */
@Controller
public class MainCotroller {
	/**
	 * 返回 String 文本类型时候会寻找模板文件, 位置在resources/templates
	 */
	
	@GetMapping("/index")
	public String index(Model map) {
		return "index";
	}
}