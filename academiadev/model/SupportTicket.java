import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class SupportTicket {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private final String creatorEmail;
    private final String title;
    private final String message;
    private final LocalDateTime createdAt;

    public SupportTicket(String creatorEmail, String title, String message) {
        this.id = COUNTER.getAndIncrement();
        this.creatorEmail = creatorEmail;
        this.title = title;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() { return id; }
    public String getCreatorEmail() { return creatorEmail; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("Ticket #%d by %s at %s - %s", id, creatorEmail, createdAt, title);
    }
}
