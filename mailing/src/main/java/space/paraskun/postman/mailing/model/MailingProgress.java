package space.paraskun.postman.mailing.model;

public record MailingProgress(
        String state,
        int goal,
        int current
) { }
