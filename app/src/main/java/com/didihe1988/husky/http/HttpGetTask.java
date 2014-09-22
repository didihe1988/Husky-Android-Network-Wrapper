package com.didihe1988.husky.http;

import android.os.AsyncTask;

import com.didihe1988.husky.constant.HttpConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by lml on 2014/9/22.
 */
public class HttpGetTask extends AsyncTask<Void,Void,Object>{
    private URL url;
    private Map<String,String> params;
    private HttpConfig config;

    public HttpGetTask(URL url,Map<String,String> params,HttpConfig config) {
        this.url=url;
        this.params=params;
        this.config=config;
    }

    @Override
    protected Object doInBackground(Void... voids) {

        try {
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HttpConstant.METHOD.GET.name());
            connection.setUseCaches(config.isUseCaches());
            connection.setReadTimeout(config.getReadTimeOut());
            connection.setConnectTimeout(config.getConnectTimeOut());
            for(Map.Entry<String,String> param:params.entrySet())
            {
                connection.setRequestProperty(param.getKey(),param.getValue());
            }
            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response=new StringBuffer();
            while( (inputLine=in.readLine())!=null)
            {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return  response.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
            return e;
        }
        catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            return e;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(o instanceof ProtocolException)
        {

        }
        else if(o instanceof IOException)
        {

        }
        else if(o instanceof String)
        {
            System.out.println(o);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
