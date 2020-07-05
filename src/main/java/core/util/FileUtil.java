package core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        try {
            String folderPathStr = pathStr.substring(0, pathStr.lastIndexOf("/"));
            Path path = Paths.get(pathStr);
            Path folderPath = Paths.get(folderPathStr);

            if (!Files.exists(folderPath)) {
                mkdir(folderPathStr);
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String pathStr = "/Library/aas/ws/w2-self/codeGenerator/src/main/resources/demo/FreeMakerProj/output/aas";
        String data = "aa3";

        writeFile(data,pathStr);



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

    public static List<ParsedFile> parseFiles(String basePath, List<String> relPaths) {
        if (relPaths == null || relPaths.size() == 0) {
            return Collections.emptyList();
        }
        return relPaths.stream()
                .map(e -> concatPath(basePath, e))
                .map(FileUtil::parseFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<ParsedFile> parseFiles(List<String> fullPaths) {
        if (fullPaths == null || fullPaths.size() == 0) {
            return Collections.emptyList();
        }
        return fullPaths.stream()
                .map(FileUtil::parseFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static ParsedFile parseFile(String basePath, String relPath) {
        String fullPath = concatPath(basePath, relPath);
        File file = new File(fullPath);
        if (!file.exists()) {
            return null;
        }
        String fullName = file.getName();
        String data = readFile(fullPath);
        if (!fullName.contains(".")) {
            return new ParsedFile(data, fullName, "");
        } else {
            String[] spli = fullName.split("\\.");
            return new ParsedFile(data, spli[0], spli[spli.length - 1]);
        }
    }

    public static ParsedFile parseFile(String fullPath) {
        File file = new File(fullPath);
        if (!file.exists()) {
            return null;
        }
        String fullName = file.getName();
        String data = readFile(fullPath);
        if (!fullName.contains(".")) {
            return new ParsedFile(data, fullName, "");
        } else {
            String[] spli = fullName.split("\\.");
            return new ParsedFile(data, spli[0], spli[spli.length - 1]);
        }
    }


    public static String concatPath(String... paths) {
        String sep = "/";
        if (paths == null || paths.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(paths[i]);

        }
        return sb.toString();
    }

    public static class ParsedFile {
        String data;
        String fileName;
        String fileTyle;

        public ParsedFile(String data, String fileName, String fileTyle) {
            this.data = data;
            this.fileName = fileName;
            this.fileTyle = fileTyle;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileTyle() {
            return fileTyle;
        }

        public void setFileTyle(String fileTyle) {
            this.fileTyle = fileTyle;
        }
    }

}
