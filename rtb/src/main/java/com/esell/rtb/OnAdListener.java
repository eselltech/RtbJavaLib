package com.esell.rtb;

import java.util.List;

/**
 * 广告监听
 *
 * @author NiuLei
 * @date 2019/10/21 10:07
 */
public interface OnAdListener {
    /**
     * 回调
     *
     * @param message 信息
     * @param adList 广告列表
     */
    void onAd(Message message,List<RtbAD> adList);
}
