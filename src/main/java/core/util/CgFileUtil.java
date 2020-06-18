package core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * cgFile就是用一个文件包含多个文件的方法
 * 从<<<<< 开始 >>>>>> 结束
 *
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-18
 */
public class CgFileUtil {
    private static Logger logger = LoggerFactory.getLogger(CgFileUtil.class);

    public static Map<String, String> loadCgFile(String path) {
        Map<String, String> result = new HashMap<>();
        try {
            String tmpData = "";
            String tmpName = "";
            boolean isInContent=false;
            for (String line : Files.readAllLines(Paths.get(FileUtil.toFullPath(path)))) {
                if (line.startsWith("<<<<<")) {
                    tmpName = line.substring(5);
                    isInContent = true;
                } else if (line.startsWith(">>>>>")) {
                    result.put(tmpName, tmpData);
                    tmpData = "";
                    tmpName = "";
                    isInContent = false;
                } else {
                    if(isInContent){
                        tmpData += line + "\n";
                    }
                }
            }

        } catch (IOException e) {
            logger.error("error", e);
        }
        return result;

    }

    public static void main(String[] args) {

        String path = "/Library/aas/ws/w2-self/codeGenerator/src/main/resources/demo/simpleReplace.cg";
        Map<String, String> result =loadCgFile(path);
        System.out.println(JsonUtil.toJson(result));

    }
}
