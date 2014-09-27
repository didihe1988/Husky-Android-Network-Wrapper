package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.constant.ExecuteType;
import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/9/26.
 */
public class GetExecutor extends Executor{
    protected GetExecutor() {
        super(ExecuteType.GET_NORMAL);
    }

    @Override
    public Object execute(HttpRequest request) {
        HttpURLConnection connection=null;
        try {
            URL url=new URL(HttpUtils.addProtocol(request.getUrl()));
            System.out.println(url.toString());
            HttpConfig config=request.getConfig();
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(GET.name());
            connection.setUseCaches(config.isUseCaches());
            connection.setReadTimeout(config.getReadTimeOut());
            connection.setConnectTimeout(config.getConnectTimeOut());
            /*
            if(request.getParams()!=null)
            {
                for(Map.Entry<String,String> param:request.getParams().entrySet())
                {
                    connection.setRequestProperty(param.getKey(),param.getValue());
                }
            }*/
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


}
