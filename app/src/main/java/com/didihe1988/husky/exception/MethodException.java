package com.didihe1988.husky.exception;

import com.didihe1988.husky.http.executor.Executor;

/**
 * Created by lml on 2014/9/27.
 */
public class MethodException extends Exception{
    public MethodException(String detailMessage) {
        super(detailMessage);
    }
}
