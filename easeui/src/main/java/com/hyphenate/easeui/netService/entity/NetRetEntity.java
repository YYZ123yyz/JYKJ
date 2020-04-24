package com.hyphenate.easeui.netService.entity;

public class NetRetEntity {
    private                 int                 resCode;                //执行状态，0：失败；1：成功；
    private                 String              resMsg;                 //返回消息，正常(异常)消息内容提醒
    private                 String              resJsonData;            //返回JSON字符串
    private                 String              resTokenData;           //返回Token数据，由登录时产生，后续所有接口请求调用时，都需要token的值
    private                 String              resData;                //返回非JSON数据


    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResJsonData() {
        return resJsonData;
    }

    public void setResJsonData(String resJsonData) {
        this.resJsonData = resJsonData;
    }

    public String getResTokenData() {
        return resTokenData;
    }

    public void setResTokenData(String resTokenData) {
        this.resTokenData = resTokenData;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }
}
