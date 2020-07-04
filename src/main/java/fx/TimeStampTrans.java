package fx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.FileUtil;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-06-30
 */
public class TimeStampTrans {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName(){
        return "翻译时间戳";
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
            text2.setText(trans(newValue));
        });
    }
    public static String trans(String str){
        Pattern pattern = Pattern.compile("(\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d)");
        Matcher matcher = pattern.matcher(str);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String mark = "__mark__";
        while (matcher.find( )){
            try{
                String stamp= matcher.group(1);

                String fmtDate = simpleDateFormat.format(new Date(Long.parseLong(stamp)));
               String  markstamp=stamp.substring(0,5)+mark+stamp.substring(5);

                String rep=markstamp+"("+fmtDate+")";

                str = str.replaceAll(stamp, rep);
            }catch(Exception e){
            logger.error("[error]",e);
            }

        }
        str=str.replaceAll(mark,"");

        return str;

    }

    public static void main(String[] args) {
        String testStr="1593398330001 1593398330001 sdfsdf adf1593398330002s\n sfsdfds1593398330003 sdf\n 1593398330004";
        System.out.println(trans(testStr));
    }
}
