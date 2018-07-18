package codeu.model.store.basic;

import codeu.model.data.Notification;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class NotificationStore {

  /** Singleton instance of NotificationStore. */
  private static NotificationStore instance;

  /**
   * Returns the singleton instance of NotificationStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static NotificationStore getInstance() {
    if (instance == null) {
      instance = new NotificationStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static NotificationStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new NotificationStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Notifications from and saving Notifications to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Notifications. */
  private List<Notification> notifications;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private NotificationStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    notifications = new ArrayList<>();
  }

  /**
   * Access the Notification object with the given UUID.
   *
   * @return null if the UUID does not match any existing Notification.
   */
  public Notification getNotification(UUID id) {
    for (Notification notification : notifications) {
      if (notification.getId().equals(id)) {
        return notification;
      }
    }
    return null;
  }

  /**
  * Returns the list of all Notifications
  *
  * @return List<Notification> of all Notifications
  */
  public List<Notification> getAllNotifications() {
    return notifications;
  }

  /**
   * Add a new Notification to the current set of Notifications known to the application. This should only be called
   * to add a new Notification, not to update an existing Notification.
   */
  public void addNotification(Notification notification) {
    notifications.add(notification);
    persistentStorageAgent.writeThrough(notification);
  }

  /**
   * Update an existing Notification.
   */
  public void updateNotification(Notification notification) {
    persistentStorageAgent.writeThrough(notification);
  }

  /**
   * Sets the List of Notifications stored by this NotificationStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setNotifications(List<Notification> notifications) {
    this.notifications = notifications;
  }
}
