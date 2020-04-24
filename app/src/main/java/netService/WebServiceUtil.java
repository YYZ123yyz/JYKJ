package netService;

import android.app.Activity;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import www.jykj.com.jykj_zxyl.application.Constant;

/**
 * webservice 工具类
 */
public class WebServiceUtil {

    private                     Activity                            mActivity;                      //activity

    public WebServiceUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * webService获取数据方法
     * @param url              Url+命名空间
     * @param methodName      方法名
     * @param parmentString   参数
     */
    public void webServiceUtil(String url,String methodName, String parmentString) {
        SoapObject rpc = new SoapObject(url
                , methodName);
        rpc.addProperty("jsonLoginInfo",parmentString);
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        String seUrl = url+"?wsdl";
        HttpTransportSE transport = new HttpTransportSE(seUrl);
        try {
            String callUrl = url+"/"+methodName;
            // 调用WebService
            transport.call(callUrl, envelope);
            SoapObject  object2 = (SoapObject) envelope.bodyIn;
            JSONTokener jsonParser = new JSONTokener(object2.getProperty(0).toString());
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            String str = obj.toString();

        }catch(Exception e) {
            System.out.println("");
        }
    }
}
