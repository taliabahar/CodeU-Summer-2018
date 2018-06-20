// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {

    List<User> users = new ArrayList<>();
    users.add(new User(UUID.randomUUID(), "user1",BCrypt.hashpw("user1", BCrypt.gensalt()), Instant.now()));
    users.add(new User(UUID.randomUUID(), "wordiest",BCrypt.hashpw("wordiest", BCrypt.gensalt()), Instant.now()));
    users.add(new User(UUID.randomUUID(), "most active",BCrypt.hashpw("most active", BCrypt.gensalt()), Instant.now()));
    users.add(new User(UUID.randomUUID(), "most recent",BCrypt.hashpw("most recent", BCrypt.gensalt()), Instant.now()));

    List<Conversation> conversations = new ArrayList<>();
    conversations.add(new Conversation(UUID.randomUUID(), users.get(0).getId(), "convo 1", Instant.now()));
    conversations.add(new Conversation(UUID.randomUUID(), users.get(0).getId(), "convo 2", Instant.now()));

    List<Message> messages = new ArrayList<>();
    messages.add(new Message(UUID.randomUUID(), conversations.get(0).getId(), users.get(1).getId(), "This is an awful lot of words in one message and should win wordiest user.", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(2).getId(), "This", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(2).getId(), "user", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(2).getId(), "messages", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(2).getId(), "a", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(2).getId(), "lot", Instant.now()));
    messages.add(new Message(UUID.randomUUID(), conversations.get(1).getId(), users.get(3).getId(), "I also message but not enough for awards.", Instant.now()));

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    MessageStore mockMessageStore = Mockito.mock(MessageStore.class);
    ConversationStore mockConversationStore = Mockito.mock(ConversationStore.class);

    Mockito.when(mockUserStore.getAllUsers()).thenReturn(users);
    Mockito.when(mockMessageStore.getAllMessages()).thenReturn(messages);
    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(conversations);

    Mockito.when(mockUserStore.getUser(users.get(0).getId())).thenReturn(users.get(0));
    Mockito.when(mockUserStore.getUser(users.get(1).getId())).thenReturn(users.get(1));
    Mockito.when(mockUserStore.getUser(users.get(2).getId())).thenReturn(users.get(2));
    Mockito.when(mockUserStore.getUser(users.get(3).getId())).thenReturn(users.get(3));

    adminServlet.initializeDataStores(mockUserStore, mockMessageStore, mockConversationStore);

    HashMap<String, String> stats = new HashMap<>();
    stats.put("number of conversations", "2");
    stats.put("number of messages", "7");
    stats.put("number of users", "4");
    stats.put("newest user", "most recent");
    stats.put("most active user", "most active");
    stats.put("wordiest user", "wordiest");

    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("statistics map", stats);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

}
