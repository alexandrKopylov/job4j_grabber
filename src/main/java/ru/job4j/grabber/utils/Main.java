package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;

public class Main {
    public static void main(String[] args) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        System.out.println("Текущие дата и время до форматирования: " + currentDateTime);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yy HH:mm:ss");
//        String currentDateTimeFormat = currentDateTime.format(formatter);
//        System.out.println("Текущие дата и время после форматирования: " + currentDateTimeFormat);


//        String str = "1986-04-08 12:30";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//


//         final Map<String, String> MONTHS = Map.ofEntries(
//                entry("дек", "декабрь"),
//                entry("янв", "Jan"),
//                entry("фев", "февраль"),
//                entry("мар", "март"),
//                entry("апр", "апрель"),
//                entry("май", "май"),
//                entry("июн", "июнь"),
//                entry("июл", "июль"),
//                entry("авг", "август"),
//                entry("сен", "сентябрь"),
//                entry("окт", "октябрь"),
//                entry("ноя", "ноябрь")
//        );
//         String sss = "2 янв 05 22:29";
//       // String sss = "02 08 2020 22:29";
//        String[] mas = sss.split(" ");
//       String rrr = MONTHS.get(mas[1]);
//           mas[1]  = MONTHS.get(mas[1]);
//
//        String name = String.join(" ", mas);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", Locale.ENGLISH);
//        LocalDateTime dateTime = LocalDateTime.parse(name, formatter);
//        System.out.println(dateTime);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM-dd-yyyy");
//        LocalDate date = LocalDate.parse("February-23-2019", dtf);
//        System.out.println(date);


//        String str = "1986-04-08 12:30";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
//        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//
//        String sss =  dateTime.format(formatter);
//        System.out.println(sss);
      //  Locale.ENGLISH


       // String sss = "5 дек 05, 22:29";

         String sss = "вчера, 22:29";
        SqlRuDateTimeParser rrrr = new SqlRuDateTimeParser();


        LocalDateTime ddd = rrrr.parse(sss);
        System.out.println(ddd);

//        LocalDateTime ldt = LocalDateTime.now();
//        System.out.println(ldt);
//        System.out.println( ldt.minusDays(1));

    }

}
