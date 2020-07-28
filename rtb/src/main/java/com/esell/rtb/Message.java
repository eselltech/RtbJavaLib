package com.esell.rtb;

import java.util.Objects;

/**
 * 回调信息
 *
 * @author NiuLei
 * @date 2019/11/8 14:35
 */
public class Message {
    /**
     * 成功
     */
    static Message SUCCESS = new Message(0, "成功");

    /**
     * 失败 请求广告位为空
     */
    static Message FAILED_UNLINK_SLOT = new Message(-1, "未关联广告位");

    /**
     * 失败 请求路径为空
     */
    static Message FAILED_URL_EMPTY = new Message(-2, "url 为空");

    /**
     * 失败 请求设备为空
     */
    static Message FAILED_DEVICE_NULL = new Message(-3, "请求设备为空");
    /**
     * 失败 响应数据解析异常
     */
    static Message FAILED_RESPONSE_JSON_SYNTAX = new Message(-4, "响应数据解析异常");
    /**
     * 失败 响应结果为null
     */
    static Message FAILED_RESPONSE_RESULT_NULL = new Message(-5, "响应结果为null");
    /**
     * 失败 响应code!=0
     */
    static Message FAILED = new Message(-6, "code != 0");

    /**
     * 失败 网络请求异常
     */
    static Message FAILED_REQUEST_EXCEPTION = new Message(-7, "网络请求异常");

    public int code;
    public String message;

    Message(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message1 = (Message) o;
        return code == message1.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Message{" + "code=" + code + ", message='" + message + '\'' + '}';
    }
}
