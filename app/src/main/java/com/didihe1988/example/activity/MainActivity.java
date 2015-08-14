package com.didihe1988.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.didihe1988.example.constant.Web;
import com.didihe1988.husky.http.BaseCallback;
import com.didihe1988.husky.http.FileDownloadCallBack;
import com.didihe1988.husky.http.HeadCallback;
import com.didihe1988.husky.http.HttpRequest;
import com.didihe1988.husky.http.RequestQueue;
import com.didihe1988.husky.http.executor.head.Content;
import com.didihe1988.husky.http.param.FileDownloadParam;
import com.didihe1988.husky.http.param.PostParams;

import java.io.File;


/**
 * Created by lml on 2014/9/22.
 */

public class MainActivity extends Activity{

    private static final String TAG="MainActicity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String url="192.168.0.119:5000/get_normal";


        //Map<String,String> params=new HashMap<String, String>();
        RequestQueue requestQueue=new RequestQueue();
        /*
        HttpRequest request=new HttpRequest(url,null,new HttpCallback() {
            @Override
            public void onSuccess(Object object) {
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
            public void onSuccess(Object object) {
                System.out.println("Post onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println("Post onFailure: "+object);
            }
        });
        requestQueue.add(request1);*/
       /* File file=new File("/mnt/sdcard/test.jpg");
        File file1=new File("/mnt/sdcard/beauty.jpg");
        FileParams fileParams=new FileParams();
        fileParams.putParamEntry("language","java");
        fileParams.putParamEntry("school","uestc");
        fileParams.putFileEntry("image", file);
        fileParams.putFileEntry("beauty",file1);
        HttpRequest request2=new HttpRequest(RequestMethod.POST,"192.168.0.102:5000/upload_file",fileParams,new FileUploadCallback(){
            @Override
            public void onSuccess(Object object) {
                System.out.println("UploadFile onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println("UploadFile onFailure: "+object);
            }


            @Override
            public void onProgressUpdate(int fileCount, int curFile, long count, long cur) {
                System.out.println(fileCount+" "+curFile+" "+count+" "+cur);
            }
        });
        requestQueue.add(request2);*/
        /*File file=new File("/storage/sdcard0/lml/test.jpg");
        FileDownloadParam param=new FileDownloadParam(file,10);
        HttpRequest request2=new HttpRequest(RequestMethod.GET,"http://n0.itc.cn/img7/adapt/wb/smccloud/fetch/2014/10/27/92218658612621356_302_1000.jpg",param,new FileUploadCallback(){
            @Override
            public void onSuccess(Object object) {
                System.out.println("DownloadFile onSuccess: "+object);
            }

            @Override
            public void onFailure(Object object) {
                System.out.println("DownloadFile onFailure: "+object);
            }


            @Override
            public void onProgressUpdate(int fileCount, int curFile, long count, long cur) {
                System.out.println(fileCount+" "+curFile+" "+count+" "+cur);
            }
        });
        requestQueue.add(request2);*/

        //stestException(requestQueue);

        //testGET(requestQueue);

        //testPOST(requestQueue);

        //testFileDownload(requestQueue);

        //testHead(requestQueue);
        new Thread(new HttpConnection("http://192.168.0.114:5000/get_normal")).start();
        new Thread(new HttpConnection("http://192.168.0.114:5000/get_normal")).start();
        new Thread(new HttpConnection("http://192.168.0.114:5000/get_normal")).start();
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

    private void testFileDownload(RequestQueue requestQueue)
    {
        String url="http://c2.hoopchina.com.cn/uploads/star/event/images/141103/bmiddle-1a2e7216ebf392d8265b658e3fbedd7f8df624e9.jpg";
        //final File file=new File("/storage/sdcard0/tmp_fot_test/test.jpg");
        final File file=new File("/mnt/sdcard/test.jpg");
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

    private void testHead(RequestQueue requestQueue)
    {
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
