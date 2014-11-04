package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.component.MessageSender;
import com.didihe1988.husky.utils.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.didihe1988.husky.constant.RequestMethod.GET;

/**
 * Created by lml on 2014/9/26.
 */
public class GetExecutor extends Executor{
    /*protected GetExecutor() {
        super(ExecuteType.GET_NORMAL);
    }*/

    private MessageSender sender;
    /*
    构造函数对父类Executor可见  在Executor.create()中调用
     */
    protected GetExecutor(HttpRequest request)
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
            String response=getResponse(connection.getInputStream());
            sender.sendMessage(request.getHandler(),response);
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


}
