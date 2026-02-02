package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Appointment {

    private final long id;
    private final long petId;
    private final long doctorId;

    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private String reason;
    private AppointmentStatus status; //PLANNED, CANCELLED, DONE, NO_SHOW
}
