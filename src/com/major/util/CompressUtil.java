package com.major.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-10-23
 * Time: 上午12:23
 */
@SuppressWarnings("UnusedDeclaration")
public class CompressUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private CompressUtil() {}

    /**
     * Compress files as zip.
     * @param files File list
     * @param zipFile Destination zip file
     */
    public static void zip(File files[], File zipFile) {
        if (zipFile.exists() || files == null)
            return;

        ZipArchiveOutputStream zipOut = null;
        InputStream is = null;
        try {
            zipOut = new ZipArchiveOutputStream(zipFile);
            zipOut.setUseZip64(Zip64Mode.AsNeeded);

            for (File file : files) {
                if (file == null || !file.isFile() || !file.exists())
                    continue;
                ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
                zipOut.putArchiveEntry(entry);
                is = new BufferedInputStream(new FileInputStream(file));
                IOUtils.copy(is, zipOut);
                zipOut.closeArchiveEntry();
            }
            zipOut.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(zipOut);
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Unzip a zip file.
     * @param zipFile source zip file
     * @param dir save dir
     */
    public static void unzip(File zipFile, File dir) throws IOException{
        if (!zipFile.exists())
            return;
        //if dir is not exists, then make it
        if (dir.exists() && dir.isFile())
            return;
        else if (!dir.exists())
            if (!dir.mkdir())
                return;

        InputStream is = null;
        ZipArchiveInputStream zipIn = null;
        try {
            is = new FileInputStream(zipFile);
            zipIn = new ZipArchiveInputStream(is);
            ArchiveEntry entry;
            List<String> failList = new ArrayList<>(); //to record fail-entry msg to throw
            while ((entry = zipIn.getNextEntry()) != null) {
                OutputStream os = null;
                try {
                    String path = dir.getPath().endsWith(File.separator) ? dir.getPath() : dir.getPath() + File.separator;
                    os = new BufferedOutputStream(new FileOutputStream(new File(path + entry.getName())));
                    IOUtils.copy(zipIn, os);
                } catch (IOException e) {
                    failList.add(entry.getName());
                } finally {
                    IOUtils.closeQuietly(os);
                }
            }
            if (failList.size() > 0) {
                throw new IOException("file: " + StringUtils.join(failList, ",") + " unzip failed.");
            }
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(zipIn);
        }
    }
}
