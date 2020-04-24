package com.hyphenate.easeui.netService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yinzijing on 2017/11/1.
 */
public class HttpNetService {

    private        static         String                  requestClientVerify = "878e805913c5468da6d033ca5072071b";
    private        static         String                  requestLoginTokenValue = "eyJ0eXAiOiJKV1QiLCJ0eXBlIjoiSldUIiwiZW5jcnlwdGlvbiI6IkhTMjU2IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyUGhvbmUiOiIxODk4NzkzNTIyOCIsIk5vd0RhdGVUaW1lIjoxNTc4OTM1MDQ2Njg5LCJleHAiOjE1Nzg5ODUxMTJ9.v43YSbgk1KfkK9lVORLowj6Wf91DM_tB556NOp7Ila4";

    /**
     * 调用后台接口的http请求
     * @param   json  需要传递过去的json字符串
     *                urlString   url字符串
     */
    public static String urlConnectionService(String json, String urlString) throws Exception {
        String retInfo = "";
        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         *  java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("requestClientVerify",requestClientVerify);
        connection.setRequestProperty("requestLoginTokenValue",requestLoginTokenValue);
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
                l_urlStream,"utf8"));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            retInfo += sCurrentLine;
        }
        return retInfo;
    }


}
