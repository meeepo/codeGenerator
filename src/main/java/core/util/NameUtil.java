package core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class NameUtil {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern word = Pattern.compile("(\\w+)");

    //这里我们只考虑下划线和驼峰，不是下划线就是驼峰，普通单词算驼峰
    public static boolean isUnderscore(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return false;
        }
        return name.contains("_");
    }


    public static String toPureWord(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        Matcher matcher = word.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }


    }

    //any to underscore
    public static String toUnderScorePure(String name) {
        name = toPureWord(name);
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        if (isUnderscore(name)) {
            return name;
        }
        return camelToUnderScore(name);
    }

    //any to camel
    public static String toStdCamelPure(String name) {
        return toStdCamel(toPureWord(name));
    }

    public static String toStdCamel(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        if (isUnderscore(name)) {
            return underScoreToCamel(name);
        }
        return underScoreToCamel(camelToUnderScore(name));
    }

    public static String underScoreToCamel(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        name = name.toLowerCase();
        Matcher matcher = linePattern.matcher(name);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static String camelToUnderScore(String name) {


        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        String first = name.replaceAll("[A-Z]", "_$0").toLowerCase();
        String sec = first.replaceAll("_([a-z])_([a-z])", "_$1$2");
        if (sec.startsWith("_")) {
            return sec.substring(1);
        } else {
            return sec;
        }
    }

    public static String underScoreToMiddleLine(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        name = name.toLowerCase();
        return name.replaceAll("_", "-");
    }

    public static String underScoreToUpper(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        return name.toUpperCase();
    }

    public static String toClassName(String camel) {
        return camel.substring(0, 1).toUpperCase() + camel.substring(1);
    }

    public static String camelToPackageName(String camel) {
        return camel.toLowerCase();
    }

    public static String toGetter(String camel) {
        return "get" + toClassName(camel);
    }

    public static String toSetter(String camel) {
        return "set" + toClassName(camel);
    }

    public static String toService(String camel) {
        return toClassName(camel) + "Service";
    }

    public static String toServiceImpl(String camel) {
        return toClassName(camel) + "ServiceImpl";
    }

    public static String toDao(String camel) {
        return toClassName(camel) + "Dao";
    }

    public static String toController(String camel) {
        return toClassName(camel) + "Controller";
    }

    public static String toHelper(String camel) {
        return toClassName(camel) + "Helper";
    }

    public static String toConsumer(String camel) {
        return toClassName(camel) + "Consumer";
    }

    public static String toTask(String camel) {
        return toClassName(camel) + "Task";
    }


}
