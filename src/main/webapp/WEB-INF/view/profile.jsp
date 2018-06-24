<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>


<%
	UserStore userStore = UserStore.getInstance();
	User user = userStore.getUser((String)request.getSession().getAttribute("user"));
%>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>
  <%@ include file = "/WEB-INF/view/header.jsp" %>

 <div id="container"><h1><%=request.getSession().getAttribute("user")%> 's Profile Page</h1>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <form action ="/profile" method="POST">
	  <h3>My Bio: </h3>
		<p> <%= user.getAboutMe() %> </p> 
        <label for = "About Me" > Write your About Me: </label>
        <br>
		<input type= "text" name= "About Me" id= "about me" value="" style= "width: 600px; height: 40px;">
		<br/>
        <input type="Submit">
       </form>
    <% } %>
  </div>
</body>
</html>
