package com.major.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:17
 */
@SuppressWarnings("UnusedDeclaration")
public class PinYinUtil {
    public static String convertToPinyin(String origin) {
        if (origin == null)
            return null;

        StringBuilder str = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        for (int i = 0; i < origin.length(); i++) {
            try {
                char point = origin.charAt(i);
                if (Character.toString(point).matches("[\\u4E00-\\u9FA5]+"))
                    str.append(PinyinHelper.toHanyuPinyinStringArray(point, format)[0]).append(" ");
                else
                    str.append(point);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        return str.toString().trim();
    }
}
