package core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-18
 */
public class PropUtil {
    private static Logger logger = LoggerFactory.getLogger(PropUtil.class);

    public static Properties loadPropByPath(String path) {
        Properties properties = new Properties();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FileUtil.toFullPath(path)));
            properties.load(bufferedReader);
        } catch (Exception e) {
            logger.error("[loadProp error]", e);
        }
        return properties;
    }

    public static Properties loadPropByString(String propertiesString) {
        Properties properties = new Properties();
        try {
            properties.load(new ByteArrayInputStream(propertiesString.getBytes()));
        } catch (IOException e) {
            logger.error("loadPropByString", e);
        }
        return properties;
    }

    public static void main(String[] args) {
        //
        //        String path = "/Library/aas/ws/w2-self/codeGenerator/src/main/resources/log4j.properties";
        //        Properties properties = loadProp(path);
        //        properties.entrySet().forEach(e -> {
        //            System.out.println(e.getKey() + "=" + e.getValue());
        //        });
    }
}
