package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-29 16:27
 */
public class ApplicationUtil {

    /**
     * 判断是否在后台
     * @param context 上下文
     * @return true or false
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance
                        != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
