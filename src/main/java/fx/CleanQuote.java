package fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-30
 */
public class CleanQuote {

    public static String funName(){
        return "删除逗号";
    }
    public static void newTab(TabPane tabPane){
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        VBox vb = new VBox();
        tab.setContent(vb);
        TextArea text1 = new TextArea();
        TextArea text2 = new TextArea();
        vb.getChildren().addAll(text1, text2);

        text1.textProperty().addListener((observable, oldValue, newValue) -> {
            text2.setText(newValue.replaceAll(",",""));
        });
    }

}
