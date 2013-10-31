package com.major.util;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
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
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            LOGGER.error("Error to create URL object:" + urlString, e);
            return false;
        }

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            URLConnection connection = url.openConnection();
            LOGGER.debug(connection.getContentLength());
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found:" + file.toString(), e);
            return false;
        } catch (IOException e) {
            LOGGER.error("IO exception:" + urlString, e);
            return false;
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
