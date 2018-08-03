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
<%@ page import="java.util.Iterator" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="codeu.model.data.Message" %>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>
  <%@ include file = "/WEB-INF/view/header.jsp" %>
<div id="container">
  <% if(request.getSession().getAttribute("user") != null){ %>
    <h1> <%= user.getName() %> 's Profile Page </h1>
    <h3>My Bio: </h3>
    <p> <%= user.getAboutMe() %></p>
    <form action ="/profile" method="POST">
      <label for = "aboutme" > Write your About Me: </label>
      <br>
        <input type= "text" name= "aboutme" id= "aboutme" value="" style= "width: 600px; height: 40px;">
      <br/>
      <input type="Submit">
    </form>
  <h3><%= user.getName() + "'s"%> Sent Messages</h3>
	<% List<Message> messagesSent = (List<Message>) request.getAttribute("messages");
	if (messagesSent != null && !messagesSent.isEmpty()){
		for (Message message: messagesSent) { %>
			<a><strong> <%=message.getTime() %> </strong> : <%= message.getContent() %></a>
			<br/>
	<% }} %>
	<% } %>

		
</div>
</body>
</html>
