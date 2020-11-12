package com.esell.rtb;

import com.google.gson.annotations.SerializedName;

/**
 * rtb请求类
 * @author NiuLei
 * @date 2019/10/16 17:21
 */
final class RtbRequestModel {
    /**
     * ip
     */
    private String ip;

    /**
     * 设备所处位置经度
     */
    private double longitude;
    /**
     * 设备所处位置纬度
     */
    private double latitude;

    /**
     * 数量
     */
    private int quantity;
    /**
     * 广告位id
     */
    @SerializedName("slot-id")
    private String slotId;
    /**
     * 设备编号
     */
    @SerializedName("device-uuid")
    private String deviceUuid;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public RtbRequestModel(int quantity, String slotId,
                           String deviceUuid) {
        this.quantity = quantity;
        this.slotId = slotId;
        this.deviceUuid = deviceUuid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
