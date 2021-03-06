package com.didihe1988.husky.http;

import com.didihe1988.husky.exception.MethodException;
import com.didihe1988.husky.http.executor.Executor;

import java.io.IOException;
import java.util.LinkedList;

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
            System.out.println("queue pop");
            HttpRequest request=queue.pop();
            execute(request);
                //sendMessage(request.getHandler(), obj);
            }

        }

        /*
            synchronized   only one request at a time
         */
        private synchronized void execute(HttpRequest request)
        {

            Executor executor = null;
            try {
                executor = Executor.create(request);
                System.out.println("executor "+executor.toString());
                executor.execute();
            } catch (MethodException e) {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                /*
                Exception of connect.getResponseCode()
                */
                e.printStackTrace();
            }


        }
        /*
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
        }*/
    }

}
