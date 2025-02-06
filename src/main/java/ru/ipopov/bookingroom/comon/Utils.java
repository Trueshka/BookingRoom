package ru.ipopov.bookingroom.comon;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static boolean isTimeStepValid(LocalDateTime time) {
        return time.getMinute() % 30 == 0 && time.getSecond() == 0 && time.getNano() == 0;
    }

    public static boolean isDurationStepValid(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime.equals(endTime) || startTime.isAfter(endTime)) {
            return false;
        }
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        return minutes % 30 == 0;
    }

}
