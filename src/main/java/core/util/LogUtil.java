package core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-18
 */
public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static void main(String[] args) {
        logger.info("sdf{}", 123);
        logger.debug("sdf{}", 123);
        logger.warn("sdf{}", 123);
        logger.error("sdf{}", 123);
    }
}
