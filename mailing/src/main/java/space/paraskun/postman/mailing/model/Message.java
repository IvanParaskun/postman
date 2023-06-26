package space.paraskun.postman.mailing.model;

public record Message(
        String destination,
        String sender,
        String subject,
        String text
) { }
