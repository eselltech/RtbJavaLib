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
     * 数量
     */
    private int quantity;
    /**
     * 广告位id
     */
    @SerializedName("slot-id")
    private String slotId;
    /**
     * 请求类型 VDO IMG
     */
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public RtbRequestModel(String ip, int quantity, String slotId, String type,
                           String deviceUuid) {
        this.ip = ip;
        this.quantity = quantity;
        this.slotId = slotId;
        this.type = type;
        this.deviceUuid = deviceUuid;
    }
}
