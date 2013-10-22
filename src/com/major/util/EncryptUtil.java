package com.major.util;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:08
 */
@SuppressWarnings("UnusedDeclaration")
public class EncryptUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private EncryptUtil() {}

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * Evaluate md5 value of a file
     * @param file file to process
     * @return md5 value, on error returns null
     */
    public static String md5(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Calculates the MD2 digest and returns the value as a 32 character hex string.
     * @param data Data to digest
     * @return MD2 digest as a hex string
     */
    public static String md2(String data) {
        return DigestUtils.md2Hex(data);
    }

    /**
     * Evaluate md2 value of a file
     * @param file file to process
     * @return md2 value, on error returns null
     */
    public static String md2(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.md2Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Calculates the SHA1 digest
     * @param data Data to digest
     * @return SHA1 digest as a hex string
     */
    public static String sha1(String data) {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * Evaluate SHA1 value of a file
     * @param file file to process
     * @return SHA1 value, on error returns null
     */
    public static String sha1(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.sha1Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Calculates the sha256 digest and returns the value as a 32 character hex string.
     * @param data Data to digest
     * @return sha256 digest as a hex string
     */
    public static String sha256(String data) {
        return DigestUtils.sha256Hex(data);
    }

    /**
     * Evaluate sha256 value of a file
     * @param file file to process
     * @return sha256 value, on error returns null
     */
    public static String sha256(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.sha256Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Calculates the sha384 digest and returns the value as a 32 character hex string.
     * @param data Data to digest
     * @return sha384 digest as a hex string
     */
    public static String sha384(String data) {
        return DigestUtils.sha384Hex(data);
    }

    /**
     * Evaluate sha384 value of a file
     * @param file file to process
     * @return sha384 value, on error returns null
     */
    public static String sha384(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.sha384Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Calculates the sha512 digest and returns the value as a 32 character hex string.
     * @param data Data to digest
     * @return sha512 digest as a hex string
     */
    public static String sha512(String data) {
        return DigestUtils.sha512Hex(data);
    }

    /**
     * Evaluate sha512 value of a file
     * @param file file to process
     * @return sha512 value, on error returns null
     */
    public static String sha512(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.sha512Hex(fis);
        } catch (IOException e) {
            return null;
        } finally {
            FileUtil.closeIgnoreException(fis);
        }
    }

    /**
     * Encode a string to base64.
     * @param data data to encode
     * @return String
     */
    public static String encodeBase64(String data) {
        if (data == null)
            return null;
        return Base64.encodeBase64String(data.getBytes());
    }

    /**
     * Encode a file
     * @param original original file
     * @param destination destination
     */
    public static void encodeBase64(File original, File destination) {
        if (original == null || destination == null)
            return;

        FileInputStream fis = null;
        Base64InputStream bis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(original);
            bis = new Base64InputStream(fis, true);
            fos = new FileOutputStream(destination);
            IOUtils.copy(bis, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeIgnoreException(fis);
            FileUtil.closeIgnoreException(bis);
            FileUtil.closeIgnoreException(fos);
        }
    }

    /**
     * Decode a string to base64.
     * @param data data to decode
     * @return String
     */
    public static String decodeBase64(String data) {
        if (Base64.isBase64(data)) {
            return new String(Base64.decodeBase64(data));
        } else {
            return null;
        }
    }

    /**
     * Decode a file
     * @param original original file
     * @param destination destination
     */
    public static void decodeBase64(File original, File destination) {
        if (original == null || destination == null)
            return;

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(original);
            fos = new FileOutputStream(destination);
            com.sun.org.apache.xml.internal.security.utils.Base64.decode(fis, fos);
        } catch (IOException | Base64DecodingException e) {
            e.printStackTrace();
        }finally {
            FileUtil.closeIgnoreException(fis);
            FileUtil.closeIgnoreException(fos);
        }
    }
}
