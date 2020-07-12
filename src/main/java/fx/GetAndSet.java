package fx;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class GetAndSet {
    private static Logger logger = LoggerFactory.getLogger(GetAndSet.class);

    public static String funName() {
        return "GetAndSet";
    }

    public static void newTab(TabPane tabPane) {
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        VBox vb = new VBox();
        tab.setContent(vb);
        TextArea text1 = new TextArea();
        TextArea text2 = new TextArea();
        vb.getChildren().addAll(text1, text2);

        final ToggleGroup group = new ToggleGroup();

        RadioButton javaBean = new RadioButton("javaBean");
        javaBean.setToggleGroup(group);
        javaBean.setSelected(true);

        RadioButton kotlinClass = new RadioButton("kotlinClass");
        kotlinClass.setToggleGroup(group);
        HBox hb = new HBox();
        hb.getChildren().add(javaBean);
        hb.getChildren().add(kotlinClass);
        vb.getChildren().add(hb);
        CheckBox toJson = new CheckBox("toJson?");
        toJson.setSelected(true);
        hb.getChildren().add(toJson);

        text1.textProperty().addListener((observable, oldValue, newValue) -> {

            RadioButton sel = (RadioButton) group.getSelectedToggle();
            if (sel.getText().equals("javaBean")) {
                text2.setText(tranJavaBean(newValue, toJson));
            }
            if (sel.getText().equals("kotlinClass")) {
                text2.setText(tranKotlinClass(newValue, toJson));
            }
        });
    }

    public static String tranKotlinClass(String str, CheckBox toJson) {

        List<String> names = Arrays.stream(str.split("\n"))
                .map(line -> {
                    line = line.trim();
                    String regSimp = "var\\W+(\\w+)\\W*:";
                    Matcher m2 = Pattern.compile(regSimp).matcher(line);
                    if (m2.find()) {
                        return m2.group(1);
                    }
                    return "";
                })
                .filter(e -> !Strings.isNullOrEmpty(e))
                .distinct()
                .map(e -> {
                    if (toJson.isSelected()) {
                        return "\"" + e + "\"";
                    }
                    return e;
                })
                .collect(Collectors.toList());

        return Joiner.on(",").join(names);
    }

    public static String tranJavaBean(String str, CheckBox toJson) {

        List<String> names = Arrays.stream(str.split("\n"))
                .map(line -> {
                    line = line.trim();
                    if (Strings.isNullOrEmpty(line)) {
                        return "";
                    }
                    if (line.matches(".*final.*|.*static.*|.*return.*|.*import.*|.*package.*")) {
                        return "";
                    }

                    String regSimp = ".*\\W(\\w+)\\W*;.*";
                    String regEqul = ".*\\W(\\w+)\\W*=.*;.*";
                    Matcher m = Pattern.compile(regEqul).matcher(line);
                    if (m.find()) {
                        return m.group(1);
                    }
                    Matcher m2 = Pattern.compile(regSimp).matcher(line);
                    if (m2.find()) {
                        return m2.group(1);
                    }
                    return "";
                })
                .filter(e -> !Strings.isNullOrEmpty(e))
                .distinct()
                .map(e -> {
                    if (toJson.isSelected()) {
                        return "\"" + e + "\"";
                    }
                    return e;
                })
                .collect(Collectors.toList());

        return Joiner.on(",").join(names);


    }
}
