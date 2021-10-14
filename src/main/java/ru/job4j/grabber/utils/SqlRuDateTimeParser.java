package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, String> MONTHS = Map.ofEntries(
            entry("дек", "Dec"),
            entry("янв", "Jan"),
            entry("фев", "Feb"),
            entry("мар", "Mar"),
            entry("апр", "Apr"),
            entry("май", "May"),
            entry("июн", "Jun"),
            entry("июл", "Jul"),
            entry("авг", "Aug"),
            entry("сен", "Sep"),
            entry("окт", "Oct"),
            entry("ноя", "Nov")
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime dateTime = null;
        String[] mas = parse.split(" ");
        if (mas[0].trim().length() == 1) {
            mas[0] = "0" + mas[0];
        }
        String mapReturn = MONTHS.get(mas[1]);
        if (mapReturn != null) {
            mas[1] = mapReturn;
            String name = String.join(" ", mas);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy, HH:mm", Locale.ENGLISH);
            dateTime = LocalDateTime.parse(name, formatter);
        } else {
            LocalDate ld = LocalDate.now();
            String[] hourMinutes = mas[1].split(":");
            LocalDateTime createDataTimes = LocalDateTime.of(ld.getYear(),
                    ld.getMonth(),
                    ld.getDayOfMonth(),
                    Integer.parseInt(hourMinutes[0]),
                    Integer.parseInt(hourMinutes[1])
            );
            dateTime = (mas[0].equals("сегодня,")) ? createDataTimes : createDataTimes.minusDays(1);
        }
        return dateTime;
    }
}
