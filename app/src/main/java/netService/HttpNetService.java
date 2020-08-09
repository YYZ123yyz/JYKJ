package netService;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import www.jykj.com.jykj_zxyl.util.marqueeimage.transforms.DefaultTransformer;

import static com.hyphenate.util.EasyUtils.TAG;

/**
 * Created by yinzijing on 2017/11/1.
 */
public class HttpNetService {

    public static String requestClientVerify = "878e805913c5468da6d033ca5072071b";
    public static String requestLoginTokenValue = "eyJ0eXAiOiJKV1QiLCJ0eXBlIjoiSldUIiwiZW5jcnlwdGlvbiI6IkhTMjU2IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyUGhvbmUiOiIxODk4NzkzNTIyOCIsIk5vd0RhdGVUaW1lIjoxNTc4OTM1MDQ2Njg5LCJleHAiOjE1Nzg5ODUxMTJ9.v43YSbgk1KfkK9lVORLowj6Wf91DM_tB556NOp7Ila4";
    public Context context;

    /**
     * 调用后台接口的http请求
     *
     * @param json 需要传递过去的json字符串
     *             urlString   url字符串
     */
    public static String urlConnectionService(String json, String urlString) throws Exception {
        String retInfo = "";
        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         *  java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("requestClientVerify", requestClientVerify);
        connection.setRequestProperty("requestLoginTokenValue", requestLoginTokenValue);
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        /**
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
         */
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf8");
        out.write(json); //post的关键所在！
        out.flush();
        out.close();
        /**
         * 这样就可以发送一个看起来象这样的POST：
         * POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:
         * text/plain Content-type: application/x-www-form-urlencoded
         * Content-length: 99 username=bob password=someword
         */
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                l_urlStream, "utf8"));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            retInfo += sCurrentLine;
        }
        return retInfo;
    }

    //ybf https ssl
    public static HttpsURLConnection getHttpsURLConnection(String uri) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();
        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setRequestProperty("requestClientVerify", requestClientVerify);
        httpsConn.setRequestProperty("requestLoginTokenValue", requestLoginTokenValue);
        httpsConn.setSSLSocketFactory(ssf);
    //    httpsConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }


    public static String getUpgradeInfo(String json, String url) throws IOException {
        Log.e(TAG, "getUpgradeInfo");
        HttpsURLConnection connection = getHttpsURLConnection(url);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(json.getBytes("UTF-8"));//这样可以处理中文乱码问题
        out.flush();
        out.close();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        Log.e(TAG, "返回结果：sb==" + sb);
        reader.close();
        String result = sb.toString();
        // 断开连接
        connection.disconnect();
        return result;
    }


}
