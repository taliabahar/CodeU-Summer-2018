package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a notification */
public class Notification {

  private final UUID id;
  private final UUID user;
  private final UUID message;
  private final Instant creation;

  /**
  * Constructs a new notification
  *
  * @param id the ID of this Notification
  * @param user the ID of the User this Notification is for
  * @param message the ID of the message this Notification originated from
  * @param creation the Instance for the moment this Notification was created
  */
  public Notification(UUID id, UUID user, UUID message, Instant creation) {
    this.id = id;
    this.user = user;
    this.message = message;
    this.creation = creation;
  }

  /** Returns the ID of this Notification. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the user this Notification is for */
  public UUID getUserId() {
    return user;
  }

  /** Returns the ID of the message this Notification came from */
  public UUID getMessageId() {
    return message;
  }

  /** Returns the Instant this Notification was created */
  public Instant getCreationTime() {
    return creation;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Notification)) {
      return false;
    }
    if (o == this) {
      return true;
    }
    return ((Notification) o).getId().equals(id);
  }
}
