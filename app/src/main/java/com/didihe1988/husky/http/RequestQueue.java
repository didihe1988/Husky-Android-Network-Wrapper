package com.didihe1988.husky.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didihe1988.husky.constant.MessageType;
import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.didihe1988.husky.constant.RequestMethod.*;
import static com.didihe1988.husky.constant.RequestMethod.GET;

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
                Object obj=execute(request);
                sendMessage(request.getHandler(), obj);
            }

        }

        /*
            synchronized   only one request at a time
         */
        private synchronized Object execute(HttpRequest request)
        {
           switch (request.getMethod())
           {
               case POST:
                   return executePost(request);
               case GET:
               default:
                   return executeGet(request);

           }
        }

        private Object executeGet(HttpRequest request)
        {
            try {
                URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
                System.out.println(url.toString());
                HttpConfig config=request.getConfig();
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod(GET.name());
                connection.setUseCaches(config.isUseCaches());
                connection.setReadTimeout(config.getReadTimeOut());
                connection.setConnectTimeout(config.getConnectTimeOut());
                if(request.getParams()!=null)
                {
                    for(Map.Entry<String,String> param:request.getParams().entrySet())
                    {
                        connection.setRequestProperty(param.getKey(),param.getValue());
                    }
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

        private Object executePost(HttpRequest request)
        {
            try {
                URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
                System.out.println(url.toString());
                HttpConfig config=request.getConfig();
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod(POST.name());
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(config.isUseCaches());
                connection.setReadTimeout(config.getReadTimeOut());
                connection.setConnectTimeout(config.getConnectTimeOut());
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                /*
                BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response=new StringBuffer();
                while( (inputLine=in.readLine())!=null)
                {
                    response.append(inputLine);
                }
                in.close();*/
                /*
                OutputStream out=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.write(getParamString(request.getParams()));
                writer.flush();
                writer.close();*/

                connection.connect();
                if(request.getParams()!=null)
                {
                    OutputStream out=connection.getOutputStream();
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                    writer.write(getParamString(request.getParams()));
                    writer.flush();
                    writer.close();
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                connection.disconnect();
                return response.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
                return e;
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return e;
            } catch (IOException e) {
                e.printStackTrace();
                return e;
            }
        }

        private String getParamString(Map<String,String> params) throws UnsupportedEncodingException {
            StringBuilder builder=new StringBuilder();
            boolean isFirst=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(isFirst)
                {
                    isFirst=false;
                }
                else
                {
                    builder.append("&");
                }
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            return builder.toString();
        }

        //{ "Name": "Foo", "Id": 1234, "Rank": 7 }




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
