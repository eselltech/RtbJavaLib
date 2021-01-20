package com.esell.rtb;

/**
 * 扩展接口
 * @author NiuLei
 * @date 2021/1/20 11:41
 */
public interface IExtend {
    /**
     * 设置连接超时
     * @param connectTimeout
     */
    void setConnectTimeout(int connectTimeout);

    /**
     * 设置读取超时
     * @param readTimeout
     */
    void setReadTimeout(int readTimeout);
}
