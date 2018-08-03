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
	private MessageStore messageStore;
	private UserStore userStore;

	@Override
	public void init() throws ServletException {
		super.init();
		setMessageStore(MessageStore.getInstance());
		setUserStore(UserStore.getInstance());
		}

	void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
    }

	void setUserStore(UserStore userStore) {
    this.userStore = userStore;
    }

	 @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = (String) request.getSession().getAttribute("user");
		user = UserStore.getInstance().getUser(username);
		
		if (user == null) {
	    System.out.println("Not logged in " + username);
      response.sendRedirect("/login");
		  return;
	  }
		else{
		UUID userid = user.getId();
		MessageStore message = MessageStore.getInstance();
		List<Message> messagesSent = message.getMessagesByUser(userid); //get the users messages
		request.setAttribute("user", user);
		request.setAttribute("messages", messagesSent);
		 
		request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);     
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = (String) request.getSession().getAttribute("user");
		user = userStore.getUser(username);

		if(username != null){
			String aboutMe = request.getParameter("aboutme");
			user.setAboutMe(aboutMe);
			userStore.updateUser(user);
			response.sendRedirect("/profile");
			}
		}
}
