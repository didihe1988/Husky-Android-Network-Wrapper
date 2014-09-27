package com.didihe1988.example.activity;

import android.app.Activity;
import android.os.Bundle;

import com.didihe1988.husky.constant.RequestMethod;
import com.didihe1988.husky.http.HttpCallback;
import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.RequestQueue;
import com.didihe1988.husky.http.param.FileParams;
import com.didihe1988.husky.http.param.Params;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lml on 2014/9/22.
 */
public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url="192.168.0.102:5000/get_normal";

        Map<String,String> params=new HashMap<String, String>();
        RequestQueue requestQueue=new RequestQueue();
        /*
        HttpRequest request=new HttpRequest(url,null,new HttpCallback() {
            @Override
            public void onSuceess(Object object) {
                System.out.println("onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println(object);
            }
        });
       requestQueue.add(request);*/
        /*
        params.put("age","2");
        params.put("name","Jack");
        params.put("weight","98");
        HttpRequest request1=new HttpRequest(RequestMethod.POST,"192.168.0.102:5000/post_normal",new Params(params),new HttpCallback() {
            @Override
            public void onSuceess(Object object) {
                System.out.println("Post onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println("Post onFailure: "+object);
            }
        });
        requestQueue.add(request1);*/
        File file=new File("/mnt/sdcard/test.jpg");
        File file1=new File("/mnt/sdcard/beauty.jpg");
        FileParams fileParams=new FileParams();
        fileParams.putParamEntry("language","java");
        fileParams.putFileEntry("image",file);
        fileParams.putFileEntry("beauty",file1);
        HttpRequest request2=new HttpRequest(RequestMethod.POST,"192.168.0.102:5000/upload_file",fileParams,new HttpCallback() {
            @Override
            public void onSuceess(Object object) {
                System.out.println("UploadFile onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println("UploadFile onFailure: "+object);
            }
        });
        requestQueue.add(request2);
    }
}
