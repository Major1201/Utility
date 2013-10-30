package com.major.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-10-30
 * Time: 下午4:43
 */
@SuppressWarnings("UnusedDeclaration")
public class CollectionsUtil {
    /**
     * Make a list without duplicates.
     * @param list list to process
     * @return a list without duplicates
     */
    public static <E> List<E> withoutDuplicates(List<E> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * Convert an array to a list
     * @param array an array
     * @return List
     */
    public static <T> List<T> arrayToList(T array[]) {
        if (array != null) {
            List<T> list = new ArrayList<>();
            list.addAll(Arrays.asList(array));
            return list;
        }
        return null;
    }
}
