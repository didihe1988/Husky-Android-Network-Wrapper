package com.didihe1988.husky.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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
public abstract class HttpCallback{


   public void setCallback(Object object)
   {
        /*
        判断是onSuceess还是onFailure
         */

   }

    public abstract void onSuceess();

    public abstract  void onFailure();



}
