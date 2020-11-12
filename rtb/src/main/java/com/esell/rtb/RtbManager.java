package com.esell.rtb;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * rtb管理
 * 因服务端多设备unicode特性 替换为{@link RtbManager2}
 * @author NiuLei
 * @date 2019/11/8 14:06
 */
@Deprecated
public final class RtbManager {
    private static final RtbManager RTB_MANAGER = new RtbManager();
    /**
     * 基本路径
     */
    private static final String URL_BASE = "http://api6.pingxiaobao.com/";

    /**
     * 广告路径
     */
    private static final String URL_AD = URL_BASE + "rtb/subscribe.shtml";
    /**
     * 静态上报路径
     */
    private static final String URL_STATIC = URL_BASE + "t.shtm";
    /**
     * 非法参数异常格式
     */
    final String illegalArgumentFormat = "It needs to be initialized before use.the %s isEmpty";
    /**
     * 签名格式
     */
    final String signFormat = "appid=%s&appkey=%s&payload=%s&sequence=%s&timestamp=%s&uuid=%s" +
            "&version=%s";
    /**
     * 路径格式
     */
    final String urlFormat = "%s?appid=%s&sequence=%s&timestamp=%s&uuid=%s&version=%s&sign=%s";

    final String staticUrlFormat =
            "%s?sid=%s&aid=%s&mid=%s&uid=%s&ip=%s&mac=%s&lat=%s&lon=%s&tt" + "=%s";
    /**
     * 屏效宝广告默认版本
     */
    final String VERSION = "1.3";
    /**
     * 公司对应唯一id
     */
    private String appId;
    /**
     * 对应key
     */
    private String appKey;
    /**
     * 设备唯一id
     */
    private String unicode;

    /**
     * 广告请求
     */
    private final IRTBRequest request = new HttpUrlConnectionImp();
    /**
     * json解析框架
     */
    final Gson gson = new Gson();
    private String ip;
    private double longitude;
    private double latitude;

    private RtbManager() {
    }

    /**
     * 获取实例
     */
    public static RtbManager getInstance() {
        return RTB_MANAGER;
    }

    /**
     * 获取实例
     *
     * @param appId   公司对应唯一id
     * @param appKey  对应key
     * @param unicode 设备唯一id
     */
    public final void init(String appId, String appKey, String unicode) {
        init(appId,appKey,unicode,-1,-1,null);
    }
    /**
     * 获取实例
     *
     * @param appId   公司对应唯一id
     * @param appKey  对应key
     * @param unicode 设备唯一id
     */
    public final void init(String appId, String appKey, String unicode,String ip) {
        init(appId,appKey,unicode,-1,-1,ip);
    }
    /**
     * 获取实例
     *
     * @param appId   公司对应唯一id
     * @param appKey  对应key
     * @param unicode 设备唯一id
     */
    public final void init(String appId, String appKey, String unicode,double longitude,double latitude) {
        init(appId,appKey,unicode,longitude,latitude,null);
    }
    /**
     * 获取实例
     *
     * @param appId   公司对应唯一id
     * @param appKey  对应key
     * @param unicode 设备唯一id
     * @param longitude 经度
     * @param latitude 纬度
     * @param ip ip
     */
    public final void init(String appId, String appKey, String unicode,double longitude,double latitude,String ip) {
        YLog.d("初始化");
        this.appId = appId;
        this.appKey = appKey;
        this.unicode = unicode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ip = ip;
    }

    /**
     * 检查init参数
     */
    final void checkInit() {
        if (Tools.isEmpty(appId)) {
            throw new IllegalArgumentException(String.format(illegalArgumentFormat, "appId"));
        }
        if (Tools.isEmpty(appKey)) {
            throw new IllegalArgumentException(String.format(illegalArgumentFormat, "appKey"));
        }
        if (Tools.isEmpty(unicode)) {
            throw new IllegalArgumentException(String.format(illegalArgumentFormat, "unicode"));
        }
    }

