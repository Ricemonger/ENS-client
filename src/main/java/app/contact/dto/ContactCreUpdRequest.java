package app.contact.dto;

public record ContactCreUpdRequest(
        String method,
        String contactId,
        String notificationName
) {
}
