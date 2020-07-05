package test;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.util.JsonUtil;
import freemarker.template.*;
/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class FmTest1 {
    public static void main(String[] args) throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File("/Library/aas/ws/w2-self/codeGenerator/src/main/resources"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        cfg.setSharedVariable("company", "Foo Inc.");

        Map<String, Object> root = new HashMap<>();
        root.put("user", "aas");
        root.put("nodd", new Nodd("bbs"));
        root.put("ccs", JsonUtil.parseJsonTree(JsonUtil.toJson(new Nodd("ccs"))) );
        Map<String, Object> latest = new HashMap<>();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");
        List<String> ls = new ArrayList<>();
        ls.add("aaa");
        ls.add("bbb");
        ls.add("ccc");
        root.put("lis", ls);

        Template temp = cfg.getTemplate("ftl/test.ftl");
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
    }
    public static class  Nodd{
        String name="bbs";
        int age=44;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Nodd(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
