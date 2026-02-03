package service.validator;

import java.time.OffsetDateTime;

public class TimeValidator {

    public static void validateTime(OffsetDateTime start, OffsetDateTime end) {
        if (start == null || end == null)
            throw new IllegalArgumentException("Время не указано");

        if (!start.isBefore(end))
            throw new IllegalArgumentException("start должен быть раньше end");

        if (start.isBefore(OffsetDateTime.now()))
            throw new IllegalStateException("Нельзя записаться в прошлое");
    }
}
