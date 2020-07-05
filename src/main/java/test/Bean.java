package test;

import java.util.List;
import java.util.Map;

import core.util.NameUtil;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-05
 */
public class Bean {
    //sss
    public  int myAge; //bbb
    public  int myNum=4;
    public static final Integer MAX_NUM=4;

    /*
    sdf
     */
    /**
     * sdf
     */
    public  String myName="aas";
    public   String myDesp;
    public  List<String> names;
    public  Map<String,Object> map;
    NameUtil nameUtil;

    public int getMyAge() {
        int sss=44;
        return myAge;
    }

    public void setMyAge(int myAge) {
        this.myAge = myAge;
    }

    public int getMyNum() {
        return myNum;
    }

    public void setMyNum(int myNum) {
        this.myNum = myNum;
    }

    public static Integer getMaxNum() {
        return MAX_NUM;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyDesp() {
        return myDesp;
    }

    public void setMyDesp(String myDesp) {
        this.myDesp = myDesp;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public NameUtil getNameUtil() {
        return nameUtil;
    }

    public void setNameUtil(NameUtil nameUtil) {
        this.nameUtil = nameUtil;
    }
}
