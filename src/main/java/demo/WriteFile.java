package demo;

import core.util.FileUtil;

/**
 * @author zhangbowen <zhangbowen@kuaishou.com>
 * Created on 2020-07-11
 */
public class WriteFile {

    public static void main(String[] args) {
        String id = "83374488337448";

//        make("83374488337448",10000);
//        make("83374488337448",100000);
//        make("83374488337448",1000000);
        make("8337448",100);
//        make("8337448",100000);
//        make("8337448",1000000);
    }
    public static void make(String id,int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if(i==0){
                sb.append(id);
            }else{
                sb.append(",").append(id);
            }
        }

        FileUtil.writeFile(sb.toString(), "/Library/aas/tmp/"+id+len);
    }
}
