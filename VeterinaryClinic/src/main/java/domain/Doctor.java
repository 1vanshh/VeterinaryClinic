package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Doctor {

    private final long id;
    private String fullName;
    private String specialization;
    private String phone;
}
