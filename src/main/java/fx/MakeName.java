package fx;

import java.io.IOException;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.NameUtil;
import fx.MvnGe.MyModuleSel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class MakeName {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName(){
        return "MakeName";
    }
    public static void newTab(TabPane tabPane){


        try {
            Parent root = FXMLLoader.load(MvnGe.class.getClassLoader().getResource("fxres/makeName.fxml"));
//            VBox moduleSel = (VBox) root.lookup("#moduleSel");
            Button doGe = (Button) root.lookup("#doGe");
            TextField rawName = (TextField) root.lookup("#rawName");

//            initModule(moduleSel);

            Tab tab = new Tab(funName());
            tabPane.getTabs().add(tab);
            tab.setContent(root);

            doGe.setOnAction(ac -> {
                dealGe(root);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    public static void initModule(VBox moduleSel){
//        CheckBox cb;
//        cb = new CheckBox("camel");
//        moduleSel.getChildren().add(cb);
//        cb = new CheckBox("underScore");
//        moduleSel.getChildren().add(cb);
//    }
    public static void dealGe(Parent root){
        TextField rawName = (TextField) root.lookup("#rawName");
        TextArea outputCmd = (TextArea) root.lookup("#outputCmd");
        VBox moduleSel = (VBox) root.lookup("#moduleSel");

        StringBuilder res=new StringBuilder();

        String camel = NameUtil.toStdCamelPure(rawName.getText());
        String underScore = NameUtil.camelToUnderScore(camel);
        res.append(camel).append("\n");
        res.append(underScore).append("\n");
        res.append(NameUtil.underScoreToMiddleLine(underScore)).append("\n");
        res.append(NameUtil.underScoreToUpper(underScore)).append("\n");

        res.append(NameUtil.toClassName(camel)).append("\n");
        res.append(NameUtil.toGetter(camel)).append("\n");
        res.append(NameUtil.toSetter(camel)).append("\n");
        res.append(NameUtil.toService(camel)).append("\n");
        res.append(NameUtil.toServiceImpl(camel)).append("\n");
        res.append(NameUtil.toDao(camel)).append("\n");
        res.append(NameUtil.toController(camel)).append("\n");
        res.append(NameUtil.toHelper(camel)).append("\n");
        res.append(NameUtil.toConsumer(camel)).append("\n");
        res.append(NameUtil.toTask(camel)).append("\n");

        outputCmd.setText(res.toString());
        System.out.println(res.toString());



    }
}
