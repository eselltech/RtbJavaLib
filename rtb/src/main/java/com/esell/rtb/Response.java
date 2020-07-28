package com.esell.rtb;

import java.util.List;

/**
 * 响应
 * @author NiuLei
 * @date 2020/7/28 09:51
 */
public class Response {
    /**
     * 唯一标识
     */
    private String unicode;

    /**
     * 广告位id
     */
    public String pxbSlotId;

    Message message;
    List<RtbAD> list;

    public Response(Message message, List<RtbAD> list) {
        this.message = message;
        this.list = list;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<RtbAD> getList() {
        return list;
    }

    public void setList(List<RtbAD> list) {
        this.list = list;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getPxbSlotId() {
        return pxbSlotId;
    }

    public void setPxbSlotId(String pxbSlotId) {
        this.pxbSlotId = pxbSlotId;
    }
}
