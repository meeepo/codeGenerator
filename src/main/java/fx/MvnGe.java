package fx;

import java.io.IOException;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fx.MvnGe.MyModuleSel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-30
 */
public class MvnGe {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName() {
        return "mvn命令生成";
    }

    public static void newTab(TabPane tabPane) {


        try {
            Parent root = FXMLLoader.load(MvnGe.class.getClassLoader().getResource("fxres/mvnGe.fxml"));
            VBox moduleSel = (VBox) root.lookup("#moduleSel");
            Button doGe = (Button) root.lookup("#doGe");

            initModule(moduleSel);

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
    public static void dealGe(Parent root){

        TextArea outputCmd = (TextArea) root.lookup("#outputCmd");
        CheckBox isUpdate = (CheckBox) root.lookup("#isUpdate");
        VBox moduleSel = (VBox) root.lookup("#moduleSel");
        logger.info("isUpdate={}", isUpdate.isSelected());

        StringBuilder cmd=new StringBuilder();
        String mvn = " mvn clean install -DskipTests=true ";
        String dep = " dependency:copy-dependencies ";
        String udp = "  -U  ";

        moduleSel.getChildren().stream()
                .map(e->(MyModuleSel) e)
                .filter(CheckBox::isSelected)
                .sorted(Comparator.comparingInt(MyModuleSel::getPriority))
                .forEach(e->{
                    cmd.append("cd ").append(e.getPath()).append(" && ");
                    cmd.append(mvn);
                    if(e.getText().contains("runner")){
                        cmd.append(dep);
                    }
                    if(isUpdate.isSelected()){
                        cmd.append(udp);
                    }
                    cmd.append(" && ");

                });
        String res=cmd.substring(0,cmd.length()-" && ".length());
        logger.info(res);
        outputCmd.setText(res);
    }

    public static class MyModuleSel extends CheckBox {
        String moduleName;
        String path;
        int priority;

        public MyModuleSel(String moduleName, String path, int priority) {
            super(moduleName);
            this.moduleName = moduleName;
            this.path = path;
            this.priority = priority;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getPath() {
            return path;
        }

        public int getPriority() {
            return priority;
        }
    }

    public static void initModule(VBox moduleSel) {
        String base;
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis-api",
                "/Library/aas/ws/w4-freq/kuaishou-audit-themis/kuaishou-audit-themis-api", 33));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis-component",
                "/Library/aas/ws/w4-freq/kuaishou-audit-themis/kuaishou-audit-themis-component", 32));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis-runner",
                "/Library/aas/ws/w4-freq/kuaishou-audit-themis/kuaishou-audit-themis-runner", 34));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis-sdk",
                "/Library/aas/ws/w4-freq/kuaishou-audit-themis/kuaishou-audit-themis-sdk", 31));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis",
                "/Library/aas/ws/w4-freq/kuaishou-audit-themis", 30));

         base = "/Library/aas/ws/w4-freq/kuaishou-admin";
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-admin-api",
                base+"/kuaishou-admin-api", 13));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-admin-component",
                base+"/kuaishou-admin-component", 12));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-admin-runner",
                base+"/kuaishou-admin-runner", 14));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-admin-themis-sdk",
                base+"/kuaishou-admin-sdk", 11));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-admin",
                base, 10));
        base = "/Library/aas/ws/w4-freq/kuaishou-audit";

        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-api",
                base+"/kuaishou-audit-api", 23));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-component",
                base+"/kuaishou-audit-component", 22));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-runner",
                base+"/kuaishou-audit-runner", 24));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit-themis-sdk",
                base+"/kuaishou-audit-sdk", 21));
        moduleSel.getChildren().add(new MyModuleSel("kuaishou-audit",
                base, 20));



    }

}
