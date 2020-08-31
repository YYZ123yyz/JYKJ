package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-14 15:06
 */
public class NetworkUtil {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_CLASS_2_G = 2;
    public static final int NETWORK_CLASS_3_G = 3;
    public static final int NETWORK_CLASS_4_G = 4;
    public static final String NET_STATUS_MOBILE = "mobile";
    public static final String NET_STATUS_WIFI = "wifi";
    public static final String NET_STATUS_NOT_CONNECT = "not_connect";

    public NetworkUtil() {
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == 1) {
                return 1;
            }

            if (activeNetwork.getType() == 0) {
                return 2;
            }
        }

        return 0;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == 1) {
            status = "wifi";
        } else if (conn == 2) {
            status = "mobile";
        } else if (conn == 0) {
            status = "not_connect";
        }

        return status;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }







    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getType() == 1;
    }

    public static boolean isMobileConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getType() == 0;
    }
}
