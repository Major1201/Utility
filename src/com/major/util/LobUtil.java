package com.major.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;

/**
 * User: Minjie
 * Date: 13-9-1
 * Time: 下午9:35
 */
@SuppressWarnings("UnusedDeclaration")
public class LobUtil {
    public static String clobToString(Clob clob) {
        StringBuilder content = new StringBuilder();
        if (clob != null) {
            Reader in = null;
            try {
                int p;
                char[] buff = new char[1024];
                in = clob.getCharacterStream();
                while ((p = in.read(buff, 0, 1024)) != -1){
                    content.append(new String(buff, 0, p));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content.toString();
    }
}
