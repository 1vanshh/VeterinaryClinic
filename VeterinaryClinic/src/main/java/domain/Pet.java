package domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Pet {

    private final long id;
    private final long ownerId;

    private String name;
    private String species;
    private String breed;
    private Gender gender;
}