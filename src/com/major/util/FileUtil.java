package com.major.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:09
 */
@SuppressWarnings("UnusedDeclaration")
public class FileUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private FileUtil() {}

    public static void closeIgnoreException(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {}
        }
    }
}
