package com.esell.simple;

import com.esell.rtb.IRTBRequest;
import com.esell.rtb.Message;
import com.esell.rtb.OnAdListener;
import com.esell.rtb.RtbAD;
import com.esell.rtb.RtbManager;
import com.esell.rtb.RtbSlot;
import com.esell.rtb.YLog;

import java.util.List;

/**
 * @author NiuLei
 * @date 2019/11/8 16:15
 */
public class Test {
    public static void main(String[] args) {
        RtbManager rtbManager = RtbManager.getInstance();
        rtbManager.init("pxbAppId", "pxbAppKey", "unicode");
        rtbManager.request(new OnAdListener() {
            @Override
            public void onAd(Message message, List<RtbAD> adList) {
                YLog.d(message + "," + adList);
            }
        }, new RtbSlot("广告位id", "类型", 1/*数量*/));
//        List<RtbAD> rtbADS = rtbManager.requestSync();

        rtbManager.dynamicReport("广告的上报地址", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });

//        rtbManager.dynamicReportSync()
        rtbManager.staticReport("广告位id", "广告id", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });
//        rtbManager.staticReportSync()
//        staticReport(int slotId, int adId, double lat, double lon,
//        IRTBRequest.Callback callback)
    }
}
