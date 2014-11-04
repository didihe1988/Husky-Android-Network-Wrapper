package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.component.HeadMessageSender;
import com.didihe1988.husky.http.executor.head.Content;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.didihe1988.husky.constant.RequestMethod.HEAD;

/**
 * Created by lml on 2014/11/4.
 */
public class HeadExecutor extends Executor{
    private HeadMessageSender sender;

    protected HeadExecutor(HttpRequest request)
    {
        super(request);
        this.sender=new HeadMessageSender();
    }
    @Override
    public void execute() throws IOException {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(request.getUrl());
            System.out.println(url.toString());
            HttpConfig config=request.getConfig();
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HEAD.name());
            connection.setUseCaches(config.isUseCaches());
            connection.setReadTimeout(config.getReadTimeOut());
            connection.setConnectTimeout(config.getConnectTimeOut());
            if(connection.getResponseCode()==200)
            {
                sender.sendMessage(request.getHandler(),getContent(connection));
            }
        } catch (ProtocolException e) {
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        }
        catch (IOException e) {
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }
    }

    private Content getContent(HttpURLConnection connection)
    {
        return new Content(connection.getContentLength(),connection.getContentEncoding(),connection.getContentType());
    }
}
