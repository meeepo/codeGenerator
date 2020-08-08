package fx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.util.HttpUtil;
import core.util.JsonUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class HttpTester {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName() {
        return "HttpTester";
    }

    public static void newTab(TabPane tabPane) {


        try {
            Parent root = FXMLLoader.load(MvnGe.class.getClassLoader().getResource("fxres/httpTester.fxml"));
            Button send = (Button) root.lookup("#send");
            TextField url = (TextField) root.lookup("#url");
            TextArea cfg = (TextArea) root.lookup("#cfg");
            TextArea data = (TextArea) root.lookup("#data");
            TextArea result = (TextArea) root.lookup("#result");


            Tab tab = new Tab(funName());
            tabPane.getTabs().add(tab);
            tab.setContent(root);

            send.setOnAction(ac -> {
                try {
                    dealGe(root);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void dealGe(Parent root) throws JsonProcessingException {

        TextField url = (TextField) root.lookup("#url");
        TextArea cfg = (TextArea) root.lookup("#cfg");
        TextArea data = (TextArea) root.lookup("#data");
        TextArea result = (TextArea) root.lookup("#result");

        JsonNode json = JsonUtil.parseJsonTree(cfg.getText());
        String method = json.get("method") == null ? "" : json.get("method").asText();
        String cookie = json.get("cookie") == null ? "" : json.get("method").asText();
        String contentType = json.get("Content-Type") == null ? "" : json.get("Content-Type").asText();
        String headers = json.get("headers") == null ? "" : json.get("headers").toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> header= mapper.readValue(headers,
                new TypeReference<HashMap<String,String>>(){});

        System.out.println(header);


//        Map<String, String> header = new HashMap<>();
//        header.put("Content-Type", contentType);
//        header.put("Cookie", cookie);
        String res = "";
        if (method.equals("post")) {
            res = HttpUtil.doPost(url.getText(), data.getText(), header);
        }

        if (method.equals("get")) {
            String fullUrl = url.getText() + "?" + data.getText();
            res = HttpUtil.doGet(fullUrl, header);
        }


        result.setText(res);


    }
}
