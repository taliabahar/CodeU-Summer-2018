<head>
<link rel="stylesheet" href="/css/main.css">
<link href="https://fonts.googleapis.com/css?family=Jockey+One|Six+Caps|VT323|Voltaire&effect=3d-float" rel="stylesheet">
</head>


<nav>
<a id="navTitle" class="font-effect-3d-float" href="/">Baker's Dozen Chat App</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
  <% } else { %>
    <a href="/login">Login</a>
  <% } %>
  <a href="/conversations">Conversations</a>
  <a href="/about.jsp">About</a>
  <% if(request.getSession().getAttribute("admin") != null){ %>
    <a href="/admin">Admin</a>
  <% } %>
</nav>
