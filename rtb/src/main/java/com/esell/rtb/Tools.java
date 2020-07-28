package com.esell.rtb;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 工具类
 *
 * @author NiuLei
 * @date 2019/11/8 14:26
 */
public class Tools {

    /**
     * 线程池 最大线程数
     */
    public static final int maximumPoolSize = 128;
    /**
     * 线程池 线程存活时长 秒
     */
    public static final long keepAliveTime = 20L;
    /**
     * 线程池
     */
    public static final ExecutorService POOL =  new ThreadPoolExecutor(0,maximumPoolSize, keepAliveTime,TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

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
        while (Objects.requireNonNull(interfaces).hasMoreElements()) {
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
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && !ip.getHostAddress().contains(":")) {
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

    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (en.hasMoreElements()) {
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                sb.delete(0, sb.length());
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        /*去重，别忘了同一个网卡的ipv4,ipv6得到的mac都是一样的，肯定有重复，下面这段代码是。。流式处理*/
        List<Object> collect = tmpMacList.stream().distinct().collect(Collectors.toList());
        tmpMacList.clear();
        for (Object o : collect) {
            tmpMacList.add(o.toString());
        }
        return tmpMacList;
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
