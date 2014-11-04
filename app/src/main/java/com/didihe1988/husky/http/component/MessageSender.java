package com.didihe1988.husky.http.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

/**
 * Created by lml on 2014/11/3.
 */
public class MessageSender {

    public void sendFailureMessage(Handler handler,Exception exception,int errCode)
    {
        Message msg=Message.obtain();
        msg.what=MessageType.REQUEST_FAILURE;
        msg.obj=exception;
        Bundle bundle=new Bundle();
        bundle.putInt("errCode",errCode);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    public void sendMessage(Handler handler,String response)
    {
        Message msg=Message.obtain();
        Bundle bundle=new Bundle();
        msg.what= MessageType.REQUEST_SUCCESS;
        bundle.putString("response",response);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}
