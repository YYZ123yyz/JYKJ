/*
package www.jykj.com.jykj_zxyl.app_base.base_service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;

*/
/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-05-21 14:21
 *//*

public class PushIntentService extends UmengMessageService {
    private static final String TAG = PushIntentService.class.getName();


    @Override
    public void onMessage(Context context, Intent intent) {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//            String str=message.replaceAll("\\\\", "");//将URL中的反斜杠替换为空  加上之后收不到消息
        UMessage msg = null;
        try {
            msg = new UMessage(new JSONObject(message));
            Log.d(TAG,"message=" + message);    //消息体
            Log.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            Log.d(TAG, "title=" + msg.title);    //通知标题
            Map<String, String> extra = msg.extra;
            String msgType = extra.get("msgType");
            if (StringUtils.isNotEmpty(msgType)) {
                NotificationService.start(context, Integer.parseInt(msgType), message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
*/
