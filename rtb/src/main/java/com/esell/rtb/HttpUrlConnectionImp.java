package com.esell.rtb;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * 原生实现
 *
 * @author NiuLei
 * @date 2019/11/8 15:22
 */
final class HttpUrlConnectionImp implements IRTBRequest {
    /**
     * json解析框架
     */
    final Gson gson = new Gson();

    /**
     * post请求广告
     *
     * @param url          路径
     * @param payload      参数
     * @param onAdListener 监听
     */
    final void post(String url, String payload, OnAdListener onAdListener) {
        if (onAdListener == null) {
            return;
        }
        if (Tools.isEmpty(url)) {
            onAdListener.onAd(Message.FAILED_URL_EMPTY, null);
            return;
        }
        HttpURLConnection httpURLConnection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            configConnection(httpURLConnection);
            httpURLConnection.connect();
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(("payload=" + payload).getBytes("UTF-8"));
            outputStream.flush();
            int responseCode = httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                bufferedReader =
                        new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String readResponseBody = readResponseBody(bufferedReader);
                List<RtbAD> parse = parse(readResponseBody);
                onAdListener.onAd(Message.SUCCESS, parse);
            } else {
                onAdListener.onAd(new Message(responseCode,
                        httpURLConnection.getResponseMessage()), null);
            }
        } catch (Exception e) {
            onAdListener.onAd(new Message("Exception".hashCode(), Tools.throwable2String(e)), null);
        } finally {
            Tools.closeCloseable(outputStream);
            Tools.closeCloseable(bufferedReader);
        }
    }

    @Override
    public void postOnWorkThread(final String url, final String payload,
                                 final OnAdListener onAdListener) {
        Tools.pool.execute(new Runnable() {
            @Override
            public void run() {
                post(url, payload, onAdListener);
            }
        });
    }

    /**
     * 解析响应数据
     *
     * @param readResponseBody json响应数据
     * @return 对象
     */
    private List<RtbAD> parse(String readResponseBody) {
        Result<List<RtbAD>> result = null;
        result = gson.fromJson(readResponseBody, new TypeToken<Result<List<RtbAD>>>() {
        }.getType());
        if (result == null) {
            return null;
        }
        if (result.isSuccess()) {
            return result.getPayload();
        }
        return null;
    }

    /**
     * 读取响应数据
     *
     * @param bufferedReader
     * @return
     * @throws IOException
     */
    private String readResponseBody(BufferedReader bufferedReader) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder(1024);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        try {
            return stringBuilder.toString();
        } finally {
            stringBuilder.setLength(0);
        }
    }

    /**
     * 配置链接
     *
     * @param httpURLConnection
     * @throws ProtocolException
     */
    private void configConnection(HttpURLConnection httpURLConnection) throws ProtocolException {
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        httpURLConnection.setRequestProperty("Connection", "close");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
    }
}
