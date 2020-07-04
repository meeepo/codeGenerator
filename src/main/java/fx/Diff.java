package fx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2019-07-02
 */
public class Diff {
    public static String funName() {
        return "Diff";
    }

    public static void newTab(TabPane tabPane) {
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        tab.setContent(getPane());
    }

    public static GridPane getPane() {


        GridPane pane = new GridPane();
        TextField text1 = new TextField();
        TextArea text2 = new TextArea();
        TextArea text3 = new TextArea();
        TextArea text4 = new TextArea();

        text1.setText(",");
        Button btn = new Button();
        btn.setText("交集");
        Button btn2 = new Button();
        btn2.setText("差集");
        Button btn3 = new Button();
        btn3.setText("并集");
        Button btn4 = new Button();
        btn4.setText("join");
        Button btn5 = new Button();
        btn5.setText("split");
        Button btn6 = new Button();
        btn6.setText("查重");

        pane.add(new Label("分隔符"), 0, 1);
        pane.add(new Label("输入1"), 0, 2);
        pane.add(new Label("输入2"), 0, 3);
        pane.add(new Label("输出"), 0, 4);

        pane.add(text1, 1, 1);
        pane.add(text2, 1, 2);
        pane.add(text3, 1, 3);
        pane.add(text4, 1, 4);
        pane.add(btn, 1, 5);
        pane.add(btn2, 1, 6);
        pane.add(btn3, 1, 7);
        pane.add(btn4, 0, 5);
        pane.add(btn5, 0, 6);
        pane.add(btn6, 0, 7);


        btn.setOnAction(event -> {
            String sep = text1.getText();
            Set<String> set1 = toSet(text2.getText(), sep);
            Set<String> set2 = toSet(text3.getText(), sep);
            Sets.SetView<String> intersection = Sets.intersection(set1, set2);
            Set<String> res = new HashSet<>(intersection);
            res.remove("");
            if (isCount) {
                text4.setText(res.size() + "");
            } else {
                text4.setText(StringUtils.join(res.toArray(), sep));
            }
        });
        btn2.setOnAction(event -> {
            String sep = text1.getText();
            Set<String> set1 = toSet(text2.getText(), sep);
            Set<String> set2 = toSet(text3.getText(), sep);
            Sets.SetView<String> diff1 = Sets.difference(set1, set2);
            Sets.SetView<String> diff2 = Sets.difference(set2, set1);
            Sets.SetView<String> union = Sets.union(diff1, diff2);
            Set<String> res = new HashSet<>(union);
            res.remove("");
            if (isCount) {
                text4.setText(res.size() + "");
            } else {
                text4.setText(StringUtils.join(res.toArray(), sep));
            }
        });
        btn3.setOnAction(event -> {
            String sep = text1.getText();
            Set<String> set1 = toSet(text2.getText(), sep);
            Set<String> set2 = toSet(text3.getText(), sep);
            Sets.SetView<String> union = Sets.union(set1, set2);
            Set<String> res = new HashSet<>(union);
            res.remove("");
            if (isCount) {
                text4.setText(res.size() + "");
            } else {
                text4.setText(StringUtils.join(res.toArray(), sep));
            }
        });
        btn4.setOnAction(event -> { //join
            String sep = text1.getText();
            List<String> set1 = toList(text2.getText(), "\n");
            List<String> set2 = toList(text3.getText(), "\n");
            String sep2 = set1.isEmpty() || set2.isEmpty() ? "" : sep;
            String res = StringUtils.join(set1, sep) + sep2 + StringUtils.join(set2, sep);
            if (isCount) {
                int count = set1.size() + set2.size();
                text4.setText(count + "");
            } else {
                text4.setText(res);
            }
        });
        btn5.setOnAction(event -> { //split
            String sep = text1.getText();
            List<String> set1 = toList(text2.getText(), sep);
            List<String> set2 = toList(text3.getText(), sep);
            String sep2 = set1.isEmpty() || set2.isEmpty() ? "" : "\n";
            String res = StringUtils.join(set1, "\n") + sep2 + StringUtils.join(set2, "\n");
            text4.setText(res);
            if (isCount) {
                int count = set1.size() + set2.size();
                text4.setText(count + "");
            } else {
                text4.setText(res);
            }
        });
        btn6.setOnAction(event -> { //查重
            String sep = text1.getText();
            List<String> list1 = toList(text2.getText(), sep);

            Map<String, Integer> statMap = new HashMap<>();
            for (String elm : list1) {
                statMap.merge(elm, 1, (a, b) -> a + b);
            }

            Set<String> res = statMap.entrySet().stream().filter(e -> e.getValue() > 1)
                    .map(e -> e.getKey()).collect(Collectors.toSet());
            res.remove("");
            Set<String> res2 = statMap.entrySet().stream().filter(e -> e.getValue() > 1)
                    .map(e -> e.getKey() + "[" + e.getValue() + "]").collect(Collectors.toSet());
            res2.remove("");
            if (isCount) {
                text4.setText(res.size() + "");
            } else {
                text4.setText(StringUtils.join(res.toArray(), "\n"));
                text3.setText(StringUtils.join(res2.toArray(), "\n"));
            }
        });
        ChoiceBox<String> sel_count = new ChoiceBox<>(
                FXCollections.observableArrayList("输出", "计数"));
        sel_count.setOnAction(eve -> {
            if (sel_count.getValue().equals("计数")) {
                isCount = true;
            } else {
                isCount = false;
            }
        });
        pane.add(sel_count, 1, 10);
        return pane;
    }

    static boolean isCount = false;

    public static Set<String> toSet(String rawStr, String spliter) {
        return Stream.of(rawStr).flatMap(str -> Arrays.stream(str.split("\n"))) //
                .filter(e -> !e.isEmpty()) //
                .flatMap(str -> Arrays.stream(str.split(spliter))).collect(Collectors.toSet());
    }

    public static List<String> toList(String rawStr, String spliter) {
        return Stream.of(rawStr).flatMap(str -> Arrays.stream(str.split("\n"))) //
                .filter(e -> !e.isEmpty()) //
                .flatMap(str -> Arrays.stream(str.split(spliter))).collect(Collectors.toList());
    }


}
