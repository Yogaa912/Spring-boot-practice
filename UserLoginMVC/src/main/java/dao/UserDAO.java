package dao;
import model.User;

public class UserDAO {
	public boolean validate(User user) {
		// 数据库用户名密码校验
		return "admin".equals(user.getUsername()) && "123456".equals(user.getPassword());
	}
}