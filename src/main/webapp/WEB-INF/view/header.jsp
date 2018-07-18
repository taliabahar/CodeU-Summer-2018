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

</head>
<nav>
<a id="navTitle" class="font-effect-3d-float" href="/">Baker's Dozen Chat App</a>
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
  <% if(request.getSession().getAttribute("user") != null){ %>
  <div class="dropdown">
    <div onclick="dropdownMenu()" class="dropbtn" > Notifications</div>
    <div id="notificationDropdown" class="dropdown-content">
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
      <a href="#">You were mentioned in ______ chat by @_______</a>
    </div>
  </div>
  <% } %>
</nav>
