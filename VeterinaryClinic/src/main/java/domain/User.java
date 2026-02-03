package domain;

public record User (

    long id,
    String login,
    Role role
) {}
