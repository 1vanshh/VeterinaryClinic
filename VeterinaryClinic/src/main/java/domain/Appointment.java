package domain;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private long id;
    private long petId;
    private long doctorId;

    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private String reason;
    private AppointmentStatus status; //PLANNED, CANCELLED, DONE, NO_SHOW
}
