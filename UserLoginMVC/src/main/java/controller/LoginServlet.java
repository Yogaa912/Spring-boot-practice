package controller;
import model.User;
import model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User user = new User(username, password);
			UserService service = new UserService();
			
			if(service.login(user)) {
				request.setAttribute("username", username);
				request.getRequestDispatcher("success.jsp").forward(request, response);
			} else {
				response.sendRedirect("login.jsp");
			}
		}
}