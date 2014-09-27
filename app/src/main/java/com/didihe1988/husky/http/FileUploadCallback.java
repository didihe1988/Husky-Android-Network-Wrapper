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
                onProgressUpdate((Integer)bundle.get("fileCount"),(Integer)bundle.get("curFile"),(Long)bundle.get("count"),(Long)bundle.get("cur"));
            case MessageType.REQUEST_SUCCESS:
                onSuceess(bundle.get("response"));
                break;
            case MessageType.REQUEST_FAILURE:
            default:
                onFailure(bundle.get("exception"));
        }
    }

    public abstract void onProgressUpdate(int fileCount, int curFile, long count, long cur);
}
