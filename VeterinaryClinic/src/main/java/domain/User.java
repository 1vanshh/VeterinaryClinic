package domain;

public record User (

    long id,
    String name,
    Role role
) {}
