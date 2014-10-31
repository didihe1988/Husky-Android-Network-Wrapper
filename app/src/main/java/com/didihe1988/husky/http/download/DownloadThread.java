package com.didihe1988.husky.http.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/10/31.
 */
public class DownloadThread extends Thread{
    private String url;

    private File saveFile;

    private int block;

    private int index;

    public DownloadThread(String url, File saveFile, int block, int index) {
        this.url = url;
        this.saveFile = saveFile;
        this.block = block;
        this.index = index;
    }

    @Override
    public void run() {
        super.run();
        HttpURLConnection connection=null;
        try {
            URL url=new URL(this.url);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(GET.name());
            connection.setUseCaches(false);
            connection.setReadTimeout(12000);
            connection.setConnectTimeout(12000);
            connection.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg");
            //("Range", "bytes=" + start + "-" + end + "")
            int startPos=block*index;
            int endPos=block*(index+1)-1;
            connection.setRequestProperty("Range","bytes="+startPos+"-"+endPos);
            RandomAccessFile file=new RandomAccessFile(this.saveFile,"rwd");
            file.seek(startPos);
            byte[] buffer=new byte[1024];
            int bytes=-1;
            InputStream input=connection.getInputStream();
            while((bytes=input.read(buffer))!=-1)
            {
                file.write(buffer,0,bytes);
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
}
