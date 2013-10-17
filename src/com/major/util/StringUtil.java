package com.major.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午12:43
 */
@SuppressWarnings("UnusedDeclaration")
public class StringUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private StringUtil() {}

    /*
    it's a regexp that matches a legal ip address.
     */
    public static final String REG_IP = "(([2][5][0-5]|[2][0-4][0-9]|[1][0-9]{2}|[1-9][0-9]|[0-9])[.]){3}([2][5][0-5]|[2][0-4][0-9]|[1][0-9]{2}|[1-9][0-9]|[0-9])";

    /**
     * It no longer trims the String.
     * @param str the String to check, may be null
     * @return if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * Convert an object to string safely.
     * @param o object to convert
     * @param dv default value
     * @return String
     */
    public static String safeToString(Object o, String dv) {
        String r = dv;
        if (o != null) {
            r = String.valueOf(o);
        }
        return r;
    }

    /**
     * Joining an array using specified delimiter.
     * @param array array to join
     * @param delimiter delimiter
     * @param startDeli enable delimiter at start or not, default is false
     * @param endDeli enable delimiter at end or not, default is false
     * @return String
     */
    public static String join(Object[] array, String delimiter, boolean startDeli, boolean endDeli) {
        if (array == null || array.length <= 0)
            return "";
        String deli = (delimiter == null) ? "" : delimiter;
        StringBuffer buffer = startDeli ? new StringBuffer(deli) : new StringBuffer();
        for (Object o : array) {
            buffer.append(o.toString());
            buffer.append(deli);
        }
        if (!endDeli)
            buffer.delete(buffer.length() - deli.length(), buffer.length());
        return buffer.toString();
    }
    public static String join(Object[] array, String delimiter) {
        return join(array, delimiter, false, false);
    }
    public static String join(List list, String delimiter, boolean startDeli, boolean endDeli) {
        return join(list.toArray(), delimiter, startDeli, endDeli);
    }
    public static String join(List list, String delimiter) {
        return join(list.toArray(), delimiter);
    }

    /**
     * Convert a string array to a list
     * @param array String[]
     * @return List<String>
     */
    public static List<String> arrayToList(String array[]) {
        if (array != null) {
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(array));
            return list;
        }
        return null;
    }

    /**
     * Convert a string to a list using the delimiter.
     * @param str original string
     * @param delimiter delimiter
     * @return List<String>
     */
    public static List<String> stringToList(String str, String delimiter) {
        if (str != null && delimiter != null) {
            String[] s = str.split(delimiter);
            return arrayToList(s);
        }
        return null;
    }

    /**
     * Get subString from one to another.
     * e.g. getBetween("hello<world@mysite.com>", "<", ">") -> "world@mysite.com"
     * @param original string to cut
     * @param start start string
     * @param end end string
     * @return String
     */
    public static String getBetween(String original, String start, String end) {
        Matcher matcher = Pattern.compile(start + ".*" + end).matcher(original);
        if (matcher.matches())
            return original.substring(matcher.regionStart() + start.length(), matcher.regionEnd() - end.length());
        else
            return null;
    }

    /**
     * To build a random string, may be a password.
     * @param length the length you want to build
     *               If allowDuplicate is false, the length is limited.{
     *               usingNumber    usingCapital    usingLowerCase  usingSignal     usingBlank      max(length)
     *                  10              26              26              32              1               95
     *               if you use true, you should add that number to see the max value of length param.}
     * @param usingNumber using number in the base-random string
     * @param usingCapital using capital in the base-random string
     * @param usingLowerCase using lower case in the base-random string
     * @param usingSignal using signal in the base-random string
     * @param usingBlank  using blank in the base-random string
     * @param allowDuplicate to judge whether you allow the string appears duplicated chars
     * @return a random string
     */
    public static String buildRandomString(int length, boolean usingNumber, boolean usingCapital, boolean usingLowerCase,
                                         boolean usingSignal, boolean usingBlank, boolean allowDuplicate) {
        StringBuilder str = new StringBuilder();
        if (usingNumber) str.append("0123456789");
        if (usingCapital) str.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (usingLowerCase) str.append("abcdefghijklmnopqrstuvwxyz");
        if (usingSignal) str.append("`~!@#$%^&*()-=_+[]{}\\|;:'\",.<>/?");
        if (usingBlank) str.append(" ");

        if (str.length() == 0)
            return "";
        else {
            StringBuilder r = new StringBuilder();
            int len = str.length();
            Random random = new Random(System.currentTimeMillis());
            if (allowDuplicate) {
                for (int i = 0; i < length; i++)
                    r.append(str.charAt(random.nextInt(len)));
                return r.toString();
            } else {
                if (length <= len) {
                    for (int i = 0; i < length; i++) {
                        int randomInt = random.nextInt(len - i);
                        r.append(str.charAt(randomInt));
                        str.deleteCharAt(randomInt);
                    }
                    return r.toString();
                } else {
                    throw new IllegalArgumentException("length is too long to build a non-duplicated string");
                }
            }
        }
    }
    public static String buildRandomString(int length) {
        return buildRandomString(length, true, true, true, false, false, true);
    }
}
