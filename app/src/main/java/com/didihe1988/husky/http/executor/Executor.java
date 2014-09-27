package com.didihe1988.husky.http.executor;

import com.didihe1988.husky.constant.ExecuteType;
import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.exception.MethodException;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.param.FileParams;
import com.didihe1988.husky.http.param.Params;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lml on 2014/9/26.
 */
public abstract class Executor {
    private ExecuteType type;

    public abstract Object execute(HttpRequest request);

    protected Executor(ExecuteType type)
    {
        this.type=type;
    }

    public static Executor create(RequestMethod method,Params params) throws MethodException {
        if(method == RequestMethod.GET)
        {
            if(params instanceof FileParams)
            {
                throw new MethodException("Upload file should use POST method");
            }
            return new GetExecutor();
        }
        else
        {
            if(params instanceof FileParams)
            {
                return new UploadFileExecutor();
            }
            return new PostExecutor();
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
