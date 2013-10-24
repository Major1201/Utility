package com.major.util;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.compress.archivers.jar.*;
import org.apache.commons.compress.archivers.ar.*;
import org.apache.commons.compress.archivers.dump.*;
import org.apache.commons.compress.archivers.cpio.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
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
        try {
            zipOut = new ZipArchiveOutputStream(zipFile);
            zipOut.setUseZip64(Zip64Mode.AsNeeded);
            compress(files, zipOut, ZipArchiveEntry.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(zipOut);
        }
    }

    /**
     * Unzip a zip file.
     * @param zipFile source zip file
     * @param dir save dir
     */
    public static void unzip(File zipFile, File dir) throws IOException {
        if (!zipFile.exists())
            return;

        InputStream is = null;
        ZipArchiveInputStream zipIn = null;
        try {
            is = new FileInputStream(zipFile);
            zipIn = new ZipArchiveInputStream(is);
            decompress(zipIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(zipIn);
        }
    }

    /**
     * Compress files as tar.
     * @param files File list
     * @param tarFile Destination tar file
     */
    public static void tar(File files[], File tarFile) {
        if (tarFile.exists() || files == null)
            return;

        TarArchiveOutputStream tarOut = null;
        try {
            tarOut = new TarArchiveOutputStream(new FileOutputStream(tarFile));
            compress(files, tarOut, TarArchiveEntry.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(tarOut);
        }
    }

    /**
     * Decompress a tar file.
     * @param tarFile source tar file
     * @param dir save dir
     */
    public static void unTar(File tarFile, File dir) throws IOException {
        if (!tarFile.exists())
            return;

        InputStream is = null;
        TarArchiveInputStream tarIn = null;
        try {
            is = new FileInputStream(tarFile);
            tarIn = new TarArchiveInputStream(is);
            decompress(tarIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(tarIn);
        }
    }

    /**
     * Compress files as jar.
     * @param files File list
     * @param jarFile Destination jar file
     */
    public static void jar(File files[], File jarFile) {
        if (jarFile.exists() || files == null)
            return;

        JarArchiveOutputStream jarOut = null;
        try {
            jarOut = new JarArchiveOutputStream(new FileOutputStream(jarFile));
            jarOut.setUseZip64(Zip64Mode.AsNeeded);
            compress(files, jarOut, ZipArchiveEntry.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(jarOut);
        }
    }

    /**
     * Decompress a jar file.
     * @param jarFile source jar file
     * @param dir save dir
     */
    public static void unJar(File jarFile, File dir) throws IOException {
        if (!jarFile.exists())
            return;

        InputStream is = null;
        JarArchiveInputStream jarIn = null;
        try {
            is = new FileInputStream(jarFile);
            jarIn = new JarArchiveInputStream(is);
            decompress(jarIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(jarIn);
        }
    }

    /**
     * Compress files as ar.
     * @param files File list
     * @param arFile Destination ar file
     */
    public static void ar(File files[], File arFile) {
        if (arFile.exists() || files == null)
            return;

        ArArchiveOutputStream arOut = null;
        try {
            arOut = new ArArchiveOutputStream(new FileOutputStream(arFile));
            compress(files, arOut, ArArchiveEntry.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(arOut);
        }
    }

    /**
     * Decompress a ar file.
     * @param arFile source ar file
     * @param dir save dir
     */
    public static void unAr(File arFile, File dir) throws IOException {
        if (!arFile.exists())
            return;

        InputStream is = null;
        ArArchiveInputStream arIn = null;
        try {
            is = new FileInputStream(arFile);
            arIn = new ArArchiveInputStream(is);
            decompress(arIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(arIn);
        }
    }

    /**
     * Decompress a dump file.
     * @param dumpFile source dump file
     * @param dir save dir
     */
    public static void unDump(File dumpFile, File dir) throws IOException, ArchiveException {
        if (!dumpFile.exists())
            return;

        InputStream is = null;
        DumpArchiveInputStream dumpIn = null;
        try {
            is = new FileInputStream(dumpFile);
            dumpIn = new DumpArchiveInputStream(is);
            decompress(dumpIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(dumpIn);
        }
    }

    /**
     * Compress files as cpio.
     * @param files File list
     * @param cpioFile Destination cpio file
     */
    public static void cpio(File files[], File cpioFile) {
        if (cpioFile.exists() || files == null)
            return;

        CpioArchiveOutputStream cpioOut = null;
        try {
            cpioOut = new CpioArchiveOutputStream(new FileOutputStream(cpioFile));
            compress(files, cpioOut, CpioArchiveEntry.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(cpioOut);
        }
    }

    /**
     * Decompress a cpio file.
     * @param cpioFile source cpio file
     * @param dir save dir
     */
    public static void unCpio(File cpioFile, File dir) throws IOException {
        if (!cpioFile.exists())
            return;

        InputStream is = null;
        CpioArchiveInputStream cpioIn = null;
        try {
            is = new FileInputStream(cpioFile);
            cpioIn = new CpioArchiveInputStream(is);
            decompress(cpioIn, dir);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(cpioIn);
        }
    }

    /**
     * Compress file list.
     * @param files File list
     * @param archiveOutputStream archiveOutputStream
     * @param entryClazz ArchiveEntry detail
     * @throws IOException
     */
    private static void compress(File files[], ArchiveOutputStream archiveOutputStream, Class<? extends ArchiveEntry> entryClazz) throws IOException{
        InputStream is = null;
        try {
            for (File file : files) {
                if (file == null || !file.isFile() || !file.exists())
                    continue;
                ArchiveEntry entry = entryClazz.getDeclaredConstructor(File.class, String.class).newInstance(file, file.getName());
                archiveOutputStream.putArchiveEntry(entry);
                is = new BufferedInputStream(new FileInputStream(file));
                IOUtils.copy(is, archiveOutputStream);
                archiveOutputStream.closeArchiveEntry();
            }
            archiveOutputStream.finish();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Decompress an archive input stream.
     * @param archiveInputStream archiveInputStream
     * @param dir save dir
     * @throws IOException
     */
    private static void decompress(ArchiveInputStream archiveInputStream, File dir) throws IOException {
        //if dir is not exists, then make it
        if (dir.exists() && dir.isFile())
            return;
        else if (!dir.exists())
            if (!dir.mkdir())
                return;

        ArchiveEntry entry;
        List<String> failList = new ArrayList<>(); //to record fail-entry msg to throw
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            OutputStream os = null;
            try {
                String path = dir.getPath().endsWith(File.separator) ? dir.getPath() : dir.getPath() + File.separator;
                os = new BufferedOutputStream(new FileOutputStream(new File(path + entry.getName())));
                IOUtils.copy(archiveInputStream, os);
            } catch (IOException e) {
                failList.add(entry.getName());
            } finally {
                IOUtils.closeQuietly(os);
            }
        }
        if (failList.size() > 0) {
            throw new IOException("File: " + StringUtils.join(failList, ",") + " decompress failed.");
        }
    }
}
