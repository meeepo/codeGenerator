package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-10-10
 */
public class GeneTest {

    public static void main(String[] args) {
        //        GeneClass<String> list = new GeneClass<String>(){};
        //        Type type = list.getClass().getGenericSuperclass();
        //        Type type2 = ((ParameterizedType)type).getActualTypeArguments()[0];
        //        System.out.println(type2);

        GeneClass<Map<String,Long>> ls = new GeneClass<Map<String,Long>>() ;
        //        ls.field = "sdf";
        //        Type type3 = ls.getClass().getGenericSuperclass();
        //        Type type4 = ((ParameterizedType)type3).getActualTypeArguments()[0];
        //        System.out.println(type4);

        //                Type type4 = ((ParameterizedType)ls.getClass().getGenericSuperclass())
        //                .getActualTypeArguments()[0];
        //        System.out.println(type4);
        tellGeneric(ls.getClass());
        //        tellGeneric(tellGeneric(ls.getClass()));
    }

    public static class GeneClass<T> {
        T field;
    }

    public static <T> Class<T> tellGeneric(Class<T> clazz) {
        Type type;
        Class<T> classType;
//        Object sdf= clazz.get;
        Type superClass = clazz.getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            classType = (Class<T>) parameterizedType.getRawType();
            System.out.println(classType);

            for (Type tt : parameterizedType.getActualTypeArguments()) {
                if (tt instanceof ParameterizedType) {
                    System.out.println(((ParameterizedType) tt).getRawType());
                } else {
                    System.out.println(tt);
                }
            }

        } else {
            classType = (Class<T>) type;
            System.out.println(classType);

        }

        return classType;
    }
}
