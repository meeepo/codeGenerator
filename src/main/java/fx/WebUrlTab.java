package fx;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-04
 */
public class WebUrlTab {
    String name;
    String url;

    public WebUrlTab(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void newTab(TabPane tabPane) {
        Tab tab = new Tab(name);
        tabPane.getTabs().add(tab);
        BorderPane bp = new BorderPane();
        tab.setContent(bp);
        HBox hb = new HBox();
        TextField textField = new TextField(url);
        Button btn = new Button("加载");
        hb.getChildren().add(btn);
        Button btn2 = new Button("back");
        hb.getChildren().add(btn2);
        hb.getChildren().add(textField);


        bp.setTop(hb);

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        btn.setOnAction(e -> {
            webEngine.load(textField.getText());
        });
        btn2.setOnAction(e -> {
            webEngine.getHistory().go(-1);
        });
        webEngine.load(textField.getText());
        bp.setCenter(browser);


    }
}
