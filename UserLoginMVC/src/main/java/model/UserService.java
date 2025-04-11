package model;
import dao.UserDAO;

public class UserService {
	private UserDAO userDAO = new UserDAO();
	public boolean login(User user) {
		return userDAO.validate(user);
	}
}