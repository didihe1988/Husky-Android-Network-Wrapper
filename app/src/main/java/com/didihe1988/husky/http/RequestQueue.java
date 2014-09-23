package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.HttpConstant;
import com.didihe1988.husky.constant.MessageType;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lml on 2014/9/23.
 */
public class RequestQueue {

    private static LinkedList<HttpRequest> queue=new LinkedList<HttpRequest>();

    static
    {
        new Thread(new HttpRunnable()).start();
    }

    public void add(HttpRequest request)
    {
        synchronized (queue)
        {
            queue.push(request);
            /*
            唤醒之前为空时queue.wait()的线程
             */
            queue.notify();
        }

    }

    public void cancelAll()
    {
        synchronized (queue) {
            queue.clear();
        }
    }

    private static class HttpRunnable implements  Runnable {

        @Override
        public void run() {
            synchronized (queue)
            {
                while (queue.isEmpty())
                {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                HttpRequest request=queue.pop();
                Object obj=connect(request);
                sendMessage(request.getHandler(),obj);
            }

        }

        /*
            synchronized   only one request at a time
         */
        private synchronized Object connect(HttpRequest request)
        {
            try {
                URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
                System.out.println(url.toString());
                HttpConfig config=request.getConfig();
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod(HttpConstant.METHOD.GET.name());
                connection.setUseCaches(config.isUseCaches());
                connection.setReadTimeout(config.getReadTimeOut());
                connection.setConnectTimeout(config.getConnectTimeOut());

                for(Map.Entry<String,String> param:request.getParams().entrySet())
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

        private void sendMessage(Handler handler,Object obj)
        {
            Message msg=Message.obtain();
            Bundle bundle=new Bundle();
            if(obj instanceof  String)
            {
                msg.what=MessageType.REQUEST_SUCCESS;
                bundle.putString("response",(String)obj);
            }
            else if(obj  instanceof ProtocolException)
            {
                msg.what=MessageType.REQUEST_FAILURE;
                bundle.putString("exception",obj.toString());
            }
            else if(obj instanceof  IOException)
            {
                msg.what=MessageType.REQUEST_FAILURE;
                bundle.putString("exception",obj.toString());
            }
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

}
