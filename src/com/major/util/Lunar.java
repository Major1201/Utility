package com.major.util;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Minjie
 * Date: 13-9-19
 * Time: 下午10:33
 */
@SuppressWarnings("UnusedDeclaration")
public class Lunar {
    //天干
    private static final String TIAN_GAN = "甲乙丙丁戊己庚辛壬癸";
    //地支
    private static final String DI_ZHI = "子丑寅卯辰巳午未申酉戌亥";
    //中文数字
    private static final String NUMBER = "一二三四五六七八九十";
    //阴历中的月称
    private static final String CHINESE_MONTH = "正二三四五六七八九十冬腊";
    //生肖
    private static final String ZODIAC = "鼠牛虎兔龙蛇马羊猴鸡狗猪";
    //1900年到2050年的阴历推算信息
    private static long[] CALENDAR_DATA = new long[] { 0x8096d, 0x4ae, 0xa57, 0x50a4d,
            0xd26, 0xd95, 0x40d55, 0x56a, 0x9ad, 0x2095d, 0x4ae, 0x6149b,
            0xa4d, 0xd25, 0x51aa5, 0xb54, 0xd6a, 0x212da, 0x95b, 0x70937,
            0x497, 0xa4b, 0x5164b, 0x6a5, 0x6d4, 0x415b5, 0x2b6, 0x957,
            0x2092f, 0x497, 0x60c96, 0xd4a, 0xea5, 0x50d69, 0x5ad, 0x2b6,
            0x3126e, 0x92e, 0x7192d, 0xc95, 0xd4a, 0x61b4a, 0xb55, 0x56a,
            0x4155b, 0x25d, 0x92d, 0x2192b, 0xa95, 0x71695, 0x6ca, 0xb55,
            0x50ab5, 0x4da, 0xa5d, 0x30a57, 0x52d, 0x8152a, 0xe95, 0x6aa,
            0x615aa, 0xab5, 0x4b6, 0x414ae, 0xa57, 0x526, 0x31d26, 0xd95,
            0x70b55, 0x56a, 0x96d, 0x5095d, 0x4ad, 0xa4d, 0x41a4d, 0xd25,
            0x81aa5, 0xb54, 0xb5a, 0x612da, 0x95b, 0x49b, 0x41497, 0xa4b,
            0xa164b, 0x6a5, 0x6d4, 0x615b4, 0xab6, 0x957, 0x5092f, 0x497,
            0x64b, 0x30d4a, 0xea5, 0x80d65, 0x55c, 0xab6, 0x5126d, 0x92e,
            0xc96, 0x41a95, 0xd4a, 0xda5, 0x20b55, 0x56a, 0x7155b, 0x25d,
            0x92d, 0x5192b, 0xa95, 0xb4a, 0x416aa, 0xad5, 0x90ab5, 0x4ba,
            0xa5b, 0x60a57, 0x52b, 0xa93, 0x40e95, 0x6aa, 0xad5, 0x209b5,
            0x4b6, 0x614ae, 0xa4e, 0xd26, 0x51d26, 0xd53, 0x5aa, 0x30d6a,
            0x96d, 0x7095d, 0x4ad, 0xa4d, 0x61a4b, 0xd25, 0xd52, 0x51b54,
            0xb5a, 0x56d, 0x2095b, 0x49b, 0x71497, 0xa4b, 0xaa5, 0x516a5,
            0x6d2, 0xada };

    private String year, zodiac, month, day, hour;

    public String getYear() {
        return year;
    }

    public String getZodiac() {
        return zodiac;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMonthAndDay() {
        return month + "月" + day;
    }

    public String getFullString() {
        return year + "年(" + zodiac + ")" + month + "月" + day + hour + "时";
    }

    public Lunar(Date date) {
        int cYear, cMonth, cDay, cHour;


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int total, m, n, k;
        boolean isEnd = false;

        //判断合法性
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1900, Calendar.JANUARY, 31);
        if (calendar.get(Calendar.YEAR) >= 2050 || calendar.getTimeInMillis() < calendar1.getTimeInMillis())
            throw new IllegalArgumentException("illegal year,must between 1900 to 2050.");

        //计算TheDate到1900年1月30日的总天数，1900年1月31日是“庚子年正月初一”我们以这个时间点来推测
        total = (int) TimeUtil.getBetweenDays(calendar1.getTime(), date) + 1;

        //开始推算已经过了几个阴历年，从1900年开始
        for (m = 0;; m++) {
            //检查16进制中信息，当年是否有闰月，有，则为13个月份
            k = (CALENDAR_DATA[m] < 0xfff) ? 11 : 12;

            for (n = k; n >= 0; n--) {
                //如果总天数被减得小于29或30（由16进制中的规律来确定），则推算结束
                if (total <= 29 + getBit(CALENDAR_DATA[m], n)) {
                    isEnd = true;
                    break;
                }
                //如果不小于29或30，则继续做减
                total = total - 29 - getBit(CALENDAR_DATA[m], n);
            }
            if (isEnd)
                break;
        }
        //当前阴历年份
        cYear = 1900 + m;
        //当前阴历月份
        cMonth = k - n + 1;
        //当前阴历日子
        cDay = total;

        //如果阴历cYear年有闰月，则确定是闰几月，并精确阴历月份
        if (k == 12) {
            //如果cMonth恰巧等于该年闰月，则需要标示当前阴历月份为闰月
            if (cMonth == Math.floor(CALENDAR_DATA[m] / 0x10000) + 1){
                cMonth = 1 - cMonth;
            }
            //如果cMonth大于该年闰月，则表示闰月已过，需要对cMonth减1
            if (cMonth > Math.floor(CALENDAR_DATA[m] / 0x10000) + 1){
                cMonth--;
            }
        }
        //计算时辰，夜里23点到1点为子时，每两个小时为一个时辰，用“地支”依次类推
        cHour = (int) Math.floor((calendar.get(Calendar.HOUR_OF_DAY) + 3) / 2);


        //整理输出
        year = "" + TIAN_GAN.charAt((cYear - 4) % 10) + DI_ZHI.charAt((cYear - 4) % 12);
        zodiac = "" + ZODIAC.charAt((cYear - 4) % 12);
        month = "" + (cMonth < 1 ? "闰" : "") + CHINESE_MONTH.charAt(cMonth - 1);
        day = (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "卅"));
        if (cDay % 10 != 0 || cDay == 10)
            day += NUMBER.charAt((cDay - 1) % 10);
        hour = "" + DI_ZHI.charAt((cHour - 1) % 12);
    }

    //位运算，主要用来从十六进制中得到阴历每个月份是大月还是小月
    private int getBit(long m, int n) {
        return (int) ((m >> n) & 1);
    }
}