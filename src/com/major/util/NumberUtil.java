package com.major.util;

import bsh.EvalError;
import bsh.Interpreter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 上午11:45
 */
@SuppressWarnings("UnusedDeclaration")
public class NumberUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private NumberUtil() {}

    public enum Ary {
        BINARY(2), OCTAL(8), DECIMAL(10), HEX(16);

        private int value = 0;

        private Ary(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * To judge an object is numeric or not.(enable comma)
     * @param string String
     * @param allowHead0 whether allow 0 at head,e.g. 00012.34
     * @return Boolean
     */
    public static boolean isNumeric(String string, boolean allowHead0) {
        if (string == null)
            throw new IllegalArgumentException("empty string");

        if (allowHead0 || !Pattern.compile("^[-+]?[0].*").matcher(string).matches()) {
            Pattern pattern = Pattern.compile("^([-+]?[0-9]+([.][0-9]+)?)$");
            if (pattern.matcher(string).matches())
                return true;
            else {
                pattern = Pattern.compile("^([-+]?[0-9]{1,3}([,][0-9]{3})*([.][0-9]+)?)$");
                return pattern.matcher(string).matches();
            }
        } else {
            return false;
        }
    }

    /**
     * To judge a number is odd or not.
     * @param i number to judge
     * @return Boolean
     */
    public static boolean isOdd(int i) {
        return (i & 1) != 0;
    }
    /**
     * To judge a number is an even one or not.
     * @param i number to judge
     * @return Boolean
     */
    public static boolean isEven(int i) {
        return !isOdd(i);
    }

    /**
     * Convert an Object to Integer. If o = null or any exception occurs, returns the default value(dv).
     * @param o Object value
     * @param dv Default value
     * @return Integer
     */
    public static Integer safeToInteger(Object o, Integer dv){
        Integer r = dv;
        if (o != null){
            try{
                r = new Integer(String.valueOf(o));
            }
            catch (Exception ex){
                //ignore
            }
        }
        return r;
    }

    /**
     * Convert an Object to Float. If o = null or any exception occurs, returns the default value(dv).
     * @param o Object value
     * @param dv Default value
     * @return Float
     */
    public static Float safeToFloat(Object o, Float dv){
        Float r = dv;
        if (o != null){
            try{
                r = new Float(String.valueOf(o));
            }
            catch(Exception ex){
                //ignore
            }
        }
        return r;
    }
    /**
     * Convert an Object to Float. If o = null or any exception occurs, returns the default value(dv).
     * @param o Object value
     * @param dv Default value
     * @param round Valid number of decimal place.
     * @return Float
     */
    public static Float safeToFloat(Object o, Float dv, int round){
        Float r = dv;
        if (o != null){
            try{
                r = new Float(String.valueOf(o));
            }
            catch(Exception ex){
                //ignore
            }
        }
        r = new BigDecimal(r).setScale(round, BigDecimal.ROUND_HALF_UP).floatValue();
        return r;
    }

    /**
     * Convert an Object to Double. If o = null or any exception occurs, returns the default value(dv).
     * @param o Object value
     * @param dv Default value
     * @return Double
     */
    public static Double safeToDouble(Object o, Double dv){
        Double r = dv;
        if (o != null){
            try{
                r = new Double(String.valueOf(o));
            }
            catch(Exception ex){
                //ignore
            }
        }
        return r;
    }
    /**
     * Convert an Object to Double. If o = null or any exception occurs, returns the default value(dv).
     * @param o Object value
     * @param dv Default value
     * @param round Valid number of decimal place.
     * @return Double
     */
    public static Double safeToDouble(Object o, Double dv, int round){
        Double r = dv;
        if (o != null){
            try{
                r = new Double(String.valueOf(o));
            }
            catch(Exception ex){
                //ignore
            }
        }
        r = new BigDecimal(r).setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
        return r;
    }

    /**
     * Convert as Sting value to BigDecimal, supports ',' and '%' in value.
     * @param value Sting value
     * @param round Valid number of decimal place.
     * @return BigDecimal
     */
    public static BigDecimal stringToBigDecimal(String value, int round) {
        try {
            if (!value.contains("%")) {
                return new BigDecimal(Double.valueOf(value.trim().replace(",", ""))).setScale(round, BigDecimal.ROUND_HALF_UP);
            } else {
                return new BigDecimal(Double.valueOf(value.trim().replace("%", "")) / 100).setScale(round, BigDecimal.ROUND_HALF_UP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param a BigDecimal
     * @param b BigDecimal
     * @param round Valid number of decimal place.
     * @return a - b
     */
    public static BigDecimal aSubtractB(BigDecimal a, BigDecimal b, int round) {
        try {
            return a.subtract(b).setScale(round, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param a BigDecimal
     * @param b BigDecimal
     * @param round Valid number of decimal place.
     * @return a / b
     */
    public static BigDecimal aDivideB(BigDecimal a, BigDecimal b, int round) {
        try {
            return a.divide(b, round).setScale(round, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 算数平均数
     * x = (x1 + x2 + x3) / 3;
     * @param arr double sequence
     * @return Arithmetic mean
     */
    public static double avgInArithmetic(double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        double sum = 0.00;
        int T = arr.length;
        for (double d : arr) {
            sum += d;
        }
        return sum / T;
    }

    /**
     * 几何平均数
     * x = (x1 * x2 * x3) ^ (1 / 3)
     * @param arr double sequence
     * @return Geometric mean
     */
    public static double avgInGeometric(double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        double r = 1;
        for (double i : arr) {
            r *= Math.pow(i, 1D / arr.length);
        }
        return r;
    }

    /**
     * 调和平均数
     * x = 3 / (1 / x1 + 1 / x2 + 1 / x3)
     * @param arr double sequence
     * @return Harmonic mean
     */
    public static double avgInHarmonic (double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        double r = 0;
        for (double i : arr) {
            r += 1D / i;
        }
        r = arr.length / r;
        return r;
    }

    /**
     * 平方平均数
     * x = ((x1 ^2 + x2 ^ 2 + x3 ^ 2) / 3) ^ (1 / 2)
     * @param arr double sequence
     * @return Squared mean
     */
    public static double avgInSquared (double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        double r = 0;
        for (double i : arr) {
            r += Math.pow(i, 2);
        }
        r /= arr.length;
        r = Math.sqrt(r);
        return r;
    }

    /**
     * median number(中位数)
     * @param arr double sequence
     * @return the median of the arr
     */
    public static double median(double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        if (isOdd(arr.length))
            return arr[(arr.length + 1) / 2];
        else
            return (arr[arr.length / 2] + arr[arr.length / 2 + 1]) / 2;
    }

    /**
     * 方差
     * s2 = ((x1 - x)^2 + (x2 - x)^2 + (x3 - x)^2) / n
     * @param arr double sequence
     * @return Variance
     */
    public static double variance(double[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("empty arr");

        double r = 0;
        double avg = avgInArithmetic(arr);
        for (double i : arr) {
            r = r + Math.pow(i - avg, 2);
        }
        r = r / arr.length;
        return r;
    }

    /**
     * 标准差
     * s = variance ^ 0.5
     * @param arr double sequence
     * @return Standard Deviation
     */
    public static double standardDeviation(double[] arr) {
        return Math.sqrt(variance(arr));
    }

    /**
     * Format a number to specified pattern without location message.
     * @param number number to format
     * @param comma if you allow comma in the expression
     * @param round digit to reserve
     * @return String
     */
    public static String formatNumber(Number number, boolean comma, int round) {
        String format = comma ? "#,##0" : "0";
        if (round > 0) {
            format = format + ".";
            for (int i = 0; i < round; i++) {
                format = format + "0";
            }
        }
        return new DecimalFormat(format).format(number);
    }

    /**
     * Compute the file size in Byte to a suitable unit.
     * @param size size in byte
     * @return suitable size with the unit
     */
    public static String formatFileSize(double size) {
        if (size < 0)
            throw new IllegalArgumentException("Filesize < 0");

        String[] rank = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        int c = 0;
        while (size >= 1000) {
            size = size / 1024;
            c++;
            if (c >= rank.length - 1)
                break;
        }
        return new DecimalFormat("0.##").format(size) + rank[c];
    }

    /**
     * Convert a number in origin ary to the destination one.
     * @param number number to convert
     * @param origin origin ary
     * @param destination destination ary
     * @return String
     */
    public static String convertAry(String number, Ary origin, Ary destination) {
        if (number == null || "".equals(number))
            throw new IllegalArgumentException("empty number");
        String string = number.toLowerCase();
        switch (origin) {
            case BINARY:
                if (!Pattern.compile("[01]*").matcher(string).matches())
                    throw new IllegalArgumentException("Number is not binary."); break;
            case OCTAL:
                if (!Pattern.compile("[0-7]*").matcher(string).matches())
                    throw new IllegalArgumentException("Number is not octal."); break;
            case DECIMAL:
                if (!Pattern.compile("[0-9]*").matcher(string).matches())
                    throw new IllegalArgumentException("Number is not decimal."); break;
            case HEX:
                if (!Pattern.compile("[0-9a-f]*").matcher(string).matches())
                    throw new IllegalArgumentException("Number is not hex."); break;
        }

        boolean isNegative = string.charAt(0) == '-';
        String str = "";
        int decimal;
        if (isNegative)
            decimal = Integer.valueOf(string.substring(1, string.length()), origin.value());
        else
            decimal = Integer.valueOf(string, origin.value());
        switch (destination) {
            case BINARY: str = Integer.toBinaryString(decimal); break;
            case OCTAL: str = Integer.toOctalString(decimal); break;
            case DECIMAL: str = Integer.toString(decimal); break;
            case HEX: str = Integer.toHexString(decimal); break;
        }
        return isNegative ? "-" + str : str;
    }

    /**
     * it doesn't affect the origin list at all.
     * @param list list to sort
     * @param asc asc or desc(default: asc)
     * @return a sorted list
     */
    public static <T extends Comparable> List<T> sort(List<T> list, boolean asc) {
        if (list == null)
            throw new IllegalArgumentException("null list");
        List<T> _list = new ArrayList<>();
        _list.addAll(list);
        Collections.sort(_list);
        if (!asc)
            Collections.reverse(_list);
        return _list;
    }
    public static List sort(List list) {
        return sort(list, true);
    }

    /**
     * it doesn't affect the origin list at all.
     * @param c array to sort
     * @param asc asc or desc(default: asc)
     * @return a sorted array
     */
    public static <T extends Comparable> T[] sort(T[] c, boolean asc) {
        if (c == null)
            throw new IllegalArgumentException("null array");
        List<T> list = Arrays.asList(c);
        List<T> sortedList = sort(list, asc);
        return (T[]) sortedList.toArray();
    }
    public static <T extends Comparable> T[] sort(T[] c) {
        return sort(c, true);
    }

    /**
     * evaluate a expression
     * @param exp expression
     * @return value
     */
    public static String eval(String exp) {
        Interpreter in = new Interpreter();
        try {
            return in.eval(exp).toString();
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
        return null;
    }

    /**
     * Convert a number to Chinese pattern.
     * @param number number to convert
     * @param round if isCurrency round = 2
     * @param isCurrency if the number is currency
     * @param traditional returns the Chinese pattern in traditional word or not
     * @return String
     */
    public static String convertToChinese(Number number, int round, boolean isCurrency, boolean traditional) {
        if (number.doubleValue() < 0)
            throw new IllegalArgumentException("number < 0");

        //------------------------------------------------------------------------------------------------------------
        //init base name
        String str = isCurrency ? formatNumber(number, false, 2) : formatNumber(number, false, round);
        String[] numbers = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String _numbers[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String digits[] = {"", "十", "百", "千"};
        String _digits[] = {"", "拾", "佰", "仟"};
        String units[] = {"", "万", "亿", "兆", "吉", "太"};
        String currencyUnit[] = {"元", "角", "分", "毫", "厘"};
        String _currencyUnit[] = {"圆", "角", "分", "毫", "厘"};
        String point = "点";
        if (traditional) {
            numbers = _numbers;
            digits = _digits;
            currencyUnit = _currencyUnit;
        }
        //init bit integer and decimal bit
        int pointIndex = str.indexOf(".");
        int[] integerBit, decimalBit;
        if (pointIndex > 0) {
            //bit integer init
            integerBit = new int[pointIndex];
            for (int i = 0; i < integerBit.length; i++) {
                integerBit[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
            }
            //decimal bit init
            decimalBit = new int[str.length() - pointIndex - 1];
            for (int i = 0; i < decimalBit.length; i++) {
                decimalBit[i] = Integer.parseInt(String.valueOf(str.charAt(i + pointIndex + 1)));
            }
        } else {
            //bit integer init
            integerBit = new int[str.length()];
            for (int i = 0; i < integerBit.length; i++) {
                integerBit[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
            }
            //decimal bit init
            decimalBit = null;
        }

        //------------------------------------------------------------------------------------------------------------
        //convert bit integer
        if (integerBit.length == 0) return numbers[0];
        StringBuilder sb = new StringBuilder();
        // zero表示是否遇到零；nonZero表示一个四位分组是否有效（不全为零）
        boolean zero = false, nonZero = false;
        for (int i = 0; i < integerBit.length; i++) {
            int unitIndex = (integerBit.length - 1 - i) / 4;  // 计算四位组单位
            int digitIndex = (integerBit.length - 1 - i) % 4; // 计算位单位

            if (integerBit[i] == 0) {
                zero = true;
            } else {
                nonZero = true;
                if (zero) {
                    sb.append(numbers[0]);
                    zero = false;
                }
                sb.append(numbers[integerBit[i]]).append(digits[digitIndex]);
            }

            if (digitIndex == 0) { // 一个四位组结束时
                if (nonZero) {
                    sb.append(units[unitIndex]);
                    zero = false; // 若输出四位组单位，先前遇到的零不读
                }
                nonZero = false; // 重置
            }
        }

        //------------------------------------------------------------------------------------------------------------
        //convert decimal bit
        if (isCurrency) {
            sb.append(currencyUnit[0]);
            int decimalSum = 0;
            for (int i : decimalBit) {
                decimalSum += i;
            }
            if (decimalSum > 0) {
                zero = false;
                for (int i = 0; i < decimalBit.length; i++) {
                    if (decimalBit[i] == 0)
                        zero = true;
                    else {
                        if (zero)
                            sb.append(numbers[0]);
                        sb.append(numbers[decimalBit[i]]);
                        sb.append(currencyUnit[i + 1]);
                    }
                }
            }
        } else {
            sb.append(point);
            if (decimalBit != null)
                for (int i : decimalBit) {
                    sb.append(numbers[i]);
                }
        }

        return sb.toString();
    }
}
