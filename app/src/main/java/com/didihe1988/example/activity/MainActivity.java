package com.didihe1988.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.didihe1988.example.constant.Web;
import com.didihe1988.husky.http.BaseCallback;
import com.didihe1988.husky.http.FileDownloadCallBack;
import com.didihe1988.husky.http.FileUploadCallback;
import com.didihe1988.husky.http.HeadCallback;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.RequestQueue;
import com.didihe1988.husky.http.executor.head.Content;
import com.didihe1988.husky.http.param.FileDownloadParam;
import com.didihe1988.husky.http.param.FileUploadParams;
import com.didihe1988.husky.http.param.PostParams;

import java.io.File;
import java.util.HashMap;


/**
 * Created by lml on 2014/9/22.
 */

public class MainActivity extends Activity{

    private static final String TAG="MainActicity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue requestQueue=new RequestQueue();


        testException(requestQueue);

        testGET(requestQueue);

        testPOST(requestQueue);

        testFileDownload(requestQueue);

        testFileUpload(requestQueue);

        testHead(requestQueue);

    }

    private void testException(final RequestQueue requestQueue){
        System.out.println("in testException");
        String url= Web.BaseUrl+"/fake";
        HttpRequest request=new HttpRequest(url,new BaseCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,"response: "+response);
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG,"errCode: "+errCode);
            }
        });
        requestQueue.add(request);
    }

    private void testGET(final RequestQueue requestQueue){
        System.out.println("in testGET");
        String url= Web.BaseUrl+"/get_normal";
        HttpRequest request=new HttpRequest(url,new BaseCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,"response: "+response);
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG,"errCode: "+errCode);
            }
        });
        requestQueue.add(request);
    }

    private void testPOST(RequestQueue requestQueue){
        System.out.println("in testPOST");
        String url= Web.BaseUrl+"/post_normal";
        PostParams params=new PostParams();
        params.put("age", "2");
        params.put("name","Jack");
        params.put("weight", "98");
        HttpRequest request=new HttpRequest(url,params,new BaseCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,"response: "+response);
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG,"errCode: "+errCode);
            }
        });
        requestQueue.add(request);
    }

    private void testFileDownload(RequestQueue requestQueue){
        String url="http://www.baidu.com/img/bd_logo1.png";
        //final File file=new File("/storage/sdcard0/tmp_fot_test/test.jpg");
        final File file=new File(Environment.getExternalStorageDirectory().getPath()+"/baidu.jpg");
        int threadNum=10;
        FileDownloadParam param=new FileDownloadParam(file,threadNum);
        HttpRequest request=new HttpRequest(url,param,new FileDownloadCallBack()
        {
            @Override
            public void onProgress(int fileLength, int curLength) {
                Log.i(TAG,"progress: fileLength: "+fileLength+" curLength: "+curLength);
            }

            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,((File)response).getName()+" download success");
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG,"errCode: "+errCode);
            }
        });
        requestQueue.add(request);
    }

    private void testFileUpload(RequestQueue requestQueue){
        System.out.println("in testUpload");
        String url= Web.BaseUrl+"/upload_file";
        HashMap<String, File> map=new HashMap<String,File>();
        map.put("test",new File(Environment.getExternalStorageDirectory().getPath()+"/test.jpg"));
        map.put("baidu",new File(Environment.getExternalStorageDirectory().getPath()+"/baidu.jpg"));
        FileUploadParams params=new FileUploadParams(map);
        HttpRequest request=new HttpRequest(url,params,new FileUploadCallback() {
            @Override
            public void onProgress(int fileCount, int curFile, long count, long cur) {
                Log.i(TAG,"progress: totalFile:"+fileCount+" curFile:"+curFile+" fileLength:"+count+" curLength:"+cur);
            }

            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,"upload success: "+response);
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG, "errCode: " + errCode);
            }
        });
        requestQueue.add(request);
    }

    private void testHead(RequestQueue requestQueue){

        String url="http://c2.hoopchina.com.cn/uploads/star/event/images/141103/bmiddle-1a2e7216ebf392d8265b658e3fbedd7f8df624e9.jpg";

        HttpRequest request=new HttpRequest(url,new HeadCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.i(TAG,((Content)response).toString());
            }

            @Override
            public void onFailure(Exception exception, int errCode) {
                Log.i(TAG,"exception: "+exception.toString());
                Log.i(TAG,"errCode: "+errCode);
            }
        });
        requestQueue.add(request);
    }


}
