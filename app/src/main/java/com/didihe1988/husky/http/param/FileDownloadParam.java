package com.didihe1988.husky.http.param;

import java.io.File;

/**
 * Created by lml on 2014/10/31.
 */
public class FileDownloadParam extends Params{
    private File targetFile;

    private int threadNum;

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public FileDownloadParam(File targetFile, int threadNum) {
        this.targetFile = targetFile;
        this.threadNum = threadNum;
    }
}
