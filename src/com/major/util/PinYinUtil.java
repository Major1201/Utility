package com.major.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:17
 */
@SuppressWarnings("UnusedDeclaration")
public class PinYinUtil {

    private static final Logger LOGGER = LogManager.getLogger(PinYinUtil.class);

    /**
     * Get full Pinyin with tone
     * e.g. getFullPinyinWithTone("获取拼音") -> huò qŭ pīn yīn
     * @param origin origin string
     * @return String
     */
    public static String getFullPinyinWithTone(String origin) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return getFullPinyin(origin, format);
    }

    /**
     * Get full Pinyin without tone
     * e.g. getFullPinyinWithoutTone("获取拼音") -> huo qu pin yin
     * @param origin origin string
     * @return String
     */
    public static String getFullPinyinWithoutTone(String origin) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return getFullPinyin(origin, format);
    }

    /**
     * Get full Pinyin with customized HanyuPinyinOutputFormat
     * @param origin origin string
     * @return String
     */
    public static String getFullPinyin(String origin, HanyuPinyinOutputFormat format) {
        if (origin == null)
            return null;

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            try {
                char point = origin.charAt(i);
                if (Character.toString(point).matches("[\\u4E00-\\u9FA5]+"))
                    str.append(PinyinHelper.toHanyuPinyinStringArray(point, format)[0]).append(" ");
                else
                    str.append(point);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                LOGGER.error("Error to get pinyin", e);
            }
        }
        return str.toString().trim();
    }

    /**
     * Get pinyin initials
     * e.g. getPinyinInitials("获取拼音") -> hqpy
     * @param origin origin string
     * @return String
     */
    public static String getPinyinInitials(String origin) {
        if (origin == null)
            return null;

        StringBuilder str = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        for (int i = 0; i < origin.length(); i++) {
            try {
                char point = origin.charAt(i);
                if (Character.toString(point).matches("[\\u4E00-\\u9FA5]+"))
                    str.append(PinyinHelper.toHanyuPinyinStringArray(point, format)[0].charAt(0));
                else
                    str.append(point);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                LOGGER.error("Error to get pinyin", e);
            }
        }
        return str.toString();
    }
}
