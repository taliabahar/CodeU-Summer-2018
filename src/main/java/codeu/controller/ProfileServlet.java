package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet class responsible for the profile page. */
public class ProfileServlet extends HttpServlet {
	private User user;
	 //private UserStore userStore;
	 
	 @Override
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		 request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		 String username = (String) request.getSession().getAttribute("user");
		 user = UserStore.getInstance().getUser(username);
		 
		 if(username != null){
			 String aboutMe = request.getParameter("about_me");
			 user.setAboutMe(aboutMe);
			 UserStore.getInstance().updateUser(user);
			 response.sendRedirect("/profile");
		 } 
	 }
}
