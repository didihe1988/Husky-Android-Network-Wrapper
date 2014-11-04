package com.didihe1988.husky.constant;

/**
 * Created by lml on 2014/9/24.
 */
public enum RequestMethod {
    GET("GET"),POST("POST"),PUT("PUT"),HEAD("HEAD"),DELETE("DELETE"),TRACE("TRACE"),CONNECT("CONNECT");

    private final String type;
    RequestMethod(String type)
    {
        this.type=type;
    }
}
