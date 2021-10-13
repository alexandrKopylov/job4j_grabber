package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            entry("дек", "декабрь"),
            entry("янв", "январь"),
            entry("фев", "февраль"),
            entry("мар", "март"),
            entry("апр", "апрель"),
            entry("май", "май"),
            entry("июн", "июнь"),
            entry("июл", "июль"),
            entry("авг", "август"),
            entry("сен", "сентябрь"),
            entry("окт", "октябрь"),
            entry("ноя", "ноябрь")
            );

    @Override
    public LocalDateTime parse(String parse) {

        String[] mas = parse.split(" ");



        return null;
    }


}