package com.didihe1988.husky.http;

import com.didihe1988.husky.constant.HttpConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.AbstractQueue;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lml on 2014/9/23.
 */
public class RequestQueue {
    /*
    选一个合适的数据结构
    private Queue<HttpRequest> queue=new AbstractQueue<HttpRequest>() {

    };*/

    public void add()
    {

    }

    public void cancelAll()
    {

    }

    static
    {
        new Thread(new HttpRunnable()).start();
    }


    private static class HttpRunnable implements  Runnable {

        @Override
        public void run() {

           connect();

        }

        private void connect()
        {
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
                // return  response.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
                // return e;
            }
            catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
                //  return e;
            }
        }
    }

}
