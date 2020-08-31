package cn.studacm.sync.util;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lizhi
 * @date 2020-09-01
 */
@Slf4j
public class TimeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 计算两个日期相差天数
     *
     * @param secondDate
     * @param firstDate
     * @return
     */
    public static long diffDays(Date secondDate, Date firstDate) {
        LocalDateTime first = toLocalDateTime(firstDate);
        LocalDateTime second = toLocalDateTime(secondDate);
        return diffDays(second.toLocalDate(), first.toLocalDate());
    }

    /**
     * 计算两个LocalDate之间相差的天数
     *
     * @param second
     * @param first
     * @return
     */
    public static long diffDays(LocalDate first, LocalDate second) {
        return ChronoUnit.DAYS.between(first, second);
    }

    /**
     * 计算两个LocalDate之间相差年数
     * @param first
     * @param second
     * @return
     */
    public static long diffYears(LocalDate first, LocalDate second) {
        return ChronoUnit.YEARS.between(first, second);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return  Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
    }

    /**
     * 添加指定时间差
     *
     * @param date
     * @param amount
     * @param calendarField 日历的基本类型 如：Calendar.DAY_OF_YEAR
     * @return
     */
    public static Date add(Date date, int amount, int calendarField) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, amount);

        return calendar.getTime();
    }

    /**
     * 添加指定天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, long days) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime newLocalDateTime = localDateTime.plusDays(days);
        return Date.from(newLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 添加指定周数
     *
     * @param date
     * @param weeks
     * @return
     */
    public static Date addWeeks(Date date, long weeks) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime newLocalDateTime = localDateTime.plusWeeks(weeks);
        return Date.from(newLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 添加指定月数
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, long months) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime newLocalDateTime = localDateTime.plusMonths(months);
        return Date.from(newLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 添加指定年数
     *
     * @param date
     * @param years
     * @return
     */
    public static LocalDate plusYears(Date date, long years) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.plusYears(years).toLocalDate();
    }

    /**
     * 添加指定年数
     *
     * @param date
     * @param years
     * @return
     */
    public static LocalDate minusYears(Date date, long years) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.minusYears(years).toLocalDate();
    }

    /**
     * 获取一年开始时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getFirstDayOfYear(Date date) {
        return getFirstDayOfYear(toLocalDate(date));
    }


    /**
     * 获取一年结束时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLastDayOfYear(Date date) {
        return getLastDayOfYear(toLocalDate(date));
    }

    /**
     * 获取一年开始时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getFirstDayOfYear(LocalDate date) {
        date = date.with(TemporalAdjusters.firstDayOfYear());
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 获取一年结束时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLastDayOfYear(LocalDate date) {
        date = date.with(TemporalAdjusters.lastDayOfYear());
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 获取一天开始时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getFirstDateOfDay(Date date) {
        LocalDate localDate = toLocalDate(date);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }


    /**
     * 获取一天结束时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLastDateOfDay(Date date) {
        LocalDate localDate = toLocalDate(date);
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }


    /**
     * Date to  timestamp
     *
     * @param date
     * @return
     */
    public static long dateToTimestamp(Date date) {
        return date.getTime();
    }

    /**
     * LocalDate to  timestamp
     *
     * @param localDate
     * @return
     */
    public static long localDateToTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

    public static LocalDate parseLocalDate(String dateTime) {
        return LocalDate.parse(dateTime, FORMATTER);
    }

    public static Date parseDate(String dateTime) {
        return toDate(LocalDateTime.parse(dateTime, FORMATTER));
    }

}
