package com.didihe1988.husky.http.executor;

import android.graphics.Bitmap;

import com.didihe1988.husky.constant.ExecuteType;
import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/9/26.
 */
public class UploadFileExecutor extends Executor{
    private static final String crlf="\r\n";
    private static final String twoHyphens="--";
    private static final String boundry="AaB03x";

    protected UploadFileExecutor() {
        super(ExecuteType.POST_FILE);
    }

    /*
     *   rfc1867
     */
    @Override
    public Object execute(HttpRequest request) {
        HttpURLConnection connection=null;

        try {
            URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
            System.out.println(url.toString());
            HttpConfig config=request.getConfig();
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(config.isUseCaches());
            connection.setReadTimeout(config.getReadTimeOut());
            connection.setConnectTimeout(config.getConnectTimeOut());

            connection.setRequestMethod(RequestMethod.POST.name());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundry);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            /*
             *   --AaB03x
             *   content-disposition: form-data; name="pics"; filename="file1.txt"
             *   Content-Type: text/plain
             *   (Don't forget to add a crlf to make this line empty.I spent whole afternoon to fix this bug.)
             *   ... contents of file1.txt ...
             *   --AaB03x--
             */
            out.writeBytes(this.twoHyphens+this.boundry+this.crlf);
            //Content-Disposition: form-data; name="image";filename="test.jpg"
            out.writeBytes("Content-Disposition: form-data; name=\"" +"image"+ "\";filename=\"" +"test.jpg" + "\"" + this.crlf);
            out.writeBytes("Content-Type:"+ URLConnection.guessContentTypeFromName("test.jpg")+this.crlf+this.crlf);
            String path="/mnt/sdcard/test.jpg";
            DataInputStream in=new DataInputStream(new FileInputStream(path));
            int bytes=0;
            byte[] buffer=new byte[1024];
            while ((bytes=in.read(buffer))!=-1)
            {
                out.write(buffer,0,bytes);
            }
            out.writeBytes(this.crlf);
            out.writeBytes(this.twoHyphens+this.boundry+this.twoHyphens+this.crlf);
            out.flush();
            out.close();

            return getResponse(connection.getInputStream());
        } catch (ProtocolException e) {
            e.printStackTrace();
            return e;
        }
        catch (IOException e) {
            e.printStackTrace();
            return e;
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }
    }
}
