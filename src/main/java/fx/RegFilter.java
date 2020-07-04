package fx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2019-07-02
 */
public class RegFilter {
    public static String funName() {
        return "RegFilter";
    }

    public static void newTab(TabPane tabPane) {
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        tab.setContent(getPane());
    }

    public static GridPane getPane() {

        GridPane pane = new GridPane();
        TextField text1 = new TextField("");
        TextField text2 = new TextField("");
        TextArea text3 = new TextArea();
        TextArea text4 = new TextArea();
        text4.prefWidth(1000);
        Button btn = new Button();
        btn.setText("执行正则替换");

        pane.add(new Label("正则"), 0, 1);
        pane.add(new Label("格式化文本"), 0, 2);
        pane.add(new Label("输入文本"), 0, 3);
        pane.add(new Label("输出文本"), 0, 4);

        pane.add(text1, 1, 1);
        pane.add(text2, 1, 2);
        pane.add(text3, 1, 3);
        pane.add(text4, 1, 4);
        pane.add(btn, 1, 5);

        ChoiceBox<String> sel_cfg = new ChoiceBox<>(
                FXCollections.observableArrayList("kcs make path", "def", "getLongTime", "kcspath2"));
        sel_cfg.setOnAction(eve -> {
            if (sel_cfg.getValue().equals("kcs make path")) {
                text1.setText("(dmo.*?)\\s*?(admin.*)");
                text2.setText("ssh://${2}/data/logs/srsms-rpc-aliyun/${1}");
            }
            if (sel_cfg.getValue().equals("getLongTime")) {
                text1.setText(".*?(\\d{13}).*?(\\d{13})");
                text2.setText("${1}=$toDateTime{1} ${2}=$toDateTime{2}");
            }
            if (sel_cfg.getValue().equals("kcspath2")) {
                text1.setText("(dmo-.*)\\t.*(admin-.*)");
                text2.setText("${2},kcs2,clustername,${1}");
            }
        });

        pane.add(sel_cfg, 1, 6);
        pane.add(new Label("配置好的参数"), 0, 6);

        ChoiceBox<String> sel_distinct = new ChoiceBox<>(
                FXCollections.observableArrayList("不去重", "去重"));
        sel_distinct.setOnAction(eve -> {
            if (sel_distinct.getValue().equals("不去重")) {
                Cfg.isDistinct = false;
            } else {
                Cfg.isDistinct = true;
            }
        });
        pane.add(sel_distinct, 1, 7);
        pane.add(new Label("是否去重"), 0, 7);

        ChoiceBox<String> sel_order = new ChoiceBox<>(
                FXCollections.observableArrayList("不排序", "从小到大", "从大到小"));
        sel_order.setOnAction(eve -> {
            if (sel_order.getValue().equals("不排序")) {
                Cfg.order = "none";
            }
            if (sel_order.getValue().equals("从小到大")) {
                Cfg.order = "asc";
            }
            if (sel_order.getValue().equals("从大到小")) {
                Cfg.order = "desc";
            }
        });
        pane.add(sel_order, 1, 8);
        pane.add(new Label("是否排序"), 0, 8);

        Scene scene = new Scene(pane, 700, 500);


        btn.setOnAction(event -> {
            String reg = text1.getText();
            String fmt = Strings.isNullOrEmpty(text2.getText()) ? "${0}" : text2.getText();
            String data = text3.getText();
            StringBuilder sb = new StringBuilder();

            List<String> res = new ArrayList<>();
            Pattern pat = Pattern.compile(reg);
            String[] arr = data.split("\n");
            for (int i = 0; i < arr.length; i++) {
                String line = arr[i];
                Matcher m = pat.matcher(line);
                if (m.find()) {
                    String tmp = fmt;
                    for (int j = 0; j <= m.groupCount(); j++) {
                        tmp = tmp.replaceAll("\\$\\{" + j + "\\}", m.group(j));
                    }
                    for (int j = 0; j <= m.groupCount(); j++) {
                        tmp = tmp.replaceAll("\\$toDateTime\\{" + j + "\\}", longToTim(m.group(j)));
                    }
                    tmp = tmp.replaceAll("\\$\\{" + 0 + "\\}", m.group());
                    for (int j = 0; j < 10; j++) {
                        tmp = tmp.replaceAll("\\$\\{" + j + "\\}", "");
                    }
                    res.add(tmp);
                }
            }

            if (Cfg.isDistinct) {
                res = new ArrayList<>(new HashSet<>(res));
            }

            if (!Cfg.order.equals("none")) {
                res = res.stream().sorted().collect(Collectors.toList());
                if (Cfg.order.equals("desc")) {
                    res = Lists.reverse(res);
                }
            }
            for (int i = 0; i < res.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(res.get(i));

            }
            text4.setText(sb.toString());
        });

        String info = "格式化文本通过${n}表示第几组，全部是${0}\n格式化文本为空则仅返回匹配部分";
        text4.setText(info);

        return pane;
    }

    static String fmt = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String longToTim(String lon) {
        try {
            long tim = Long.parseLong(lon);
            return new SimpleDateFormat(fmt).format(new Date(tim));
        } catch (Exception e) {
        }
        return "";

    }


    static class Cfg {

        static boolean isDistinct = false;
        static String order = "none";
    }
}
