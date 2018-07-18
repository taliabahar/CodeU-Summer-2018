<nav>
  <a id="navTitle" href="/">Baker's Dozen Chat App</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
  <% } else { %>
    <a href="/login">Login</a>
  <% } %>
  <a href="/conversations">Conversations</a>
   <a href="/profile">Profile</a>
  <a href="/about.jsp">About</a>
  <% if(request.getSession().getAttribute("admin") != null){ %>
    <a href="/admin">Admin</a>
  <% } %>
</nav>
