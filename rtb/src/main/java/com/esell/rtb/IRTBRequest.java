package com.esell.rtb;

/**
 * rtb请求接口
 *
 * @author NiuLei
 * @date 2019/11/8 15:20
 */
public interface IRTBRequest {
    /**
     * 默认超时时间
     */
    int TIMEOUT = 30 * 1000;
    /**
     * 工作线程请求广告
     *
     * @param url          路径
     * @param payload      参数
     * @param onAdListener 监听
     */
    void postOnWorkThread(String url, String payload, OnAdListener onAdListener);
}
