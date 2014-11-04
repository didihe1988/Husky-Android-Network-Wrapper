package com.didihe1988.husky.http.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

/**
 * Created by lml on 2014/11/3.
 */
public class UploadMessageSender extends MessageSender{

    public void sendProgressMessage(Handler handler,int fileCount,int curIndex,long fileLength,long curLength)
    {
        Message message=Message.obtain();
        message.what= MessageType.REQUEST_PROGRESS;
        Bundle bundle=new Bundle();
        bundle.putInt("fileCount",fileCount);
        bundle.putInt("curIndex",curIndex);
        bundle.putLong("fileLength",fileLength);
        bundle.putLong("curLength",curLength);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
