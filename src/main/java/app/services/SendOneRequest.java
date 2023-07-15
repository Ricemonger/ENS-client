package app.services;

public record SendOneRequest(
        String method,
        String contactId
) {
}
