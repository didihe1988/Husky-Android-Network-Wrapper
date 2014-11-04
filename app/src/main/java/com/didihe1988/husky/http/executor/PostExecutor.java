package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.component.MessageSender;
import com.didihe1988.husky.http.param.PostParams;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import static com.didihe1988.husky.constant.RequestMethod.POST;

/**
 * Created by lml on 2014/9/26.
 */
public class PostExecutor extends Executor{

   private MessageSender sender;

    protected PostExecutor(HttpRequest request)
    {
        super(request);
        this.sender=new MessageSender();
    }

    @Override
    public void execute() throws IOException {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
            System.out.println(url.toString());
            HttpConfig config=request.getConfig();
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(POST.name());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(config.isUseCaches());
            connection.setReadTimeout(config.getReadTimeOut());
            connection.setConnectTimeout(config.getConnectTimeOut());
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.connect();
            if(request.getParams()!=null)
            {
                OutputStream out=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.write(getParamString((PostParams)request.getParams()));
                writer.flush();
                writer.close();
            }

            String response=getResponse(connection.getInputStream());
            sender.sendMessage(request.getHandler(),response);
        } catch (ProtocolException e) {
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        } catch (UnsupportedEncodingException e)
        {
            int errCode=connection!=null?connection.getResponseCode():-1;
            sender.sendFailureMessage(request.getHandler(),e,errCode);
        } catch (IOException e) {
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

    private String getParamString(PostParams postParams) throws UnsupportedEncodingException {
        StringBuilder builder=new StringBuilder();
        boolean isFirst=true;
        for(Map.Entry<String,String> entry: postParams.getParamMap().entrySet())
        {
            if(isFirst)
            {
                isFirst=false;
            }
            else
            {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return builder.toString();
    }


}
