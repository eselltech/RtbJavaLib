package com.esell.simple;

import com.esell.rtb.Device;
import com.esell.rtb.Message;
import com.esell.rtb.OnAdListener;
import com.esell.rtb.RtbAD;
import com.esell.rtb.RtbManager2;
import com.esell.rtb.RtbSlot;
import com.esell.rtb.YLog;

import java.util.List;

/**
 * @author NiuLei
 * @date 2019/11/8 16:15
 */
public class Test {
    public static void main(String[] args) {
        RtbManager2 rtbManager2 = RtbManager2.getInstance();
        rtbManager2.init("pxbAppId", "pxbAppKey");
        rtbManager2.request(new OnAdListener() {
            @Override
            public void onAd(Message message, List<RtbAD> adList) {
                YLog.d(message + "," + adList);
            }
        },
        new RtbSlot("广告位id", "类型", 1/*数量*/),
        new Device("unicode"));
//        new Device("unicode","ip"));
//        new Device("unicode",113.957647,22.544867));
//        new Device("unicode",113.957647,22.544867,"ip"));

//        rtbManager2.requestSync();

//        rtbManager2.dynamicReport("广告的上报地址", new IRTBRequest.Callback() {
//            @Override
//            public void onFinish(Message message, String response) {
//                YLog.d(message + " , response : "+response);
//            }
//        });
//
//        rtbManager2.dynamicReportSync();
//
//        rtbManager2.staticReport("设备唯一标识","广告位id", "广告id", new IRTBRequest.Callback() {
//            @Override
//            public void onFinish(Message message, String response) {
//                YLog.d(message + " , response : "+response);
//            }
//        });
//        rtbManager2.staticReportSync()
    }
}
