package com.esell.rtb;

import com.google.gson.annotations.SerializedName;

/**
 * rtb广告
 *
 * @author NiuLei
 * @date 2019/10/16 17:31
 */
public class RtbAD {
    /**
     * ad-id : 10661
     * show-time : 15
     * track-url : http://api6.pingxiaobao.com/v3/af8548dcbd3f0fb946a1aa33b82b699946484841.shtm
     * slot-id : 25075489
     * width : 1922
     * file-size : 359750
     * sign : a854e730c8f2cdd8573dcde7e35640bc
     * expire-time : 2019-10-31 23:59:59
     * type : IMG
     * url : https://file2.pingxiaobao.com/prd/1910/18/5038ce22045829ba145cf2fdafc342db.jpg
     * height : 1082
     */

    /**
     * 广告id
     */
    @SerializedName("ad-id")
    private int adId;
    /**
     * 显示时长 单位秒
     */
    @SerializedName("show-time")
    private int showTime;
    /**
     * 动态上报接口
     */
    @SerializedName("track-url")
    private String trackUrl;
    /**
     * 广告位id
     */
    @SerializedName("slot-id")
    private String slotId;
    /**
     * 宽
     */
    private int width;
    /**
     * 文件大小
     */
    @SerializedName("file-size")
    private int fileSize;
    /**
     * 签名
     */
    private String sign;
    /**
     * 过期时间 格式:"yyyy-MM-dd HH:mm:ss"
     */
    @SerializedName("expire-time")
    private String expireTime;
    /**
     * 类型
     */
    private String type;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 高
     */
    private int height;

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return "RtbResponseBean{" + "adId=" + adId + ", showTime=" + showTime + ", trackUrl='" + trackUrl + '\'' + ", slotId='" + slotId + '\'' + ", width=" + width + ", fileSize=" + fileSize + ", sign='" + sign + '\'' + ", expireTime='" + expireTime + '\'' + ", type='" + type + '\'' + ", url='" + url + '\'' + ", height=" + height + '}';
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof RtbAD && obj.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return ("adId : " + adId + ",trackUrl : " + trackUrl).hashCode();
    }
}
