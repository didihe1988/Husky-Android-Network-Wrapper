package com.didihe1988.example.activity;

import android.app.Activity;
import android.os.Bundle;

import com.didihe1988.husky.http.HttpConfig;
import com.didihe1988.husky.http.HttpGetTask;

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
        URL url= null;
        try {
            url = new URL("http://192.168.0.104:8080/picker/json/user/1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,String> params=new HashMap<String, String>();
        HttpGetTask task=new HttpGetTask(url,params,new HttpConfig());
        task.execute();
    }
}
