package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.download.DownloadThread;
import com.didihe1988.husky.http.param.FileDownloadParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/10/31.
 */
public class DownloadExecutor extends Executor{
    /*
    From HttpRequest
     */
    private int threadNum;

    private File saveFile;

    private DownloadThread[] threads;
    /*
    From Source File
     */
    private int block;

    private int fileLength;

    protected DownloadExecutor(HttpRequest request)
    {
        super(request);
        this.threadNum=((FileDownloadParam)request.getParams()).getThreadNum();
        this.saveFile=((FileDownloadParam)request.getParams()).getTargetFile();
        this.threads=new DownloadThread[threadNum];
        initField();
        System.out.println("block: "+this.block);
    }

    private void initField()
    {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(request.getUrl());
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(GET.name());
            connection.setUseCaches(false);
            connection.setReadTimeout(12000);
            connection.setConnectTimeout(12000);
            connection.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg");
            connection.connect();
            if(connection.getResponseCode()==200)
            {
                this.fileLength=connection.getContentLength();
                this.block=(fileLength % threadNum ==0)? fileLength/threadNum:fileLength/threadNum+1;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }
    }

    @Override
    public Object execute() {
        setFileLength();
        startDownloadThread();
        return "success";
    }

    private void setFileLength()
    {
        try {
            RandomAccessFile file = new RandomAccessFile(this.saveFile,"rw");
            file.setLength(this.fileLength);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startDownloadThread()
    {
        for(int index=0;index<threads.length;index++)
        {
            threads[index]=new DownloadThread(request.getUrl(),this.saveFile,this.block,index);
            threads[index].start();
        }
    }


}
