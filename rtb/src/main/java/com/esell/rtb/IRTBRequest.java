package com.esell.rtb;

import java.util.HashMap;

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
     * 工作线程post请求
     * @param url 路径
     * @param params 参数
     * @param callback 回调
     */
    void postOnWorkThread(String url, HashMap<String, String> params, Callback callback);

    /**
     * 工作线程post请求
     * @param url 路径
     * @param callback 回调
     */
    void postOnWorkThread(String url, Callback callback);

    @Deprecated
    String post(final String url, final HashMap<String, String> params);
    String post2(final String url, final HashMap<String, String> params) throws Exception;

    /**
     * 网络回调
     */
    interface Callback {
        /**
         * @param message  信息
         * @param response 响应
         */
        void onFinish(Message message, String response);
    }
}
