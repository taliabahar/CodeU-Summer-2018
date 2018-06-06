package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

  private ConversationStore conversationStore;
  private UserStore userStore;
  private MessageStore messageStore;

  private User wordiestUser;
  private User mostActiveUser;
  private User mostRecentUser;


  /**
   * Set up state for handling admin-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * This function fires when a user requests the /admin URL. It simply forwards the request to
   * admin.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    List<Conversation> conversations = conversationStore.getAllConversations();
    List<User> users = userStore.getAllUsers();
    List<Message> messages = messageStore.getAllMessages();

    //Create a Map that maps the names of stats to the statistics in String form
    HashMap<String, String> stats = new HashMap<>();
    stats.put("number of conversations", Integer.toString(conversations.size()));
    stats.put("number of messages", Integer.toString(messages.size()));
    stats.put("number of users", Integer.toString(users.size()));

    this.initializeUserStats(users, messages);

    stats.put("newest user", userToString(mostRecentUser));
    stats.put("most active user", userToString(mostActiveUser));
    stats.put("wordiest user", userToString(wordiestUser));

    //Add the map to the request
    request.setAttribute("statistics map", stats);

    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }

  /**
   * This function should not be triggered by anything.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal Post request - This address does not handle Post requests");
  }

  /**
  * Initializes the wordiestUser, mostActiveUser, and mostRecentUser variables
  *
  */
  private void initializeUserStats(List<User> users, List<Message> messages) {
    mostRecentUser = getMostRecentUser(users);

    HashMap<UUID, Integer> numMessages = new HashMap<>();
    HashMap<UUID, Integer> numWords = new HashMap<>();
    for (Message m : messages) {
      UUID userID = m.getAuthorId();
      if (!numMessages.containsKey(userID)) {
        numMessages.put(userID, 0);
        numWords.put(userID, 0);
      }
      numMessages.put(userID, numMessages.get(userID) + 1);
      numWords.put(userID, numWords.get(userID) + getNumWords(m.getContent()));
    }

    int mostMessages = 0;
    UUID mostMessagesID = null;
    double highestAverage = 0;
    UUID wordiestID = null;
    for (Map.Entry<UUID, Integer> entry : numMessages.entrySet()) {
      if (entry.getValue() > mostMessages) {
        mostMessages = entry.getValue();
        mostMessagesID = entry.getKey();
      }
      double avg = numWords.get(entry.getKey()) / (double) entry.getValue();
      if (avg > highestAverage) {
        highestAverage = avg;
        wordiestID = entry.getKey();
      }
    }
    mostActiveUser = userStore.getUser(mostMessagesID);
    wordiestUser = userStore.getUser(wordiestID);
  }

  private static int getNumWords(String str) {
    return str.split("\\s+").length;
  }

  /*
  * Returns teh User who most recently registered.
  *
  * @return the most recent User
  */
  private static User getMostRecentUser(List<User> users) {
    User recent = null;
    for (User user : users) {
      if (recent == null) {
        recent = user;
      } else if (user.getCreationTime().compareTo(recent.getCreationTime()) > 0) {
        recent = user;
      }
    }
    return recent;
  }

  private String userToString(User user) {
    if (user == null) {
      return "No user";
    } else {
      return user.getName();
    }
  }

}
