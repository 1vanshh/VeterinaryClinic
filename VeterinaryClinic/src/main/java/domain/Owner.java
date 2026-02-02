package domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Owner {

    private final long id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
}
