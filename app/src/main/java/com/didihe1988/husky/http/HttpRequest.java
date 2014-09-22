package com.didihe1988.husky.http;

import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.HttpConstant;
import com.didihe1988.husky.http.HttpConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Map;

/**
 * Created by lml on 2014/9/23.
 */
/*
    some class implements Runnable

    HttpRequest request=requestQueue.deQue();
    (request_param);
    sendMessage(request_handle);
 */

/*
    HttpRequest request=new HttpRequest(new Handler(),,);
*/
public class HttpRequest {
    private HttpCallback callback;
    private Handler handler;
    private HttpConfig config;
    private String url;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public HttpConfig getConfig() {
        return config;
    }

    public void setConfig(HttpConfig config) {
        this.config = config;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpCallback getCallback() {
        return callback;
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }

    public HttpRequest(String url,HttpConfig config,HttpCallback callback) {
        this.callback = callback;
        this.handler =new HttpHandler();
        this.config = config;
        this.url = url;
    }

    private class HttpHandler extends  Handler
    {
        @Override
        public void handleMessage(Message msg) {
           callback.setCallback(msg);
        }
    }
}
