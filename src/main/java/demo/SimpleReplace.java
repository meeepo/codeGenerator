package demo;

import core.CodeGenerator;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-15
 */
public class SimpleReplace {

    public static void main(String[] args) {
        test1();
    }
    public static void test1(){
        CodeGenerator.useConfig("/core/simpleReplaceConfig.json");
        CodeGenerator.generate();
    }
}
