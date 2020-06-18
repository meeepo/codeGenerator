package core.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-18
 */
public class JsonUtil {
    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_ARRAY_JSON = "[]";
    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String toJson(Object obj) {
        try {
            if (obj == null) {
                return null;
            }
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            if (json == null) {
                return null;
            }
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
