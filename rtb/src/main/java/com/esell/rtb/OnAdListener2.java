package com.esell.rtb;

import java.util.List;

/**
 * 多广告位请求响应
 * @author NiuLei
 * @date 2019/10/21 10:07
 */
public interface OnAdListener2 {
    /**
     * 响应回调
     * @param list 响应列表
     */
    void onResponse(List<Response> list);
}
