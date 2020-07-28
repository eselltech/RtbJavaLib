package com.esell.rtb;

/**
 * 设备属性
 * @author NiuLei
 * @date 2020/7/28 09:35
 */
public class Device {
    /**
     * 唯一标识
     */
    private String unicode;
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

    public Device(String unicode) {
        this.unicode = unicode;
    }

    public Device(String unicode, String ip) {
        this.unicode = unicode;
        this.ip = ip;
    }

    public Device(String unicode, double longitude, double latitude) {
        this.unicode = unicode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Device(String unicode,double longitude, double latitude,String ip) {
        this.unicode = unicode;
        this.ip = ip;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
