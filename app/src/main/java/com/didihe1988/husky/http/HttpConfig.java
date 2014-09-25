package com.didihe1988.husky.http;

/**
 * Created by lml on 2014/9/22.
 */
public class HttpConfig {
    private final  static  int DEFAULT_READ_TIMEOUT=8000;
    private final  static  int DEFAULT_CONNET_TIMEOUT=8000;
    private final  static  boolean DEFAULT_USE_CACHES=false;
    private final  static  String DEFAULT_CONTENTTYPE="application/x-www-form-urlencoded";

   private boolean useCaches;

   private int connectTimeOut;

   private int readTimeOut;

    private String contentType;

    public boolean isUseCaches() {
        return useCaches;
    }

    public void setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HttpConfig()
    {
        useCaches=DEFAULT_USE_CACHES;
        connectTimeOut=DEFAULT_CONNET_TIMEOUT;
        readTimeOut=DEFAULT_READ_TIMEOUT;
        contentType=DEFAULT_CONTENTTYPE;
    }

    public HttpConfig(boolean useCaches, int connectTimeOut, int readTimeOut, String contentType) {
        this.useCaches = useCaches;
        this.connectTimeOut = connectTimeOut;
        this.readTimeOut = readTimeOut;
        this.contentType = contentType;
    }
}
