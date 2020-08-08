package fx;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.FileUtil;
import fx.fmp.FreeMakerProj;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-30
 */
public class MyTool extends Application {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    Map<String, Runnable> funMap = new HashMap<>();

    public static void main(String[] args) {
        launch(MyTool.class, args);
    }


    public  void outerInitFunMap(Stage stage, TabPane tabPane, Map<String, Runnable>  funMap){
        System.out.println("outerInitFunMap");
    }

    public void initFunMap(Stage stage, TabPane tabPane) {
        outerInitFunMap(stage,tabPane,funMap);

        funMap.put(CleanQuote.funName(), () -> {
            CleanQuote.newTab(tabPane);
        });
        funMap.put(TimeStampTrans.funName(), () -> {
            TimeStampTrans.newTab(tabPane);
        });
        funMap.put(MvnGe.funName(), () -> {
            MvnGe.newTab(tabPane);
        });
        funMap.put(HtmlEditor.funName(), () -> {
            HtmlEditor.newTab(tabPane);
        });
        funMap.put(WebTimeStamp.funName(), () -> {
            WebTimeStamp.newTab(tabPane);
        });
        funMap.put(RegFilter.funName(), () -> {
            RegFilter.newTab(tabPane);
        });
        funMap.put(Diff.funName(), () -> {
            Diff.newTab(tabPane);
        });
        funMap.put(SimpleFm.funName(), () -> {
            SimpleFm.newTab(tabPane);
        });
        funMap.put(FreeMakerProj.funName(), () -> {
            FreeMakerProj.newTab(tabPane, stage);
        });
        funMap.put(MakeName.funName(), () -> {
            MakeName.newTab(tabPane);
        });
        funMap.put(GetAndSet.funName(), () -> {
            GetAndSet.newTab(tabPane);
        });
        funMap.put(HttpTester.funName(), () -> {
            HttpTester.newTab(tabPane);
        });


    }

    public Map<String, Runnable> getFunMap() {
        return funMap;
    }

    @Override
    public void start(Stage stage) {
        logger.info("start");
        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();


        borderPane.setCenter(tabPane);
        initFunMap(stage, tabPane);


        TreeItem<String> rootItem = new TreeItem<>("tools");
        rootItem.setExpanded(true);

        funMap.keySet().forEach(e -> {
            rootItem.getChildren().add(new TreeItem<>(e));
        });


        TreeView<String> tree = new TreeView<>(rootItem);
        tree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (tree.getSelectionModel().getSelectedItem() == null) {
                event.consume();
                return;
            }
            String name = (String) ((TreeItem) tree.getSelectionModel().getSelectedItem()).getValue();

            if (funMap.containsKey(name)) {
                funMap.get(name).run();
            }


        });


        borderPane.setLeft(tree);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItem1 = new MenuItem("Shuffle");
        menuItem1.setOnAction(e -> {
            logger.info("menuItem1");

        });
        menuFile.getItems().add(menuItem1);
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        borderPane.setTop(menuBar);


        stage.setScene(new Scene(borderPane, 800, 500));
        stage.setTitle("my tool");
        stage.show();
    }


}
