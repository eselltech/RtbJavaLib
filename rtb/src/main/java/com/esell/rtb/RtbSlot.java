package com.esell.rtb;

/**
 * rtb广告位配置
 *
 * @author NiuLei
 * @date 2019/11/4 15:09
 */
public class RtbSlot {
    /**
     * 广告位id
     */
    public String pxbSlotId;
    /**
     * 资源类型
     */
    @Deprecated
    public String type;
    /**
     * 数量
     */
    public int quantity = 0;
    @Deprecated
    public RtbSlot(String pxbSlotId, String type, int quantity) {
        this.pxbSlotId = pxbSlotId;
        this.type = type;
        this.quantity = quantity;
    }
    @Deprecated
    public RtbSlot(String pxbSlotId, String type) {
        this.pxbSlotId = pxbSlotId;
        this.type = type;
    }
    public RtbSlot(String pxbSlotId) {
        this.pxbSlotId = pxbSlotId;
    }

    public RtbSlot(String pxbSlotId,int quantity) {
        this.pxbSlotId = pxbSlotId;
        this.quantity = quantity;
    }

    public String getPxbSlotId() {
        return pxbSlotId;
    }

    public void setPxbSlotId(String pxbSlotId) {
        this.pxbSlotId = pxbSlotId;
    }
    @Deprecated
    public String getType() {
        return type;
    }
    @Deprecated
    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RtbSlot{" + "pxbSlotId='" + pxbSlotId + '\'' + ", type='" + type + '\'' + ", " +
                "quantity=" + quantity + '}';
    }
}
