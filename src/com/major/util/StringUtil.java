package com.major.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

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
     * Convert a string to a list using the delimiter.
     * @param str original string
     * @param delimiter delimiter
     * @return List<String>
     */
    public static List<String> stringToList(String str, String delimiter) {
        if (str != null && delimiter != null) {
            String[] s = str.split(delimiter);
            return CollectionsUtil.arrayToList(s);
        }
        return null;
    }

    /**
     * Get string length in charset:GBK
     * e.g. lengthInBytes("abc住") -> 5;
     * @param string string to get length
     * @return length, on null returns 0
     */
    public static int lengthInBytes(String string) {
        if (string == null)
            return 0;
        else
            return string.getBytes(Charset.forName("GBK")).length;
    }

    /**
     * Get string length in charset:UTF-8
     * e.g. lengthInBytes("abc住") -> 6;
     * @param string string to get length
     * @return length, on null returns 0
     */
    public static int lengthInUTF8(String string) {
        if (string == null)
            return 0;
        else
            return string.getBytes(Charset.forName("UTF-8")).length;
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
