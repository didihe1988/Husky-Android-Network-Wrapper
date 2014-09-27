package com.didihe1988.husky.http.executor;

import android.graphics.Bitmap;

import com.didihe1988.husky.constant.ExecuteType;
import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.param.FileParams;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            addFormField(request.getParams().getParamMap(),out);
            addFilePart(((FileParams)request.getParams()).getFileMap(),out);

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

    /*
    *   --AaB03x
    *   content-disposition: form-data; name="pics"; filename="file1.txt"
    *   Content-Type: text/plain
    *   (Don't forget to add a crlf to make this line empty.I spent whole afternoon to fix this bug.)
    *   ... contents of file1.txt ...
    *   --AaB03x--
    */
    private void addFilePart(Map<String,File> fileMap,DataOutputStream out) throws IOException {

        //DataOutputStream out = new DataOutputStream(outputStream);
        for(Map.Entry entry:fileMap.entrySet())
        {
            File file= (File) entry.getValue();
            out.writeBytes(this.twoHyphens+this.boundry+this.crlf);
            out.writeBytes("Content-Disposition: form-data; name=\"" +entry.getKey()+ "\";filename=\"" +file.getName() + "\"" + this.crlf);
            out.writeBytes("Content-Type:"+ URLConnection.guessContentTypeFromName(file.getName())+this.crlf+this.crlf);
            DataInputStream in=new DataInputStream(new FileInputStream(file));
            int bytes=0;
            byte[] buffer=new byte[1024];
            while ((bytes=in.read(buffer))!=-1)
            {
                out.write(buffer,0,bytes);
            }
            out.writeBytes(this.crlf);
        }
        out.writeBytes(this.twoHyphens+this.boundry+this.twoHyphens+this.crlf);
        out.flush();
        out.close();
    }

    private void addFormField(Map<String,String> paramsMap,DataOutputStream out) throws IOException {
        for(Map.Entry entry:paramsMap.entrySet())
        {
            out.writeBytes(this.twoHyphens+this.boundry+this.crlf);
            out.writeBytes("Content-Disposition: form-data; name=\"" +entry.getKey()+ "\""+this.crlf);
            out.writeBytes(this.crlf);
            out.writeBytes(entry.getValue()+this.crlf);
        }
    }


}
