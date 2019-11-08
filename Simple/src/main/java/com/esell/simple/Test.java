package com.esell.simple;

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
        rtbManager.init("vazazjhenhc5psfz", "71vr6anlbg2f9lvhbbhgj8fy79b55xuc", "419102906489");
        rtbManager.request(new OnAdListener() {
            @Override
            public void onAd(Message message, List<RtbAD> adList) {
                YLog.d(message + "," + adList);
            }
        }, new RtbSlot("25075489", "IMG", 1), new RtbSlot("25075489", "IMG", 1), new RtbSlot(
                "25075489", "IMG", 1));

    }
}
