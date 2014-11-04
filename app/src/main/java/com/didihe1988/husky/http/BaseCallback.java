package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

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
                onSuccess(bundle.getString("response"));
                break;
            case MessageType.REQUEST_FAILURE:
                onFailure((Exception)msg.obj,bundle.getInt("errCode"));
        }
   }

    public abstract void onSuccess(Object response);

    public abstract  void onFailure(Exception exception,int errCode);



}
