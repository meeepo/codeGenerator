package fx;

import javafx.scene.control.TabPane;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-04
 */
public class WebTimeStamp {
    static WebUrlTab webUrlTab = new WebUrlTab("WebTimeStamp", "https://tool.lu/timestamp/");

    public static String funName() {
        return webUrlTab.getName();
    }


    public static void newTab(TabPane tabPane) {
        webUrlTab.newTab(tabPane);
    }

}
