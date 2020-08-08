package demo;

import java.util.Map;

import fx.CleanQuote;
import fx.MyTool;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-15
 */
public class OutterProjTest extends MyTool {
    int _sssBoy = 1;

    public int get_sssBoy() {
        return _sssBoy;
    }

    public void set_sssBoy(int _sssBoy) {
        this._sssBoy = _sssBoy;
    }

    @Override
    public void outerInitFunMap(Stage stage, TabPane tabPane, Map<String, Runnable> funMap) {
        funMap.put(CleanQuote.funName() + "***", () -> {
            CleanQuote.newTab(tabPane);
        });
    }

    public static void main(String[] args) {
        launch(OutterProjTest.class, args);
    }

}
