package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.exception.DonwloadFailureException;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.component.DownloadMessageSender;
import com.didihe1988.husky.http.executor.download.DownloadThread;
import com.didihe1988.husky.http.param.FileDownloadParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import static com.didihe1988.husky.constant.RequestMethod.HEAD;

/**
 * Created by lml on 2014/10/31.
 */
public class DownloadExecutor extends Executor {
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
    /*
    For onProcess
     */
    private int curLength;

    private DownloadMessageSender sender;

    public File getSaveFile()
    {
        return this.saveFile;
    }

    public int getBlock()
    {
        return this.block;
    }

    protected DownloadExecutor(HttpRequest request)
    {
        super(request);
        sender=new DownloadMessageSender();
        this.threadNum=((FileDownloadParam)request.getParams()).getThreadNum();
        this.saveFile=((FileDownloadParam)request.getParams()).getTargetFile();
        this.threads=new DownloadThread[threadNum];
        try {
            initField();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("block: "+this.block);
    }

    private void initField() throws IOException {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(request.getUrl());
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HEAD.name());
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
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        }
        catch (IOException e) {
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }
    }

    @Override
    public void execute() {
        try {
            setFileLength();
            startDownloadThread();
            /*
           检验 fileLength!=0 避免服务器404，fileLength为0返回成功
             */
            if((curLength==fileLength)&&(fileLength!=0))
            {
                sender.sendMessage(request.getHandler(),saveFile);
            }
            else
            {
                String detailMessage=curLength==0?"Download can't start ":"Download hasn't finished";
                sender.sendFailureMessage(request.getHandler(),new DonwloadFailureException(detailMessage),-1);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
        CountDownLatch doneSignal=new CountDownLatch(threads.length);
        for(int index=0;index<threads.length;index++)
        {
            threads[index]=new DownloadThread(doneSignal,this,index);
            threads[index].start();
        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addCurLength(int length)
    {
        this.curLength+=length;
        sender.sendProgressMessage(request.getHandler(),fileLength,curLength);
    }

    public synchronized void handleThreadException(Exception exception,HttpURLConnection threadConnection)
    {

        int errCode = -1;
        try {
            errCode = threadConnection!=null?threadConnection.getResponseCode():-1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendFailureMessage(request.getHandler(),exception,errCode);
    }

}
