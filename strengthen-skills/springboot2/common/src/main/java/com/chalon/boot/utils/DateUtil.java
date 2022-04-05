package com.chalon.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author wei.peng
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 年-月-日 时:分:秒 显示格式
     * 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
     */
    public static String DATE_TO_STRING_DETAIL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    /**
     * date类型转换为默认时间格式类型字符串
     *
     * @param date
     * @return
     */
    public static String defaultFormat(Date date) {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIL_PATTERN);
        return simpleDateFormat.format(date);
    }

    /**
     * long类型转换为默认时间格式类型字符串
     *
     * @param millis
     * @return
     */
    public static String defaultFormat(long millis) {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIL_PATTERN);
        return simpleDateFormat.format(millis);
    }

    /**
     * date类型转换为短时间格式类型字符串（只有年月日）
     *
     * @param date
     * @return
     */
    public static String shortFormat(Date date) {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
        return simpleDateFormat.format(date);
    }

    /**
     * long类型转换为短时间格式类型字符串（只有年月日）
     *
     * @param millis
     * @return
     */
    public static String shortFormat(long millis) {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
        return simpleDateFormat.format(millis);
    }

    /**
     * Date类型转为指定格式的String类型
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String dateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * unix时间戳转为指定格式的String类型
     * <p>
     * System.currentTimeMillis()获得的是是从1970年1月1日开始所经过的毫秒数
     * unix时间戳:是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数,不考虑闰秒
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String timeStampToString(long source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(source * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换为时间戳(unix时间戳,单位秒)
     *
     * @param date
     * @return
     */
    public static long dateToTimeStamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime() / 1000;
    }

    /**
     * 字符串转换为对应日期(可能会报错异常)
     *
     * @param source
     * @param pattern
     * @return
     */
    public static Date stringToDate(String source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            logger.error("字符串转换日期异常", e);
        }
        return date;
    }

    /**
     * 获得当前时间对应的指定格式
     *
     * @param pattern
     * @return
     */
    public static String currentFormatDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获得当前unix时间戳(单位秒)
     *
     * @return 当前unix时间戳
     */
    public static long currentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static Date timeStampToDate(Long timeStamp) {
        Timestamp ts = new Timestamp(timeStamp);
        Date date = ts;
        date = new Date(ts.getTime());
        return date;
    }

    public static String dateFormat() {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIL_PATTERN);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     *
     * @param date
     * @return
     * @warn 该方法其时间不是绝对时间
     */
    @Deprecated
    public static Long[] getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        Long begin = cal.getTimeInMillis();
        cal.add(Calendar.DATE, 6);
        Long end = cal.getTimeInMillis();
        Long[] times = new Long[]{begin, end};
        return times;
    }

    /**
     * 根据当前日期获得上周的日期区间（上周周一和周日日期）
     *
     * @return
     */
    public static Long[] getLastTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        long begin = calendar1.getTimeInMillis();
        long end = calendar2.getTimeInMillis();
        return new Long[]{begin, end};
    }

    /**
     * 获取今天开始、结束时间戳
     *
     * @param date
     * @return
     */
    @Deprecated
    public static Long[] getTodayBeginAndEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
        long begin = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 999);
        long end = cal.getTimeInMillis();
        return new Long[]{begin, end};
    }

    /**
     * 获取给定日期的前一天开始、结束时间戳
     *
     * @param date
     * @return
     */
    public static Long[] getLastBeginAndEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
        long begin = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 999);
        long end = cal.getTimeInMillis();
        return new Long[]{begin, end};
    }

    /**
     * 获取上一个月开始结束时间戳
     *
     * @return
     */
    public static Long[] getLastMonthBeginAndEnd() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1L);
        long begin = getMonthMinMilli(lastMonth);
        long end = getMonthMaxMilli(lastMonth);
        return new Long[]{begin, end};
    }

    /**
     * 获取当前月开始结束时间戳
     *
     * @return
     */
    public static Long[] getCurrentMonthBeginAndEnd() {
        LocalDate currentMonth = LocalDate.now();
        long begin = getMonthMinMilli(currentMonth);
        long end = getMonthMaxMilli(currentMonth);
        return new Long[]{begin, end};
    }

    /**
     * 相对12个月（前12个月包括本月）
     * 开始结束时间戳
     *
     * @return
     */
    public static Long[] getLastTwelveMonthsBeginAndEnd() {
        long begin = 0L;
        long end = 0L;
        //
        LocalDate today = LocalDate.now();
        long lastTwelveMonth = 11L;
        // 递减循环
        for (long i = lastTwelveMonth; i >= 0L; i--) {
            LocalDate date = today.minusMonths(i);
            if (lastTwelveMonth == i) {
                begin = getMonthMinMilli(date);
            }
            if (0L == i) {
                end = getMonthMaxMilli(date);
            }
        }
        return new Long[]{begin, end};
    }

    /**
     * 绝对12个月（本年，今年12个月）
     * 开始结束时间戳
     *
     * @return
     */
    public static Long[] getThisYearBeginAndEnd() {
        long begin = 0L;
        long end = 0L;
        //
        int thisYear = LocalDate.now().getYear();
        // 递增循环
        int thisYearMonth = 12;
        for (int i = 1; i <= thisYearMonth; i++) {
            LocalDate date = LocalDate.of(thisYear, i, 1);
            if (1 == i) {
                begin = getMonthMinMilli(date);
            }
            if (12 == i) {
                end = getMonthMaxMilli(date);
            }
        }
        return new Long[]{begin, end};
    }

    private static Long getMonthMinMilli(LocalDate date) {
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime min = LocalDateTime.of(firstDay, LocalTime.MIN);
        long minMilli = min.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return minMilli;
    }

    private static Long getMonthMaxMilli(LocalDate date) {
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime max = LocalDateTime.of(lastDay, LocalTime.MAX);
        long maxMilli = max.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return maxMilli;
    }

    /**
     * 相对12个月（前12个月包括本月）
     * 每个月开始结束时间戳
     *
     * @return
     */
    public static List<Long[]> getLastTwelveEachMonth() {
        List<Long[]> months = Lists.newArrayList();
        //
        LocalDate today = LocalDate.now();
        long lastTwelveMonth = 11L;
        // 递减循环
        for (long i = lastTwelveMonth; i >= 0L; i--) {
            LocalDate date = today.minusMonths(i);
            Long[] month = getMonthStartEndMilli(date);
            months.add(month);
        }
        return months;
    }

    /**
     * 绝对12个月（本年，今年12个月）
     * 每个月开始结束时间戳
     *
     * @return
     */
    public static List<Long[]> getThisYearTwelveEachMonth() {
        List<Long[]> months = Lists.newArrayList();
        //
        int thisYear = LocalDate.now().getYear();
        int thisYearMonth = 12;
        // 递增循环
        for (int i = 1; i <= thisYearMonth; i++) {
            LocalDate date = LocalDate.of(thisYear, i, 1);
            Long[] month = getMonthStartEndMilli(date);
            months.add(month);
        }
        return months;
    }

    private static Long[] getMonthStartEndMilli(LocalDate date) {
        Long[] month = new Long[2];
        //
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime min = LocalDateTime.of(firstDay, LocalTime.MIN);
        long minMilli = min.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        month[0] = minMilli;
        //
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime max = LocalDateTime.of(lastDay, LocalTime.MAX);
        long maxMilli = max.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        month[1] = maxMilli;
        return month;
    }

    public static String getYearMonthByMilliSecond(Long milliSecond) {
        Date date = new Date();
        date.setTime(milliSecond);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * 获取最近七天的开始结束时间戳（包括今天）
     *
     * @return
     */
    public static Long[] getLastSevenDaysBeginAndEnd() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDay = today.minusDays(6L);
        long begin = getDayMinMilli(sevenDay);
        long end = getDayMaxMilli(today);
        return new Long[]{begin, end};
    }

    private static Long getDayMinMilli(LocalDate date) {
        LocalDateTime min = LocalDateTime.of(date, LocalTime.MIN);
        long minMilli = min.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return minMilli;
    }

    private static Long getDayMaxMilli(LocalDate date) {
        LocalDateTime max = LocalDateTime.of(date, LocalTime.MAX);
        long maxMilli = max.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return maxMilli;
    }

    /**
     * 获取最近二十四小时时间区间
     *
     * @return
     */
    public static Long[] getLastTwentyFourHourBeginAndEnd() {
        ZoneOffset offset = ZoneOffset.of("+08:00");
        //
        LocalDateTime now = LocalDateTime.now();
        Instant cur = now.toInstant(offset);
        long end = cur.toEpochMilli();
        //
        LocalDateTime lastTwentyFourHour = now.minusHours(24L);
        Instant last = lastTwentyFourHour.toInstant(offset);
        long begin = last.toEpochMilli();
        return new Long[]{begin, end};
    }

    /**
     * 获取今天的开始结束时间戳
     *
     * @return
     */
    public static Long[] getTodayBeginAndEnd() {
        long begin = getDayMinMilli(LocalDate.now());
        long end = getDayMaxMilli(LocalDate.now());
        return new Long[]{begin, end};
    }

    /**
     * 获取这周开始结束时间
     *
     * @return
     */
    public static Long[] getTheWeekBeginAndEnd() {
        LocalDate resDate = LocalDate.now();
        DayOfWeek week = resDate.getDayOfWeek();
        int value = week.getValue();
        long begin = getDayMinMilli(resDate.minusDays(value - 1));
        long end = getDayMaxMilli(resDate.plusDays(7 - value));
        return new Long[]{begin, end};
    }

    /**
     * 获取周第一天
     *
     * @param date
     * @return
     */
    public Date getStartDayOfWeek(String date) {
        LocalDate now = LocalDate.parse(date);
        return this.getStartDayOfWeek(now);
    }

    public Date getStartDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate.with(fieldISO, 1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取周最后一天
     *
     * @param date
     * @return
     */
    public Date getEndDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return this.getEndDayOfWeek(localDate);
    }

    public Date getEndDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate.with(fieldISO, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 一天的开始
     *
     * @param date
     * @return
     */
    public Date getStartOfDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return this.getStartOfDay(localDate);
    }

    public Date getStartOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 一天的结束
     *
     * @param date
     * @return
     */
    public Date getEndOfDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return this.getEndOfDay(localDate);
    }

    public Date getEndOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 获取上一天的开始和今天结束的时间戳
     *
     * @return
     */
    public static Long[] getLastDayBeginAndEnd() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDay = today.minusDays(1L);
        long begin = getDayMinMilli(sevenDay);
        long end = getDayMaxMilli(today);
        return new Long[]{begin, end};
    }

    /**
     * 获取上一天的开始的时间戳
     *
     * @return
     */
    public static Long getLastDayBegin() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDay = today.minusDays(1L);
        long begin = getDayMinMilli(sevenDay);
        return begin;
    }

    /**
     * 获取当前日期的前一天开始时间戳
     *
     * @return
     */
    public static Long getLastBegin() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
        long begin = cal.getTimeInMillis();
        return begin;
    }

}
