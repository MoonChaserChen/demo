package ink.akira.helloJdk8;

import org.junit.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class DateTimeTest {
    @Test
    public void testDateTime(){
        System.out.println("=============== LocalDateTime 不带时区的 日期 + 时间 ===============");
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        System.out.println("=============== ZonedDateTime 带时区的 日期 + 时间 ===============");
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println(zdt);
        System.out.println("=============== LocalDateTime与ZonedDateTime相互转换 (LocalDateTime + ZoneId = ZonedDateTime) ===============");
        System.out.println(ldt.atZone(ZoneId.systemDefault()));
        System.out.println(zdt.toLocalDateTime());
        System.out.println("=============== 时间加减 ===============");
        System.out.println(ldt.plusDays(1));
        System.out.println(zdt.plusDays(1));
        System.out.println("=============== ZonedDateTime 2 Date（通过Instant转换） ===============");
        Instant instant = zdt.toInstant();
        Date date = Date.from(instant);
        System.out.println(date);
        System.out.println("=============== Date 2 ZonedDateTime（通过Instant转换） ===============");
        Instant instant1 = date.toInstant();
        ZonedDateTime zdt1 = ZonedDateTime.ofInstant(instant1, ZoneId.systemDefault());
        System.out.println(zdt1);
        // LocalDateTime implements Temporal, TemporalAdjuster, ChronoLocalDateTime<LocalDate>, Serializable
        // ZonedDateTime implements Temporal, ChronoZonedDateTime<LocalDate>, Serializable
    }

    @Test
    public void testDate2LocalDateTime(){
        Date date = new Date();
        Instant instant = date.toInstant();
        System.out.println(date.getTime());
        System.out.println(instant.toEpochMilli());
        System.out.println(instant.getEpochSecond());
        System.out.println(instant.getNano());
    }

    @Test
    public void common(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        System.out.println(date);
    }
}
