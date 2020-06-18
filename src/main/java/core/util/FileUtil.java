package core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-18
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static final String separator = File.separator;

    public static String getProjectFolder() {
        return System.getProperty("user.dir");
    }

    public static String toFullPath(String pathStr) {
        if (Strings.isNullOrEmpty(pathStr)) {
            return "";
        }
        return pathStr.startsWith(separator) ? pathStr
                                             : getProjectFolder() + separator + pathStr;

    }


    //支持相对和绝对路径
    public static String readFile(String pathStr) {
        try {
            Path path = Paths.get(pathStr);
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            return "";
        }
    }

    public static void writeFile(String data, String pathStr) {
        Path path = Paths.get(pathStr);
        try {
            Files.write(path, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //支持文件和目录，支持嵌套删除
    public static void delete(String pathStr) {
        try {
            deleteAllFiles(new File(pathStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteAllFiles(File root) {
        if (!root.isDirectory()) {//判断是否为文件夹
            root.delete();
        }
        File[] files = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {//判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) {//判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    public static void mkdir(String pathStr) {
        Path path = Paths.get(pathStr);
        if (path == null) {
            return;
        }
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
