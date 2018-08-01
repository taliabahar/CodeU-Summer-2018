<head>
  <link rel="stylesheet" href="/css/main.css">
  <link href="https://fonts.googleapis.com/css?family=Jockey+One|Six+Caps|VT323|Voltaire&effect=3d-float" rel="stylesheet">
  <script>
    function dropdownMenu() {
      document.getElementById("notificationDropdown").classList.toggle("show");
    }

    // Close the dropdown menu if the user clicks outside of it
    window.onclick = function(event) {
      if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
          var openDropdown = dropdowns[i];
          if (openDropdown.classList.contains('show')) {
            openDropdown.classList.remove('show');
          }
        }
      }
    }
  </script>
  <%@ page import="java.util.List" %>
  <%@ page import="codeu.model.data.Conversation" %>
  <%@ page import="codeu.model.data.User" %>
  <%@ page import="codeu.model.data.Message" %>
  <%@ page import="codeu.model.data.Notification" %>
  <%@ page import="codeu.model.store.basic.UserStore" %>
  <%@ page import="codeu.model.store.basic.MessageStore" %>
  <%@ page import="codeu.model.store.basic.ConversationStore" %>

</head>
<nav>
<a id="navTitle" class="font-effect-3d-float" href="/">Baker's Dozen Chat App</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
  <% } else { %>
    <a href="/login">Login</a>
  <% } %>
  <a href="/conversations">Conversations</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
  <a href="/profile">Profile</a>
  <% } %>
  <a href="/about.jsp">About</a>
  <% if(request.getSession().getAttribute("admin") != null){ %>
    <a href="/admin">Admin</a>
  <% } %>
  <% UserStore userStore = UserStore.getInstance();
  MessageStore messageStore = MessageStore.getInstance();
  ConversationStore conversationStore = ConversationStore.getInstance();
  User user = userStore.getUser((String) request.getSession().getAttribute("user"));
  if(request.getSession().getAttribute("user") != null){ %>
  <div class="dropdown">
    <div onclick="dropdownMenu()" class="dropbtn" > Notifications</div>
    <div id="notificationDropdown" class="dropdown-content">
      <%
      for (Notification n : user.getNotifications()) {
        Message m = messageStore.getMessage(n.getMessageId());
        Conversation c = conversationStore.getConversation(m.getConversationId());
        User u = userStore.getUser(m.getAuthorId());
        %>
        <a href="/chat/<%= c.getTitle() %>">You were mentioned in <%= c.getTitle() %> Conversation by @<%= u.getName() %></a>
      <% } %>
    </div>
  </div>
  <% } %>
</nav>
