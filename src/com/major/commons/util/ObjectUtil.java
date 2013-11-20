package com.major.commons.util;

/**
 * User: Minjie
 * Date: 13-10-30
 * Time: 下午4:53
 */
@SuppressWarnings("UnusedDeclaration")
public class ObjectUtil {
    /**
     * Return whether object in object list.
     * @param object source
     * @param objects object list
     * @return boolean
     */
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
