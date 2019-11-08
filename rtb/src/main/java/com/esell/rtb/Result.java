package com.esell.rtb;

/**
 * 响应数据基类
 *
 * @author NiuLei
 * @date 2019/10/22 14:57
 */
final class Result<T> {
    /**
     * 响应成功
     */
    private final static int SUCCESS_CODE = 0;

    /**
     * code : 0
     * message : OK
     * payload : {}
     */

    private int code;
    private String message;
    private T payload;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * 是否响应成功
     */
    public boolean isSuccess() {
        return SUCCESS_CODE == code;
    }
}
