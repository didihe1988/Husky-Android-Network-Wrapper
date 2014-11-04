package com.didihe1988.husky.http.executor.download;

import com.didihe1988.husky.http.executor.DownloadExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/10/31.
 */
public class DownloadThread extends Thread{
    private CountDownLatch doneSignal;

    private DownloadExecutor executor;

    private int index;

    public DownloadThread(CountDownLatch doneSignal,DownloadExecutor executor,int index) {
        this.doneSignal=doneSignal;
        this.executor=executor;
        this.index = index;
    }

    @Override
    public void run(){
        super.run();
        HttpURLConnection connection=null;
        try {
            URL url=new URL(executor.getRequest().getUrl());
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(GET.name());
            connection.setUseCaches(false);
            connection.setReadTimeout(12000);
            connection.setConnectTimeout(12000);
            connection.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg");
            //("Range", "bytes=" + start + "-" + end + "")
            int startPos=executor.getBlock()*index;
            int endPos=executor.getBlock()*(index+1)-1;
            connection.setRequestProperty("Range","bytes="+startPos+"-"+endPos);
            RandomAccessFile file=new RandomAccessFile(executor.getSaveFile(),"rwd");
            file.seek(startPos);
            byte[] buffer=new byte[1024];
            int bytes=-1;
            InputStream input=connection.getInputStream();
            while((bytes=input.read(buffer))!=-1)
            {
                file.write(buffer,0,bytes);
                executor.addCurLength(bytes);
            }
        } catch (ProtocolException e) {
            executor.handleThreadException(e,connection);
        }
        catch (IOException e) {

            executor.handleThreadException(e,connection);
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            doneSignal.countDown();
        }
    }
}
