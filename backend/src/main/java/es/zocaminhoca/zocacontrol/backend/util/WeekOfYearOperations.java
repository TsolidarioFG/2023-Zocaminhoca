package es.zocaminhoca.zocacontrol.backend.util;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public abstract class WeekOfYearOperations {

    public static int getWeekOfYear(LocalDate date) {

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());

    }

    public static boolean compareDateWeeks(LocalDate a, LocalDate b) {

        return (getWeekOfYear(a) == getWeekOfYear(b)) && (a.getYear() == b.getYear());

    }

    public static LocalDate getFirstDayOfWeek(LocalDate date) {

        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.minusDays(1);
        }
        return date;

    }

    public static LocalDate getLastDayOfWeek(LocalDate date) {

        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;

    }

}
