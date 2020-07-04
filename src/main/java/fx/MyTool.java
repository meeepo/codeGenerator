package fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.FileUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

        TreeView<String> tree = new TreeView<>(rootItem);
        tree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
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


        //        HBox vb = new HBox();
        //        Button btn1 = new Button("aaa");
        //        btn1.setOnAction(e -> {
        //            logger.info("Button");
        //        });
        //        vb.getChildren().add(btn1);
        //        vb.getChildren().add(new Text(("bottom info")));
        //        borderPane.setBottom(vb);

        stage.setScene(new Scene(borderPane, 800, 500));
        stage.setTitle("my tool");
        stage.show();
    }



}
