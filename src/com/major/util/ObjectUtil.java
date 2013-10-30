package com.major.util;

/**
 * User: 朱敏捷
 * Date: 13-10-30
 * Time: 下午4:53
 */
@SuppressWarnings("UnusedDeclaration")
public class ObjectUtil {
    @SafeVarargs
    public static <T> boolean in(T object, T... objects) {
        boolean flag = false;
        if (object != null)
            for (T obj : objects) {
                if (object.equals(obj)) {
                    flag = true;
                    break;
                }
            }
        return flag;
    }
}
