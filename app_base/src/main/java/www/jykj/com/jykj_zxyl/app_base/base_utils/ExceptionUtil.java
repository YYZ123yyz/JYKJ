package www.jykj.com.jykj_zxyl.app_base.base_utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常统一处理
 */
public class ExceptionUtil {

    /**
     * 异常信息打印
     */
    public static void handleException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String string = stringWriter.toString();
        LogUtils.e("异常信息="+string);
    }

}