    /**
     * 屏效宝广告请求
     *
     * @param onAdListener 广告监听
     * @param rtbSlot      屏效宝广告位
     */
    public void request(final OnAdListener onAdListener, RtbSlot rtbSlot) {
        checkInit();
        if (onAdListener == null) {
            YLog.e("onAdListener == null");
            return;
        }
        if (rtbSlot == null) {
            onAdListener.onAd(Message.FAILED_UNLINK_SLOT, null);
            return;
        }
        YLog.d("请求广告" + rtbSlot);
        final long currentTimeMillis = System.currentTimeMillis();
        final String payload = getPayload(rtbSlot);
        /*签名格式字符串*/
        final String signFormatStr = String.format(signFormat, appId, appKey, payload,
                currentTimeMillis, currentTimeMillis, unicode, VERSION);
        /*最终签名*/
        final String sign = Tools.md5Hex(signFormatStr);
        /*请求路径*/
        final String url = String.format(urlFormat, URL_AD, appId, currentTimeMillis,
                currentTimeMillis, unicode, VERSION, sign);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("payload", payload);
        request.postOnWorkThread(url, params, new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                if (Message.SUCCESS.equals(message)) {
                    Result<List<RtbAD>> result;
                    try {
                        result = gson.fromJson(response, new TypeToken<Result<List<RtbAD>>>() {
                        }.getType());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        onAdListener.onAd(new Message("JsonSyntaxException".hashCode(),
                                Tools.throwable2String(e)), null);
                        return;
                    }
                    if (result == null) {
                        onAdListener.onAd(new Message("result == null".hashCode(),
                                "result == " + "null"), null);
                        return;
                    }
                    if (result.isSuccess()) {
                        onAdListener.onAd(message, result.getPayload());
                        return;
                    }
                }
                onAdListener.onAd(message, null);
            }
        });
    }

    private String getPayload(RtbSlot rtbSlot) {
        /*请求类*/
        RtbRequestModel rtbRequestBean = new RtbRequestModel(
                rtbSlot.quantity, rtbSlot.pxbSlotId, unicode);
        rtbRequestBean.setIp(ip);
        rtbRequestBean.setLongitude(longitude);
        rtbRequestBean.setLatitude(latitude);
        JsonElement jsonElement = gson.toJsonTree(rtbRequestBean);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (ip == null || ip.trim().length() == 0) {
                jsonObject.remove("ip");
            }
            if (longitude == -1 || latitude == -1) {
                jsonObject.remove("longitude");
                jsonObject.remove("latitude");
            }
            /*对象转json格式*/
            return jsonObject.toString();
        }
        return "";
    }

    /**
     * 屏效宝广告请求
     *
     * @param rtbSlot      屏效宝广告位
     */
    public List<RtbAD> requestSync(RtbSlot rtbSlot) {
        if (rtbSlot == null) {
            return null;
        }
        checkInit();
        YLog.d("请求广告" + rtbSlot);
        final long currentTimeMillis = System.currentTimeMillis();
        final String payload = getPayload(rtbSlot);
        /*签名格式字符串*/
        final String signFormatStr = String.format(signFormat, appId, appKey, payload,
                currentTimeMillis, currentTimeMillis, unicode, VERSION);
        /*最终签名*/
        final String sign = Tools.md5Hex(signFormatStr);
        /*请求路径*/
        final String url = String.format(urlFormat, URL_AD, appId, currentTimeMillis,
                currentTimeMillis, unicode, VERSION, sign);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("payload", payload);
        String response = request.post(url, params);
        if (response == null) {
            return null;
        }
        Result<List<RtbAD>> result;
        try {
            result = gson.fromJson(response, new TypeToken<Result<List<RtbAD>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        if (result == null) {
            return null;
        }
        if (result.isSuccess()) {
            return result.getPayload();
        }
        return null;
    }

    /**
     * 屏效宝广告请求
     *
     * @param onAdListener 广告监听
     * @param rtbSlots     多个广告位
     */
    public void request(final OnAdListener onAdListener, RtbSlot... rtbSlots) {
        if (onAdListener == null) {
            YLog.e("onAdListener == null");
            return;
        }
        if (rtbSlots == null || rtbSlots.length == 0) {
            onAdListener.onAd(Message.FAILED_UNLINK_SLOT, null);
            return;
        }
        YLog.d("批量请求广告" + Arrays.toString(rtbSlots));
        final CountDownLatch countDownLatch = new CountDownLatch(rtbSlots.length);
        final List<RtbAD> allAdList = Collections.synchronizedList(new LinkedList<RtbAD>());
        Tools.POOL.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await(IRTBRequest.TIMEOUT, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    onAdListener.onAd(Message.SUCCESS, allAdList);
                }
            }
        });
        for (final RtbSlot rtbSlot : rtbSlots) {
            request(new OnAdListener() {
                @Override
                public void onAd(Message message, List<RtbAD> adList) {
                    if (Message.SUCCESS.equals(message) && adList != null) {
                        allAdList.addAll(adList);
                    } else {
                        YLog.d(rtbSlot.toString() + " " + message.toString());
                    }
                    countDownLatch.countDown();
                }
            }, rtbSlot);
        }
    }

    /**
     * 屏效宝广告请求
     *
     * @param rtbSlots     多个广告位
     */
    public List<RtbAD> requestSync(RtbSlot[] rtbSlots) {
        if (rtbSlots == null || rtbSlots.length == 0) {
            return null;
        }
        YLog.d("批量请求广告" + Arrays.toString(rtbSlots));
        final List<RtbAD> allAdList = Collections.synchronizedList(new LinkedList<RtbAD>());
        for (final RtbSlot rtbSlot : rtbSlots) {
            List<RtbAD> request = requestSync(rtbSlot);
            if (request != null && !request.isEmpty()) {
                allAdList.addAll(request);
            }
        }
        return allAdList;
    }

    /**
     * 静态上报
     *
     * @param slotId 广告位id
     * @param adId   广告id
     */
    public void staticReport(String slotId, String adId, IRTBRequest.Callback callback) {
        staticReport(slotId, adId, 0.0D, 0.0D, callback);
    }

    /**
     * 静态上报
     *
     * @param slotId 广告位id
     * @param adId   广告id
     */
    public String staticReportSync(String slotId, String adId) {
        return staticReportSync(slotId, adId, 0.0D, 0.0D);
    }

    /**
     * 静态上报
     *
     * @param slotId   广告位id
     * @param adId     广告id
     * @param lat      经纬度
     * @param lon      经纬度
     * @param callback 回调
     */
    public void staticReport(String slotId, String adId, double lat, double lon,
                             IRTBRequest.Callback callback) {
        checkInit();
        String mid = "";
        String uid = unicode;
        String ip = Tools.getLocalIpAddress();
        List<String> macList = null;
        try {
            macList = Tools.getMacList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mac = macList == null || macList.isEmpty() ? "" : macList.get(0);
        String tt = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String url = String.format(staticUrlFormat, URL_STATIC, slotId, adId, mid, uid, ip, mac,
                lat, lon, tt);
        request.postOnWorkThread(url, callback);
    }


    /**
     * 静态上报
     *
     * @param slotId   广告位id
     * @param adId     广告id
     * @param lat      经纬度
     * @param lon      经纬度
     */
    public String staticReportSync(String slotId, String adId, double lat, double lon) {
        checkInit();
        String mid = "";
        String uid = unicode;
        String ip = Tools.getLocalIpAddress();
        List<String> macList = null;
        try {
            macList = Tools.getMacList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mac = macList == null || macList.isEmpty() ? "" : macList.get(0);
        String tt = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String url = String.format(staticUrlFormat, URL_STATIC, slotId, adId, mid, uid, ip, mac,
                lat, lon, tt);
        return request.post(url, null);
    }
    /**
     * 动态上报
     *
     * @param trackUrl 上报路径
     */
    public void dynamicReport(String trackUrl, IRTBRequest.Callback callback) {
        checkInit();
        request.postOnWorkThread(trackUrl, callback);
    }

    /**
     * 动态上报
     *
     * @param trackUrl 上报路径
     */
    public String dynamicReportSync(String trackUrl) {
        checkInit();
        return request.post(trackUrl, null);
    }
}
