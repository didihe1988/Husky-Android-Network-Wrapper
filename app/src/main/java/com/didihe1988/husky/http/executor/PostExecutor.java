package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.constant.ExecuteType;
import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.param.Params;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
   protected PostExecutor() {
        super(ExecuteType.POST_NORMAL);
    }

    @Override
    public Object execute(HttpRequest request){
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
                writer.write(getParamString(request.getParams()));
                writer.flush();
                writer.close();
            }

            return getResponse(connection.getInputStream());
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
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }

    }

    private String getParamString(Params params) throws UnsupportedEncodingException {
        StringBuilder builder=new StringBuilder();
        boolean isFirst=true;
        for(Map.Entry<String,String> entry:params.getParamMap().entrySet())
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
