package com.didihe1988.husky.constant;

/**
 * Created by lml on 2014/9/22.
 */
public class HttpConstant {
    public enum METHOD{
        GET("GET"),POST("POST"),PUT("PUT"),DELETE("DELETE"),TRACE("TRACE"),CCONNECT("CONNECT");

        private final String type;
        METHOD(String type)
        {
            this.type=type;
        }
    }
}
