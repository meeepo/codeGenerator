package fx.fmp;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 代表一个bean
 *
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-08-08
 */
public class BeanCfg {
    private String beanName = "noName"; //小写bean名称如myBean

    public static class BeanField {
        String fieldName = "noName";
        String className = "noName";
        String type = FieldType.SIMPLE.name(); //FieldType
        List<String> genericsTypes = Lists.newArrayList();
        String defaultVal = "";

        String sqlType = "varchar";
        String sqlLen = "255";
        String sqlDefault = "";
    }

    public static enum FieldType {
        SIMPLE,
        BEAN,
        LIST,
        MAP,
        SET
    }
}
