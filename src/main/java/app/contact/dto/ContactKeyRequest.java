package app.contact.dto;

public record ContactKeyRequest(
        String method,
        String contactId
) {
}
