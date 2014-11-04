package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

/**
 * Created by lml on 2014/11/4.
 */
public abstract class HeadCallback extends BaseCallback{
    void setCallback(Message msg)
    {
        /*
        判断是onSuceess还是onFailure
         */
        Bundle bundle=msg.getData();
        switch (msg.what)
        {
            case MessageType.REQUEST_SUCCESS:
                onSuccess(msg.obj);
                break;
            case MessageType.REQUEST_FAILURE:
                onFailure((Exception)msg.obj,bundle.getInt("errCode"));
        }
    }
}
