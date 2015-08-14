package com.didihe1988.husky.http;

import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.http.param.FileDownloadParam;
import com.didihe1988.husky.http.param.FileUploadParams;
import com.didihe1988.husky.http.param.Params;
import com.didihe1988.husky.http.param.PostParams;

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

    private RequestMethod method;
    private BaseCallback callback;
    private Handler handler;
    private HttpConfig config;
    private String url;
    private Params params;

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

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

   /* public HttpCallback getCallback() {
        return callback;
    }*/

    public void setCallback(BaseCallback callback) {
        this.callback = callback;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params=params;
    }

    /*
    Full Field Constructor
     */
    public HttpRequest(RequestMethod method,String url,Params params,BaseCallback callback,HttpConfig config) {
        this.method=method;
        this.handler = new HttpHandler();
        this.config = config;
        this.url = url;
        this.params=params;
        this.callback = callback;
    }

    /*
    sub situation
     */

    /**
    GET
     */
    public HttpRequest(String url,BaseCallback callback) {
        this.method=RequestMethod.GET;
        this.handler = new HttpHandler();
        this.config = new HttpConfig();
        this.url = url;
        this.params=null;
        this.callback = callback;
    }
    /**
    POST
     */
    public HttpRequest(String url,PostParams params,BaseCallback callback) {
        this.method=RequestMethod.POST;
        this.handler = new HttpHandler();
        this.config = new HttpConfig();
        this.url = url;
        this.params=params;
        this.callback = callback;
    }
    /**
    Download File
     */
    public HttpRequest(String url,FileDownloadParam params,FileDownloadCallBack callback) {
        this.method=RequestMethod.GET;
        this.handler = new HttpHandler();
        this.config = new HttpConfig();
        this.url = url;
        this.params=params;
        this.callback = callback;
    }
    /**
    Upload File
    */
    public HttpRequest(String url,FileUploadParams params,FileUploadCallback callback) {
        this.method=RequestMethod.POST;
        this.handler = new HttpHandler();
        this.config = new HttpConfig();
        this.url = url;
        this.params=params;
        this.callback = callback;
    }
    /**
     * HEAD
     */
    public HttpRequest(String url,HeadCallback callback) {
        this.method=RequestMethod.HEAD;
        this.handler = new HttpHandler();
        this.config = new HttpConfig();
        this.url = url;
        this.params=null;
        this.callback = callback;
    }

    private class HttpHandler extends  Handler
    {
        @Override
        public void handleMessage(Message msg) {
           callback.setCallback(msg);
        }
    }
}
