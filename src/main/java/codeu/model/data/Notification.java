package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a notification */
public class Notification {

  private final UUID id;
  private final UUID user;
  private final UUID message;
  private final String text;
  private final Instant creation;

  /**
  * Constructs a new notification
  *
  *
  */
  public Notification(UUID id, UUID user, UUID message, String text, Instance creation) {
    this.id = id;
    this.user = user;
    this.message = message;
    this.text = text;
    this.creation = creation;
  }

  

}
