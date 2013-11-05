package com.major.commons.util;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.compress.archivers.jar.*;
import org.apache.commons.compress.archivers.ar.*;
import org.apache.commons.compress.archivers.dump.*;
import org.apache.commons.compress.archivers.cpio.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    public static void zip(List<File> files, File zipFile) {
        if (zipFile == null || zipFile.exists() || files == null)
            throw new IllegalArgumentException();

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
     * Compress a directory
     * @param dir directory target
     * @param zipFile Destination compress file
     */
    public static void zip(File dir, File zipFile) {
        if (zipFile == null || zipFile.exists() || dir == null || !dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException();

        Map<File, String> map = getDirFileEntryMap(dir);
        if (map != null)
            zip(map, zipFile);
    }

    /**
     * Compress files using File-EntryName map.
     * @param fileEntryMap a File-EntryName map. e.g.<File, String>(new File("/test/my.jpg"), "/documents/my.jpg")
     * @param zipFile Destination compressed file
     */
    public static void zip(Map<File, String> fileEntryMap, File zipFile) {
        if (zipFile == null || zipFile.exists() || fileEntryMap == null)
            throw new IllegalArgumentException();

        ZipArchiveOutputStream zipOut = null;
        try {
            zipOut = new ZipArchiveOutputStream(zipFile);
            zipOut.setUseZip64(Zip64Mode.AsNeeded);
            compress(fileEntryMap, zipOut, ZipArchiveEntry.class);
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
        if (zipFile == null || !zipFile.exists())
            throw new IllegalArgumentException();

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
    public static void tar(List<File> files, File tarFile) {
        if (tarFile == null || tarFile.exists() || files == null)
            throw new IllegalArgumentException();

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
     * Compress a directory
     * @param dir directory target
     * @param tarFile Destination compress file
     */
    public static void tar(File dir, File tarFile) {
        if (tarFile == null || tarFile.exists() || dir == null || !dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException();

        Map<File, String> map = getDirFileEntryMap(dir);
        if (map != null)
            tar(map, tarFile);
    }

    /**
     * Compress files using File-EntryName map.
     * @param fileEntryMap a File-EntryName map. e.g.<File, String>(new File("/test/my.jpg"), "/documents/my.jpg")
     * @param tarFile Destination compressed file
     */
    public static void tar(Map<File, String> fileEntryMap, File tarFile) {
        if (tarFile == null || tarFile.exists() || fileEntryMap == null)
            throw new IllegalArgumentException();

        TarArchiveOutputStream tarOut = null;
        try {
            tarOut = new TarArchiveOutputStream(new FileOutputStream(tarFile));
            compress(fileEntryMap, tarOut, TarArchiveEntry.class);
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
        if (tarFile == null || !tarFile.exists())
            throw new IllegalArgumentException();

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
    public static void jar(List<File> files, File jarFile) {
        if (jarFile == null || jarFile.exists() || files == null)
            throw new IllegalArgumentException();

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
     * Compress a directory
     * @param dir directory target
     * @param jarFile Destination compress file
     */
    public static void jar(File dir, File jarFile) {
        if (jarFile == null || jarFile.exists() || dir == null || !dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException();

        Map<File, String> map = getDirFileEntryMap(dir);
        if (map != null)
            jar(map, jarFile);
    }

    /**
     * Compress files using File-EntryName map.
     * @param fileEntryMap a File-EntryName map. e.g.<File, String>(new File("/test/my.jpg"), "/documents/my.jpg")
     * @param jarFile Destination compressed file
     */
    public static void jar(Map<File, String> fileEntryMap, File jarFile) {
        if (jarFile == null || jarFile.exists() || fileEntryMap == null)
            throw new IllegalArgumentException();

        JarArchiveOutputStream jarOut = null;
        try {
            jarOut = new JarArchiveOutputStream(new FileOutputStream(jarFile));
            jarOut.setUseZip64(Zip64Mode.AsNeeded);
            compress(fileEntryMap, jarOut, ZipArchiveEntry.class);
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
        if (jarFile == null || !jarFile.exists())
            throw new IllegalArgumentException();

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
    public static void ar(List<File> files, File arFile) {
        if (arFile == null || arFile.exists() || files == null)
            throw new IllegalArgumentException();

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
     * Compress a directory
     * @param dir directory target
     * @param arFile Destination compress file
     */
    public static void ar(File dir, File arFile) {
        if (arFile == null || arFile.exists() || dir == null || !dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException();

        Map<File, String> map = getDirFileEntryMap(dir);
        if (map != null)
            ar(map, arFile);
    }

    /**
     * Compress files using File-EntryName map.
     * @param fileEntryMap a File-EntryName map. e.g.<File, String>(new File("/test/my.jpg"), "/documents/my.jpg")
     * @param arFile Destination compressed file
     */
    public static void ar(Map<File, String> fileEntryMap, File arFile) {
        if (arFile == null || arFile.exists() || fileEntryMap == null)
            throw new IllegalArgumentException();

        ArArchiveOutputStream arOut = null;
        try {
            arOut = new ArArchiveOutputStream(new FileOutputStream(arFile));
            compress(fileEntryMap, arOut, ArArchiveEntry.class);
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
        if (arFile == null || !arFile.exists())
            throw new IllegalArgumentException();

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
        if (dumpFile == null || !dumpFile.exists())
            throw new IllegalArgumentException();

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
    public static void cpio(List<File> files, File cpioFile) {
        if (cpioFile == null || cpioFile.exists() || files == null)
            throw new IllegalArgumentException();

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
     * Compress a directory
     * @param dir directory target
     * @param cpioFile Destination compress file
     */
    public static void cpio(File dir, File cpioFile) {
        if (cpioFile == null || cpioFile.exists() || dir == null || !dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException();

        Map<File, String> map = getDirFileEntryMap(dir);
        if (map != null)
            cpio(map, cpioFile);
    }

    /**
     * Compress files using File-EntryName map.
     * @param fileEntryMap a File-EntryName map. e.g.<File, String>(new File("/test/my.jpg"), "/documents/my.jpg")
     * @param cpioFile Destination compressed file
     */
    public static void cpio(Map<File, String> fileEntryMap, File cpioFile) {
        if (cpioFile == null || cpioFile.exists() || fileEntryMap == null)
            throw new IllegalArgumentException();

        CpioArchiveOutputStream cpioOut = null;
        try {
            cpioOut = new CpioArchiveOutputStream(new FileOutputStream(cpioFile));
            compress(fileEntryMap, cpioOut, CpioArchiveEntry.class);
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
        if (cpioFile == null || !cpioFile.exists())
            throw new IllegalArgumentException();

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
    private static void compress(List<File> files, ArchiveOutputStream archiveOutputStream, Class<? extends ArchiveEntry> entryClazz) throws IOException{
        Map<File, String> map = new HashMap<>();
        for (File file : files) {
            map.put(file, null);
        }
        compress(map, archiveOutputStream, entryClazz);
    }

    /**
     * Compress file list.
     * @param fileEntryMap File,Entry-name map
     * @param archiveOutputStream archiveOutputStream
     * @param entryClazz ArchiveEntry detail
     * @throws IOException
     */
    private static void compress(Map<File, String> fileEntryMap, ArchiveOutputStream archiveOutputStream, Class<? extends ArchiveEntry> entryClazz) throws IOException {
        InputStream is = null;
        try {
            Set<File> files = fileEntryMap.keySet();
            for (File file : files) {
                if (file == null || !file.exists())
                    continue;
                String entryName = (fileEntryMap.get(file) == null) ? file.getName() : fileEntryMap.get(file);
                ArchiveEntry entry = entryClazz.getDeclaredConstructor(File.class, String.class).newInstance(file, entryName);
                archiveOutputStream.putArchiveEntry(entry);
                if (file.isFile()) {
                    is = new BufferedInputStream(new FileInputStream(file));
                    IOUtils.copy(is, archiveOutputStream);
                }
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
            throw new IllegalArgumentException();
        else if (!dir.exists())
            if (!dir.mkdir())
                throw new RuntimeException("Cannot create folder: " + dir.getPath());

        ArchiveEntry entry;
        List<String> failList = new ArrayList<>(); //to record fail-entry msg to throw
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                File subDirectory = new File(dir, entry.getName());
                FileUtils.forceMkdir(subDirectory);
            } else {
                OutputStream os = null;
                try {
                    File file = new File(dir, entry.getName());
                    if (!file.getParentFile().exists())
                        FileUtils.forceMkdir(file.getParentFile());
                    os = new BufferedOutputStream(new FileOutputStream(file));
                    IOUtils.copy(archiveInputStream, os);
                } catch (IOException e) {
                    failList.add(entry.getName());
                } finally {
                    IOUtils.closeQuietly(os);
                }
            }
        }
        if (failList.size() > 0) {
            throw new IOException("File: " + StringUtils.join(failList, ",") + " decompress failed.");
        }
    }

    private static Map<File, String> getDirFileEntryMap(File dir) {
        Iterator<File> original = FileUtils.iterateFilesAndDirs(dir, FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter());
        //to relatively
        if (!original.hasNext())
            return null;
        String base = original.next().getPath();
        base = base.endsWith(File.separator) ? base : base + File.separator;
        Map<File, String> map = new HashMap<>();
        while (original.hasNext()) {
            File file = original.next();
            map.put(file, StringUtils.removeStart(file.getPath(), base));
        }
        return map;
    }
}
