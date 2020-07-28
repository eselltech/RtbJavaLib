package com.esell.rtb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * 原生实现
 *
 * @author NiuLei
 * @date 2019/11/8 15:22
 */
final class HttpUrlConnectionImp implements IRTBRequest {

    @Override
    public void postOnWorkThread(final String url, final HashMap<String, String> params,
                                 final Callback callback) {
        Tools.POOL.execute(new Runnable() {
            @Override
            public void run() {
                post(url, params, callback);
            }
        });
    }

    @Override
    public void postOnWorkThread(String url, Callback callback) {
        postOnWorkThread(url, null, callback);
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
        String line;
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

    /**
     * post传参
     *
     * @param httpURLConnection
     * @param params
     * @return
     * @throws IOException
     */
    private OutputStream writeParams(HttpURLConnection httpURLConnection,
                                     HashMap<String, String> params) throws IOException {
        if (params != null && params.size() > 0) {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            StringBuilder paramsBuilder = new StringBuilder();
            for (HashMap.Entry<String, String> stringStringEntry : params.entrySet()) {
                String key = stringStringEntry.getKey();
                String value = stringStringEntry.getValue();
                paramsBuilder.append(key);
                paramsBuilder.append("=");
                paramsBuilder.append(value);
                paramsBuilder.append("&");
            }
            paramsBuilder.deleteCharAt(paramsBuilder.length() - 1);
            YLog.d("paramsBuilder : " + paramsBuilder.toString());
            outputStream.write(paramsBuilder.toString().getBytes("UTF-8"));
            paramsBuilder.setLength(0);
            outputStream.flush();
            return outputStream;
        }
        return null;
    }

    /**
     * post请求广告
     *
     * @param url      路径
     * @param params   参数
     * @param callback
     */
    final void post(final String url, final HashMap<String, String> params,
                    final Callback callback) {
        if (callback == null) {
            return;
        }
        if (Tools.isEmpty(url)) {
            callback.onFinish(Message.FAILED_URL_EMPTY, null);
            return;
        }
        HttpURLConnection httpURLConnection;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            configConnection(httpURLConnection);
            httpURLConnection.connect();
            outputStream = writeParams(httpURLConnection, params);
            int responseCode = httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                bufferedReader =
                        new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String readResponseBody = readResponseBody(bufferedReader);
                YLog.d("readResponseBody : " + readResponseBody);
                callback.onFinish(Message.SUCCESS, readResponseBody);
            } else {
                callback.onFinish(new Message(responseCode,
                        httpURLConnection.getResponseMessage()), null);
            }
        } catch (Exception e) {
            callback.onFinish(new Message("Exception".hashCode(), Tools.throwable2String(e)), null);
        } finally {
            Tools.closeCloseable(outputStream);
            Tools.closeCloseable(bufferedReader);
        }
    }

    /**
     * post请求广告
     *
     * @param url    路径
     * @param params 参数
     */
    @Override
    public String post(final String url, final HashMap<String, String> params) {
        try {
            return post2(url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String post2(String url, HashMap<String, String> params) throws Exception {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            configConnection(httpURLConnection);
            httpURLConnection.connect();
            outputStream = writeParams(httpURLConnection, params);
            int responseCode = httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                bufferedReader =
                        new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                return readResponseBody(bufferedReader);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            Tools.closeCloseable(outputStream);
            Tools.closeCloseable(bufferedReader);
        }
        return null;
    }


}
