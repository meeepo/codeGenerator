package fx.fmp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class FmConfig {
    public static final String cfgName = "fmp.cfg";
    public static final  String outputFolder = "output";
    public static final String jsonSub = "json";
    public static final String propSub = "properties";
    public static final String textSub = "text";
    public static final  String ftlSub = "ftl";
    String projPath="";
    List<String> commonConfigs=new ArrayList<>();
    List<FtlCfg> ftls=new ArrayList<>();


    public static class FtlCfg{
        String ftlPath = "";
        String outputPath = "";
        List<String> datas=new ArrayList<>();

        public String getFtlPath() {
            return ftlPath;
        }

        public void setFtlPath(String ftlPath) {
            this.ftlPath = ftlPath;
        }

        public List<String> getDatas() {
            return datas;
        }

        public void setDatas(List<String> datas) {
            this.datas = datas;
        }

        public String getOutputPath() {
            return outputPath;
        }

        public void setOutputPath(String outputPath) {
            this.outputPath = outputPath;
        }
    }

    public String getProjPath() {
        return projPath;
    }

    public void setProjPath(String projPath) {
        this.projPath = projPath;
    }

    public List<String> getCommonConfigs() {
        return commonConfigs;
    }

    public void setCommonConfigs(List<String> commonConfigs) {
        this.commonConfigs = commonConfigs;
    }

    public List<FtlCfg> getFtls() {
        return ftls;
    }

    public void setFtls(List<FtlCfg> ftls) {
        this.ftls = ftls;
    }
}
