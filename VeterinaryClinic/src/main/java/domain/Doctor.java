package domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private long id;
    private String fullName;
    private String specialization;
    private String phone;
}
