package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;

/**
 * Created by lml on 2014/9/28.
 */
public abstract class FileUploadCallback extends BaseCallback{
    void setCallback(Message msg)
    {
        Bundle bundle=msg.getData();
        switch (msg.what)
        {
            case MessageType.REQUEST_PROGRESS:
                onProgress(bundle.getInt("fileCount"),bundle.getInt("curIndex"),bundle.getLong("fileLength"),bundle.getLong("curLength"));
                break;
            case MessageType.REQUEST_SUCCESS:
                onSuccess(bundle.getString("response"));
                break;
            case MessageType.REQUEST_FAILURE:
                onFailure((Exception)msg.obj,bundle.getInt("errCode"));
        }
    }

    public abstract void onProgress(int fileCount, int curFile, long count, long cur);
}
