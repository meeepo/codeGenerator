package fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.FileUtil;
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

    public static void main(String[] args) {
        launch(MyTool.class, args);
    }


    @Override
    public void start(Stage stage) {
        logger.info("start");
        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();


        borderPane.setCenter(tabPane);


        TreeItem<String> rootItem = new TreeItem<>("tools");
        rootItem.setExpanded(true);
        rootItem.getChildren().add(new TreeItem<>(CleanQuote.funName()));
        rootItem.getChildren().add(new TreeItem<>(TimeStampTrans.funName()));
        rootItem.getChildren().add(new TreeItem<>(MvnGe.funName()));
        rootItem.getChildren().add(new TreeItem<>(HtmlEditor.funName()));
        rootItem.getChildren().add(new TreeItem<>(WebTimeStamp.funName()));
        rootItem.getChildren().add(new TreeItem<>(RegFilter.funName()));
        rootItem.getChildren().add(new TreeItem<>(Diff.funName()));
        rootItem.getChildren().add(new TreeItem<>(SimpleFm.funName()));
        rootItem.getChildren().add(new TreeItem<>(FreeMakerProj.funName()));
        rootItem.getChildren().add(new TreeItem<>(MakeName.funName()));
        rootItem.getChildren().add(new TreeItem<>(GetAndSet.funName()));

        TreeView<String> tree = new TreeView<>(rootItem);
        tree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(tree.getSelectionModel().getSelectedItem()==null){
                event.consume();
                return;
            }
            String name = (String) ((TreeItem) tree.getSelectionModel().getSelectedItem()).getValue();
            if (name.equals(CleanQuote.funName())) {
                CleanQuote.newTab(tabPane);
            }
            if (name.equals(TimeStampTrans.funName())) {
                TimeStampTrans.newTab(tabPane);
            }
            if (name.equals(MvnGe.funName())) {
                MvnGe.newTab(tabPane);
            }
            if (name.equals(HtmlEditor.funName())) {
                HtmlEditor.newTab(tabPane);
            }
            if (name.equals(WebTimeStamp.funName())) {
                WebTimeStamp.newTab(tabPane);
            }
            if (name.equals(RegFilter.funName())) {
                RegFilter.newTab(tabPane);
            }
            if (name.equals(Diff.funName())) {
                Diff.newTab(tabPane);
            }
            if (name.equals(SimpleFm.funName())) {
                SimpleFm.newTab(tabPane);
            }
            if (name.equals(FreeMakerProj.funName())) {
                FreeMakerProj.newTab(tabPane,stage);
            }
            if (name.equals(MakeName.funName())) {
                MakeName.newTab(tabPane);
            }
            if (name.equals(GetAndSet.funName())) {
                GetAndSet.newTab(tabPane);
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
