package fx;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-04
 */
public class HtmlEditor {
    public static String funName(){
        return "HtmlEditor";
    }
    public static void newTab(TabPane tabPane){
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);

        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        tab.setContent(htmlEditor);

    }
}
