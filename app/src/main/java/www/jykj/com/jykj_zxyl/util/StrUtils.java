package www.jykj.com.jykj_zxyl.util;

public class StrUtils {
    public static String defaulObjToStr(Object paramobj){
        if(null==paramobj){
            return "";
        }
        return String.valueOf(paramobj);
    }
}
