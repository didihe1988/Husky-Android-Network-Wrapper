package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.exception.MethodException;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.param.FileDownloadParam;
import com.didihe1988.husky.http.param.FileUploadParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lml on 2014/9/26.
 */
public abstract class Executor {
    //private ExecuteType type;

    protected HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public abstract void execute() throws IOException;

    /*protected Executor(ExecuteType type)
    {
        this.type=type;
    }*/


    protected Executor(HttpRequest request)
    {
        this.request=request;
    }

    public static Executor create(HttpRequest request) throws MethodException {
        if(request.getMethod() == RequestMethod.GET)
        {

            if(request.getParams() instanceof FileDownloadParam)
            {
                return new DownloadExecutor(request);
            }
            return new GetExecutor(request);
        }
        else if(request.getMethod() == RequestMethod.HEAD)
        {
            return new HeadExecutor(request);
        }
        else
        {
            /*
            设定Method为POST
             */
            if(request.getParams() instanceof FileUploadParams)
            {
                return new UploadExecutor(request);
            }
            return new PostExecutor(request);
        }
    }

    protected String getResponse(InputStream inputStream) throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response=new StringBuffer();
        while( (inputLine=in.readLine())!=null)
        {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
