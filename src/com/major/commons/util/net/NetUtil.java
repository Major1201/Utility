package com.major.commons.util.net;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: Minjie
 * Date: 13-10-31
 * Time: 下午5:44
 */
public class NetUtil {

    private static final Logger LOGGER = LogManager.getLogger(NetUtil.class);

    /**
     * Download a file with a url string.
     * @param urlString url string
     * @param file file to save it
     * @return true:success while false:failed
     */
    public static boolean download(String urlString, File file) {
        return download(urlString, file, null);
    }

    /**
     * Download a file with a url string and run your expressions on progress changed.
     * @param urlString url string
     * @param file file to save it
     * @param executor to do what you want while progress changes
     * @return true:success while false:failed
     */
    public static boolean download(String urlString, File file, ProgressExecutor executor) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            LOGGER.error("Error to create URL object:" + urlString, e);
            return false;
        }

        InputStream input = null;
        FileOutputStream output = null;
        try {
            URLConnection connection = url.openConnection();
            long totalSize = connection.getContentLengthLong();
            input = connection.getInputStream();
            output = new FileOutputStream(file);
            //copy stream
            if (executor != null) {
                byte buffer[] = new byte[1024];
                long count = 0;
                int n;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                    executor.progressChanged(count, totalSize);
                }
            } else {
                IOUtils.copy(input, output);
            }
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found:" + file.toString(), e);
            return false;
        } catch (IOException e) {
            LOGGER.error("IO exception:" + urlString, e);
            return false;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * Get net file size, on error returns -1.
     * @param urlString url string
     * @return size in byte
     */
    public static long getFileSize(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            LOGGER.error("Error to create URL object:" + urlString, e);
            return -1;
        }

        try {
            URLConnection connection = url.openConnection();
            return connection.getContentLengthLong();
        } catch (IOException e) {
            LOGGER.error("IO exception:" + urlString, e);
            return -1;
        }
    }
}
