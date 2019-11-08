package com.esell.rtb;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工具类
 *
 * @author NiuLei
 * @date 2019/11/8 14:26
 */
public class Tools {
    /**
     * 线程池
     */
    public static final ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * 字符串是否为空
     *
     * @param text 字符
     * @return 是否为空
     */
    public static boolean isEmpty(String text) {
        return text == null || 0 == text.length();
    }

    /**
     * 获取本地ip地址
     */
    public static String getLocalIpAddress() {
        String clientIP = null;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces == null) {
                return null;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress ip;
        Enumeration<InetAddress> address;
        // 遍历网卡设备
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            try {
                //过滤掉 loopback设备、虚拟网卡
                if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
                    continue;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            address = networkInterface.getInetAddresses();
            if (address == null) {
                continue;
            }
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    try {
                        clientIP = ip.toString().split("/")[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        clientIP = null;
                    }
                }
            }
        }
        return clientIP;
    }


    public static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * md5加密
     *
     * @param str 需加密字符
     */
    public static String md5Hex(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        int l = hash.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(240 & hash[i]) >>> 4];
            out[j++] = DIGITS_LOWER[15 & hash[i]];
        }
        return new String(out);
    }

    public static void post(String url, String payload, OnAdListener onAdListener) {

    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    public static void closeCloseable(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ignore) {
        }
    }

    /**
     * 异常转字符串
     *
     * @param throwable
     * @return
     */
    public static String throwable2String(Throwable throwable) {
        if (throwable != null) {
            PrintWriter printWriter = null;
            try {
                final StringWriter sw = new StringWriter();
                printWriter = new PrintWriter(sw);
                throwable.printStackTrace(printWriter);
                printWriter.flush();
                return sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Tools.closeCloseable(printWriter);
            }
        }
        return "";
    }


    /**
     * priority 转字符
     *
     * @param priority
     * @return
     */
    public static String priorityKey(int priority) {
        switch (priority) {
            case Log.INFO:
                return "INFO ";
            case Log.WARN:
                return "WARN ";
            case Log.ERROR:
                return "ERROR";
            default:
                return "DEBUG";
        }
    }
}
