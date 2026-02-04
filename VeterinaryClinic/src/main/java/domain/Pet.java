package domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private long id;
    private long ownerId;

    private String name;
    private String species;
    private String breed;
    private Gender gender;
}