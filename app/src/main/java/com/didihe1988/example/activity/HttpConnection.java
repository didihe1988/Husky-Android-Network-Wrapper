package com.didihe1988.example.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lml on 2014/11/5.
 */
public class HttpConnection implements Runnable{
    private String urlString;
    public HttpConnection(String urlString)
    {
        super();
        this.urlString=urlString;
    }

    @Override
    public void run() {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(urlString);
            System.out.println(url.toString());
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setReadTimeout(12000);
            connection.setConnectTimeout(12000);
            String response=getResponse(connection.getInputStream());
            System.out.println("response: "+response);
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

    private String getResponse(InputStream inputStream) throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response=new StringBuffer();
        while( (inputLine=in.readLine())!=null)
        {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
