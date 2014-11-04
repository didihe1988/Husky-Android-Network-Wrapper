package com.didihe1988.husky.http.component;

import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;
import com.didihe1988.husky.http.executor.head.Content;

/**
 * Created by lml on 2014/11/4.
 */
public class HeadMessageSender  extends MessageSender{
    public void sendMessage(Handler handler,Content content)
    {
        Message msg=Message.obtain();
        msg.obj=content;
        msg.what= MessageType.REQUEST_SUCCESS;
        handler.sendMessage(msg);
    }
}
