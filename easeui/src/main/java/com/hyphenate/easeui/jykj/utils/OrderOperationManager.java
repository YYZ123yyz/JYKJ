package com.hyphenate.easeui.jykj.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.bean.GetdetailsBean;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.jykj.bean.ProvideBasicsDomain;
import com.hyphenate.easeui.jykj.bean.Restcommit;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Description:订单社交操作
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 14:38
 */
public class OrderOperationManager {
  private String   mNetRetStr;
  private Handler mHandler;
    private static class InstanceHolder {
        private static final OrderOperationManager INSTANCE = new OrderOperationManager();
    }

    public static OrderOperationManager getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * 订单操作接口
     *
     * @param mainDoctorCode  医生编码
     * @param mainDoctorName  医生姓名
     * @param signCode        签约订单编码
     * @param signNo          签约订单编号
     * @param mainPatientCode 患者编码
     * @param mainUserName    患者名称
     * @param confimresult    操作： 0拒绝，1同意，2需修改
     */
    public void sendOrderOperRequest(String mainDoctorCode, String mainDoctorName
            , String signCode, String signNo, String mainPatientCode
            , String mainUserName, String confimresult, String loginDoctorPosition,
                                     OnCallBackListener onCallBackListener) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", loginDoctorPosition);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        hashMap.put("signCode", signCode);
        hashMap.put("signNo", signNo);
        hashMap.put("mainPatientCode", mainPatientCode);
        hashMap.put("mainUserName", mainUserName);
        hashMap.put("confimresult", confimresult);
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(hashMap), Constant.SERVICEURL + "doctorSignControlle/operUpdSignPatientDoctorOrder");
                    Log.e("tag", "修改提交" + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:

                        break;

                }


            }
        };
    }

    public interface OnCallBackListener {

        void onResult(boolean isSucess, String msg);
    }
}
