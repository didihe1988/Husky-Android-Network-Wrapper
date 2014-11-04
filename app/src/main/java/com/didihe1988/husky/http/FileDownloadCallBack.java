package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

import java.io.File;

/**
 * Created by lml on 2014/11/3.
 */
public abstract class FileDownloadCallBack extends BaseCallback{
    void setCallback(Message msg)
    {
        Bundle bundle=msg.getData();
        switch (msg.what)
        {
            case MessageType.REQUEST_PROGRESS:
                onProgress(bundle.getInt("fileLength"),bundle.getInt("curLength"));
                break;
            case MessageType.REQUEST_SUCCESS:
                onSuccess((File)msg.obj);
                break;
            case MessageType.REQUEST_FAILURE:
                onFailure((Exception)msg.obj,bundle.getInt("errCode"));
        }
    }

    public abstract void onProgress(int fileLength, int curLength);
}
