package com.didihe1988.husky.http.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

import java.io.File;

/**
 * Created by lml on 2014/11/3.
 */
public class DownloadMessageSender extends MessageSender{
    public void sendMessage(Handler handler,File file)
    {
        Message message=Message.obtain();
        message.what=MessageType.REQUEST_SUCCESS;
        message.obj=file;
        handler.sendMessage(message);
    }


    public void sendProgressMessage(Handler handler,int fileLength,int curLength)
    {
        Message message=Message.obtain();
        message.what= MessageType.REQUEST_PROGRESS;
        Bundle bundle=new Bundle();
        bundle.putInt("fileLength",fileLength);
        bundle.putInt("curLength",curLength);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
