package fx.fmp;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.FileUtil;
import core.util.FileUtil.ParsedFile;
import core.util.JsonUtil;
import core.util.PropUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import fx.TimeStampTrans;
import fx.fmp.FmConfig;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-04
 */
public class FreeMakerProj {
    private static Logger logger = LoggerFactory.getLogger(TimeStampTrans.class);

    public static String funName() {
        return "FreeMakerProj";
    }

    public static void newTab(TabPane tabPane, Stage stage) {
        Tab tab = new Tab(funName());
        tabPane.getTabs().add(tab);
        VBox vb = new VBox();
        tab.setContent(vb);

        HBox hb1 = new HBox();
        vb.getChildren().add(hb1);


        Button browseButton = new Button("选择项目目录");
        Button doGen = new Button("生成");
        hb1.getChildren().add(browseButton);
        hb1.getChildren().add(doGen);
        TextField projPath = new TextField();
        projPath.setText("/Library/aas/ws/w2-self/codeGenerator/src/main/resources/demo/FreeMakerProj");
        vb.getChildren().add(projPath);
        TextArea cfg = new TextArea();
        vb.getChildren().add(cfg);
        TextArea res = new TextArea();
        vb.getChildren().add(res);


        browseButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File folder = directoryChooser.showDialog(stage);
            if (folder != null) {
                System.out.println(folder.getAbsolutePath());
                projPath.setText(folder.getAbsolutePath());
                cfg.setText(loadConfig(folder.getAbsolutePath()));
            }

        });
        doGen.setOnAction(xx -> {
            try {
                cfg.setText(loadConfig(projPath.getText()));
                String result = doGene(loadFmConfig(projPath.getText()));
                res.setText(result);
            } catch (Exception e) {
                logger.error("doGen.setOnAction error", e);
            }
        });


    }

    public static String loadConfig(String projPath) {
        return FileUtil.readFile(projPath + "/" + FmConfig.cfgName);
    }

    public static FmConfig loadFmConfig(String projPath) {
        FmConfig fmConfig = JsonUtil.fromJson(loadConfig(projPath), FmConfig.class);
        fmConfig.setProjPath(projPath);
        return fmConfig;
    }

    public static String doGene(FmConfig fmConfig) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);


        return freemake(fmConfig, cfg);
    }

    public static String freemake(FmConfig fmConfig, Configuration cfg) throws Exception {
        StringBuilder res = new StringBuilder();
        FileUtil.parseFiles(fmConfig.getProjPath(), fmConfig.getCommonConfigs())
                .forEach(e -> {
                    try {
                        if (FmConfig.jsonSub.equals(e.getFileTyle())) {
                            cfg.setSharedVariable(e.getFileName() + "_json", e.getData());
                        }
                        if (FmConfig.propSub.equals(e.getFileTyle())) {
                            Properties prop = PropUtil.loadPropByString(e.getData());
                            cfg.setSharedVariable(e.getFileName(), prop);
                        }
                        if (FmConfig.textSub.equals(e.getFileTyle())) {
                            cfg.setSharedVariable(e.getFileName(), e.getData());
                        }
                    } catch (TemplateModelException templateModelException) {
                        logger.error("TemplateModelException", templateModelException);
                    }
                });


        StringTemplateLoader loader = (StringTemplateLoader) cfg.getTemplateLoader();
        fmConfig.getFtls().forEach(ftlCfg -> {
            try {
                Map<String, Object> root = new HashMap<>();
                ParsedFile ftl = FileUtil.parseFile(fmConfig.getProjPath(), ftlCfg.getFtlPath());
                if (ftl == null) {
                    return;
                }
                loader.putTemplate(ftl.getFileName(), ftl.getData());
                FileUtil.parseFiles(fmConfig.getProjPath(), ftlCfg.getDatas())
                        .forEach(e -> {
                            if (FmConfig.jsonSub.equals(e.getFileTyle())) {
                                root.put(e.getFileName() + "_json", e.getData());
                            }
                            if (FmConfig.propSub.equals(e.getFileTyle())) {
                                Properties prop = PropUtil.loadPropByString(e.getData());
                                root.put(e.getFileName(), prop);
                            }
                            if (FmConfig.textSub.equals(e.getFileTyle())) {
                                root.put(e.getFileName(), e.getData());
                            }
                        });
                Template temp = cfg.getTemplate(ftl.getFileName());
                StringWriter writer = new StringWriter();
                temp.process(root, writer);
                String outPutFile =
                        FileUtil.concatPath(fmConfig.getProjPath(), FmConfig.outputFolder, ftl.getFileName());
                FileUtil.writeFile(writer.toString(), outPutFile);
                res.append("\n").append(writer.toString());
            } catch (Exception e) {
                logger.error("[error]", e);
                res.append("\n").append(e.getMessage());
            }


        });

        return res.toString();
    }
}
