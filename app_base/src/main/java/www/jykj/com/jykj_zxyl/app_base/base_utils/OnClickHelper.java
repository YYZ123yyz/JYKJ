package www.jykj.com.jykj_zxyl.app_base.base_utils;

/**
 * Description:点击事件帮助类
 *
 * @author: qiuxinhai
 * @date: 2019-10-17 10:32
 */
public class OnClickHelper {

    public static final int INTEGER_800 = 800;
    private static long lastClickTime;

    /**
     * 防止点击两次
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < INTEGER_800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
