# Husky-Android-Network-Wrapper
Provide basic http-methods(get,post,header),multi-thread uploading and downloading in callback mechanism.

#How to Run Demo
1.Run test server: python /TestServer/main.py<br>
2.If server's internal network IP has changed in your computer,modify BaseUrl string in /example/constant/Web.java and rebuild<br>
3.Run test Android class: /example/activity/MainActivity.java <br>

#Usage
1.Firstly create the RequestQueue which sends http request in FIFO order.<br>
&nbsp;&nbsp;&nbsp;The RequestQueue is in synchronized block when it invokes push() and pop() method to ensure thread safety.

    RequestQueue requestQueue=new RequestQueue();
2.GET method: override onSuccess() and onFailure() methods in Interface  BaseCallback.<br>
&nbsp;&nbsp;&nbsp;Remember to add request to the requestQueue.

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
        
![](https://github.com/didihe1988/Husky-Android-Network-Wrapper/raw/master/screenshot/test_get.png)
3.POST method: override onSuccess() and onFailure() methods in Interface  BaseCallback.

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


![](https://github.com/didihe1988/Husky-Android-Network-Wrapper/raw/master/screenshot/test_post.png)
4.HEAD method: override onSuccess() and onFailure() methods in Interface  HeadCallback.

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

![](https://github.com/didihe1988/Husky-Android-Network-Wrapper/raw/master/screenshot/test_head.png)

5.File download: override onProgress(),onSuccess() and onFailure() methods in Interface FileDownloadCallBack.<br>
&nbsp;&nbsp;&nbsp;**The onProgress() method is for acquring current doanload length in the process of downloading.This method can be used to draw a progress bar.**
    
    String url="http://www.baidu.com/img/bd_logo1.png";
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
    
![](https://github.com/didihe1988/Husky-Android-Network-Wrapper/raw/master/screenshot/test_download.png)

6.File upload: override onProgress(),onSuccess() and onFailure() methods in Interface FileUploadCallback.<br>
&nbsp;&nbsp;&nbsp;**Files upload in the form of  map< String,File>.The key string (like "test","baidu") is used for extracting specific file from post-form in server. So it isn't merely confined to file name.** <br>
&nbsp;&nbsp;&nbsp;**The onProgress() method is for acquring current file index and upoad length in the process of uploading.This method can be used to draw an upload progress bar.**

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

![](https://github.com/didihe1988/Husky-Android-Network-Wrapper/raw/master/screenshot/test_upload.png)



  



