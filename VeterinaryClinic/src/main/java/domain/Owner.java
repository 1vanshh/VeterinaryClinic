package domain;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    private long id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
}
