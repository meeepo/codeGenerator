package fx;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * json,ftl->string
 *
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class SimpleFm {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName() {
        return "SimpleFm";
    }

    public static void newTab(TabPane tabPane) {
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        VBox vb = new VBox();
        tab.setContent(vb);

        TextArea ftl = new TextArea("<#assign json=jsonStr?eval />\n"
                + "json.name=${json.name}\n"
                + "<#list json.ids as id>\n"
                + "${id}\n"
                + "</#list>");
        TextArea json = new TextArea("{\"name\":\"aas\",\"ids\":[1,2,3]}");
        TextArea res = new TextArea();
        Button btn = new Button("free maker");
        vb.getChildren().addAll(ftl, json, btn, res);

        btn.setOnAction(xx -> {

            try {
                res.setText(freemake(ftl.getText(), json.getText()));
            } catch (Exception e) {
                logger.error("[freemake error]", e);
            }

        });
    }

    public static String freemake(String ftl, String json) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myFtl", ftl);
        cfg.setTemplateLoader(stringLoader);

        Map<String, Object> root = new HashMap<>();
        root.put("jsonStr", json);
        Template temp = cfg.getTemplate("myFtl");
        StringWriter writer = new StringWriter();
        temp.process(root, writer);
        return writer.toString();
    }
}
