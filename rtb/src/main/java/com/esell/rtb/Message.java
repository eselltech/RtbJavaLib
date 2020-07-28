package com.esell.rtb;

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
    static Message FAILED_DEVICE_NULL = new Message(-728, "请求设备为空");

    public int code;
    public String message;

    Message(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + "code=" + code + ", message='" + message + '\'' + '}';
    }
}
