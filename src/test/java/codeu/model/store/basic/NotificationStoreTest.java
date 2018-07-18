package codeu.model.store.basic;

import codeu.model.data.Notification;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NotificationStoreTest {

  private NotificationStore notificationStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final UUID USER_ONE = UUID.randomUUID();
  private final UUID MESSAGE_ONE = UUID.randomUUID();
  private final Notification NOTIF_ONE =
      new Notification(
          UUID.randomUUID(),
          USER_ONE,
          MESSAGE_ONE,
          Instant.ofEpochMilli(1000));
  private final UUID USER_TWO = UUID.randomUUID();
  private final UUID MESSAGE_TWO = UUID.randomUUID();
  private final Notification NOTIF_TWO =
      new Notification(
          UUID.randomUUID(),
          USER_TWO,
          MESSAGE_TWO,
          Instant.ofEpochMilli(2000));
  private final UUID USER_THREE = UUID.randomUUID();
  private final UUID MESSAGE_THREE = UUID.randomUUID();
  private final Notification NOTIF_THREE =
      new Notification(
          UUID.randomUUID(),
          USER_THREE,
          MESSAGE_THREE,
          Instant.ofEpochMilli(3000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    notificationStore = NotificationStore.getTestInstance(mockPersistentStorageAgent);

    final List<Notification> notificationList = new ArrayList<>();
    notificationList.add(NOTIF_ONE);
    notificationList.add(NOTIF_TWO);
    notificationList.add(NOTIF_THREE);
    notificationStore.setNotifications(notificationList);
  }

  @Test
  public void testGetNotification_byId_found() {
    Notification resultNotification = notificationStore.getNotification(NOTIF_ONE.getId());

    assertEquals(NOTIF_ONE, resultNotification);
  }

  @Test
  public void testGetNotification_byId_notFound() {
    Notification resultNotification = notificationStore.getNotification(UUID.randomUUID());

    Assert.assertNull(resultNotification);
  }

  @Test
  public void testAddNotification() {
    Notification inputNotification =
        new Notification(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now());

    notificationStore.addNotification(inputNotification);
    Notification resultNotification = notificationStore.getNotification(inputNotification.getId());

    assertEquals(inputNotification, resultNotification);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputNotification);
  }

  private void assertEquals(Notification expectedNotification, Notification actualNotification) {
    Assert.assertEquals(expectedNotification.getId(), actualNotification.getId());
    Assert.assertEquals(expectedNotification.getUserId(), actualNotification.getUserId());
    Assert.assertEquals(expectedNotification.getMessageId(), actualNotification.getMessageId());
    Assert.assertEquals(expectedNotification.getCreationTime(), actualNotification.getCreationTime());
  }
}
