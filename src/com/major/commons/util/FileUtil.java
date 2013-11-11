package com.major.commons.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * List files and directories.
     * @param directory target
     * @return file list
     */
    public static List<File> listFilesAndDirs(File directory) {
        if (directory == null || !directory.isDirectory() || !directory.exists())
            return null;
        return CollectionsUtil.arrayToList(directory.listFiles());
    }

    /**
     * List files and directories recursively.
     * @param directory target
     * @return file list
     */
    public static List<File> listFilesAndDirsRecursive(File directory) {
        if (directory == null || !directory.isDirectory() || !directory.exists())
            return null;
        List<File> list = new ArrayList<>(FileUtils.listFilesAndDirs
                (directory, FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));
        list.remove(0);
        return list;
    }
}
