package com.hyphenate.easeui.hyhd.model;

/**
 * HuaweiApiClient 连接结果回调
 */
public interface ConnectHandler {
    /**
     * HuaweiApiClient 连接结果回调
     * @param rst 结果码
     */
    void onConnect(int rst);
}
