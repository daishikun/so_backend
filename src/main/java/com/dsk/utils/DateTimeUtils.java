package com.dsk.utils;

import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**  时间工具类
 * @author daishikun
 * @date 2023/4/23
 */
public class DateTimeUtils {

    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) {
        System.out.println(format(new Date(), null));
        System.out.println(parse("2023-04-23 10:10:10", null));
        System.out.println(format(LocalDateTime.now(), null));
    }



    /**
     * SimpleDateFormat形式Date->Date字符串
     * @param date  时间
     * @param pattern  格式
     * @return  String  时间字符串
     */
    @Deprecated
    public static String format(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = yyyyMMddHHmmss;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * LocalDateTime时间形式->时间字符串
     * @param localDateTime  时间
     * @param pattern  格式
     * @return  String  时间字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = yyyyMMddHHmmss;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return fmt.format(localDateTime);
    }


    /**
     * 时间字符串-->LocalDateTime时间形式
     * @param pattern  格式
     * @param  dateStr  时间字符串
     * @return localDateTime  时间
     */
    public static LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }


    /**
     * SimpleDateFormat形式Date的字符串->Date
     * @param dateStr  时间字符串
     * @param pattern  格式
     * @return  Date  时间
     */
    @Deprecated
    public static Date parse(String dateStr, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = yyyyMMddHHmmss;
        }
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



}
