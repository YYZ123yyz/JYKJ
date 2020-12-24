package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.text.TextUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-23 19:14
 */
public class VerificationUtils {

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {

        if (TextUtils.isEmpty(value)) {
            return false;
        }
        String reg = "\\d+(\\.\\d+)?";
        return value.matches(reg);

    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
