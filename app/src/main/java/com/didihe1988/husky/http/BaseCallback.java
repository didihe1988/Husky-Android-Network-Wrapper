package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

import static android.os.Handler.Callback;

/**
 * Created by lml on 2014/9/23.
 */
/*
    HttpRequest request=new HttpRequest(url,params,new JsonCallback()
    {
        @Override
        public void onSuccess()
        {
        }
    });
    写library真是乐趣多多
*/
public abstract class BaseCallback {
    /*
    缺省包内可见 可以在HttpHandler内调用
     */
   void setCallback(Message msg)
   {
        /*
        判断是onSuceess还是onFailure
         */
        Bundle bundle=msg.getData();
        switch (msg.what)
        {
            case MessageType.REQUEST_SUCCESS:
                onSuceess(bundle.get("response"));
                break;
            case MessageType.REQUEST_FAILURE:
            default:
                onFailure(bundle.get("exception"));
        }
   }

    public abstract void onSuceess(Object object);

    public abstract  void onFailure(Object object);



}
