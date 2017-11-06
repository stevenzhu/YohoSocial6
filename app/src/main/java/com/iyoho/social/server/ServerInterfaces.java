package com.iyoho.social.server;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoho.social.base.IBaseApplication;
import com.iyoho.social.utils.FilePathUtil;
import com.iyoho.social.utils.LogUtil;
import com.iyoho.social.utils.NetWorkUtil;
import com.iyoho.social.utils.SDCardUtils;
import com.iyoho.social.utils.SPUtils;
import com.iyoho.social.utils.SystemUtil;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.OkHttpUtilInterface;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.DownloadStatus;
import com.okhttplib.annotation.RequestType;
import com.okhttplib.bean.DownloadFileInfo;
import com.okhttplib.callback.CallbackOk;
import com.okhttplib.callback.ProgressCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.path;
import static android.R.attr.x;
import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by ab053167 on 2017/9/12.
 */

public class ServerInterfaces {
    public ServerInterfaces() {
        init();
    }

    public enum ENV {
        DEVELOP, UAT, PRD
    }

    public static ENV ENVIRONMENT = ENV.DEVELOP;
    private String serverInterfaceURL = "";
    public static final String DEVELOP_HOST = "";
    public static final String UAT_HOST = "";
    public static final String PRD_HOST = "";
    private String fileUploadUrl="http://open.1000erp.com/os/file/uploadFile.do";
    private String version;
    private String deviceID;
    private String platformId;
    private String uuid;
    private String deviceType="Android";
    private static volatile ServerInterfaces instance = null;
    private static Map<String,DownloadFileInfo> fileInfoMap=new HashMap<String,DownloadFileInfo>();

    public static ServerInterfaces getInstance() {

        if (instance == null) {
            synchronized (ServerInterfaces.class) {
                if (instance == null) {
                    instance = new ServerInterfaces();
                }
            }
        }
        return instance;
    }

    public void init() {
        initENV();
        initParams();
    }

    private void initENV() {
        switch (ENVIRONMENT) {
            case DEVELOP: {
                serverInterfaceURL = DEVELOP_HOST;
            }
            break;
            case UAT: {
                serverInterfaceURL = UAT_HOST;
            }
            break;
            case PRD: {
                serverInterfaceURL = PRD_HOST;
            }
            break;
            default: {
                serverInterfaceURL = PRD_HOST;

            }
        }
    }

    public void initParams() {
        version = SystemUtil.getVersionName(SystemUtil.getInstance().getApplication().getApplicationContext());
        if (TextUtils.isEmpty(version)) {
            version = "1.0.0";
        }
        platformId = SystemUtil.getInstance().getApplication().getPackageName();
        deviceID = SystemUtil.getInstance().getDeviceID();
        uuid = SystemUtil.getInstance().getUuid();

    }

    /**
     * 异步POST请求：回调方法可以直接操作UI
     */
    public void postAsync(Context context, String interfaceUrl, HashMap<String, String> params, final ServerCallback callback) {
        // addPublicParams(params);
        OkHttpUtilInterface build=null;
        if(!NetWorkUtil.isNetworkAvailable(context)){
            build=OkHttpUtil.Builder().setCacheType(CacheType.FORCE_CACHE).build(interfaceUrl);
        }else{
            build=OkHttpUtil.getDefault(interfaceUrl);
        }
        HttpInfo info =null;
        if(getHeads()!=null&&getHeads().size()>0){
            info= HttpInfo.Builder().addHeads(getHeads()).setUrl(interfaceUrl).addParams(params).build();
        }else{
            info= HttpInfo.Builder().setUrl(interfaceUrl).addParams(params).build();
        }
        build.doPostAsync(info, new CallbackOk() {
            @Override
            public void onResponse(HttpInfo info) throws IOException {
                if(callback!=null){
                    if (info != null && info.isSuccessful()) {
                        callback.onSuccess(info.getRetDetail());
                    } else {
                        callback.onFailure(info.getRetCode(), info.getRetDetail());
                    }
                }
            }
        });
    }

    /**
     * 同步POST请求：由于不能在UI线程中进行网络请求操作，所以采用子线程方式
     */
    public void postSync(final Context context, final String interfaceUrl,final HashMap<String, String> params, final ServerCallback callback) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
               // addPublicParams(params);
                OkHttpUtilInterface build=null;
                if(!NetWorkUtil.isNetworkAvailable(context)){
                    build=OkHttpUtil.Builder().setCacheType(CacheType.FORCE_CACHE).build(interfaceUrl);
                }else{
                    build=OkHttpUtil.getDefault(interfaceUrl);
                }
                HttpInfo info =null;
               if(getHeads()!=null&&getHeads().size()>0){
                info= HttpInfo.Builder().addHeads(getHeads()).setUrl(interfaceUrl).addParams(params).build();
                }else{
                info= HttpInfo.Builder().setUrl(interfaceUrl).addParams(params).build();
                 }
                build.doPostSync(info);
        if(callback!=null){
            if (info != null && info.isSuccessful() ) {
                callback.onSuccess(info.getRetDetail());
            } else {
                callback.onFailure(info.getRetCode(), info.getRetDetail());
            }
        }

            }
    /**
     * 异步GET请求：回调方法可以直接操作UI
     */
    public void getAsync(final Context context, String interfaceUrl, final ServerCallback callback) {
        // addPublicParams(params);
        OkHttpUtilInterface build=null;
        if(!NetWorkUtil.isNetworkAvailable(context)){
            build=OkHttpUtil.Builder().setCacheType(CacheType.FORCE_CACHE).build(interfaceUrl);
        }else{
            build=OkHttpUtil.getDefault(interfaceUrl);
        }
        HttpInfo info=null;
        if(getHeads()!=null&&getHeads().size()>0){
            info= HttpInfo.Builder().addHeads(getHeads()).setUrl(interfaceUrl).build();
        }else{
            info= HttpInfo.Builder().setUrl(interfaceUrl).build();
        }
        build.doGetAsync(info, new CallbackOk() {
            @Override
            public void onResponse(HttpInfo info) throws IOException {
                if(callback!=null){
                    if (info != null && info.isSuccessful()) {
                        callback.onSuccess(info.getRetDetail());
                    } else {
                        callback.onFailure(info.getRetCode(), info.getRetDetail());
                    }
                }

            }
        });
    }

    /**
     * 同步GET请求：由于不能在UI线程中进行网络请求操作，所以采用子线程方式
     */
    public void getSync(final Context context, final String interfaceUrl, final ServerCallback callback) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
                // addPublicParams(params);
                OkHttpUtilInterface build=null;
                if(!NetWorkUtil.isNetworkAvailable(context)){
                    build=OkHttpUtil.Builder().setCacheType(CacheType.FORCE_CACHE).build(interfaceUrl);
                }else{
                    build=OkHttpUtil.getDefault(interfaceUrl);
                }
                HttpInfo info=null;
                if(getHeads()!=null&&getHeads().size()>0){
                    info= HttpInfo.Builder().addHeads(getHeads()).setUrl(interfaceUrl).build();
                }else{
                    info= HttpInfo.Builder().setUrl(interfaceUrl).build();
                }
                build.doGetSync(info);
        if(callback!=null){
            if (info != null && info.isSuccessful()) {
                callback.onSuccess(info.getRetDetail());
            } else {
                callback.onFailure(info.getRetCode(), info.getRetDetail());
            }
        }

            }

            //------上传文件-----
            public interface UploadFileCallback {
                void uploadProgress(int uploadIndex,String uploadPath,int percent, long bytesWritten, long contentLength, boolean done);
                void uploadCompleted(int uploadIndex,String uploadPath, String result);
                void uploadFailure(int uploadIndex,String uploadPath,String errorMsg);
            }
          //------下载文件-----
    public interface DownFileCallback {
              void downProgress(int downIndex,String downPath,int percent, long bytesWritten, long contentLength, boolean done);
              void onSuccessMain(int downIndex,String filePath, HttpInfo info);
              void onFailMain(int downIndex,String filePath, HttpInfo info);
    }
    /**
     * 异步上传单张图片
     */
    public void uploadFileAsync(final String filePath,final UploadFileCallback callback) {
        HttpInfo info = HttpInfo.Builder()
                .setUrl(fileUploadUrl)
                .addParam("fileType", "avatar")
                .addUploadFile("uploadFile", filePath, new ProgressCallback() {
                    //onProgressMain为UI线程回调，可以直接操作UI
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        if(callback!=null){
                            callback.uploadProgress(0,filePath,percent,bytesWritten,contentLength,done);
                        }

                    }
                    @Override
                    public void onResponseSync(String filePath, HttpInfo info) {
                        super.onResponseSync(filePath,info);
                        if(callback!=null){
                            if (info != null && info.isSuccessful() ) {
                                callback.uploadCompleted(0,filePath,info.getRetDetail());
                            } else {
                                callback.uploadFailure(0,filePath,info.getRetDetail());
                            }
                        }
                    }
                })
                .build();
        doUploadFileAsync(info,filePath);

    }
    public void uploadFileAsync(final int index,final String filePath,final UploadFileCallback callback) {
        HttpInfo info = HttpInfo.Builder()
                .setUrl(fileUploadUrl)
                .addParam("fileType", "avatar")
                .addUploadFile("uploadFile", filePath, new ProgressCallback() {
                    //onProgressMain为UI线程回调，可以直接操作UI
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        if(callback!=null){
                            callback.uploadProgress(index,filePath,percent,bytesWritten,contentLength,done);
                        }
                    }

                    @Override
                    public void onResponseSync(String filePath, HttpInfo info) {
                        super.onResponseSync(filePath,info);
                        if (info != null && info.isSuccessful()) {
                            if(callback!=null){
                                callback.uploadCompleted(index,filePath,info.getRetDetail());
                            }
                        } else {
                            if(callback!=null){
                                callback.uploadFailure(index,filePath,info.getRetDetail());
                            }
                        }
                    }
                })

                .build();
        doUploadFileAsync(info,filePath);

    }
    /**
     * 异步批量上传
     */
    public void uploadBatchFileAsync(List<String> imgList,final UploadFileCallback callback) {
        //循环添加上传文件
        if (imgList != null && imgList.size() > 0) {
            for (int x = 0; x < imgList.size(); x++) {
                uploadFileAsync(x, imgList.get(x), new UploadFileCallback() {
                    @Override
                    public void uploadProgress(int uploadIndex, String uploadPath, int percent, long bytesWritten, long contentLength, boolean done) {
                        if(callback!=null){
                            callback.uploadProgress(uploadIndex, uploadPath, percent, bytesWritten, contentLength, done);
                        }
                    }

                    @Override
                    public void uploadCompleted(int uploadIndex, String uploadPath, String result) {
                        if(callback!=null){
                            callback.uploadCompleted(uploadIndex, uploadPath, result);
                        }
                    }

                    @Override
                    public void uploadFailure(int uploadIndex, String uploadPath, String errorMsg) {
                        if(callback!=null){
                            callback.uploadFailure(uploadIndex, uploadPath, errorMsg);
                        }
                    }
                });
            }
        }
    }
        /**
         * 同步批量上传
         */
    public void uploadBatchFileSync(final List<String> imgList, final UploadFileCallback callback){
        //循环添加上传文件
        if(imgList!=null&&imgList.size()>0){
                uploadFileAsync(0,imgList.get(0), new UploadFileCallback() {
                    @Override
                    public void uploadProgress(int uploadIndex, String uploadPath, int percent, long bytesWritten, long contentLength, boolean done) {
                        callback.uploadProgress(uploadIndex,uploadPath,percent,bytesWritten,contentLength,done);
                    }
                    @Override
                    public void uploadCompleted(int uploadIndex, String uploadPath, String result) {
                        if(callback!=null){
                            callback.uploadCompleted(uploadIndex,uploadPath,result);
                        }

                        if(uploadIndex++!=imgList.size()){
                            uploadFileAsync(uploadIndex,imgList.get(uploadIndex),this);
                        }

                    }
                    @Override
                    public void uploadFailure(int uploadIndex, String uploadPath, String errorMsg) {
                        if(callback!=null){
                            callback.uploadFailure(uploadIndex,uploadPath,errorMsg);
                        }

                        if(uploadIndex++!=imgList.size()){
                            uploadFileAsync(uploadIndex,imgList.get(uploadIndex),this);
                        }
                    }
                });

        }

    }
    /**
     * 异步上传图片
     */
    private void doUploadFileAsync(final HttpInfo info,final String requestTag){
        OkHttpUtil.getDefault(requestTag).doUploadFileAsync(info );
    }

    /*
    * 单文件下载
    * */
    public void singleFileAsyncDownLoad(final int downIndex,final String downUrl,final String fileName, final DownFileCallback callback){
        if(!SDCardUtils.isSDCardEnable()){
            Toast.makeText(IBaseApplication.getInstance(),"没有SD卡，不支持下载！",Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String saveFileName=fileName;
                    if(fileName==null||"".equals(fileName)){
                        saveFileName=System.currentTimeMillis()+"."+ FilePathUtil.parseSuffix(downUrl);
                    }else{
                        if(!fileName.contains(FilePathUtil.parseSuffix(downUrl))){
                            saveFileName=fileName+"."+FilePathUtil.parseSuffix(downUrl);
                        }
                    }
                    final HttpInfo info = HttpInfo.Builder()
                            .addDownloadFile(downUrl, saveFileName, new ProgressCallback() {
                                @Override
                                public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                                    if(callback!=null){
                                        callback.downProgress(downIndex,downUrl,percent,bytesWritten,contentLength,done);
                                    }
                                }

                                @Override
                                public void onResponseMain(String filePath, HttpInfo info) {
                                    if (info != null && info.isSuccessful()) {
                                        if(callback!=null){
                                            callback.onSuccessMain(downIndex,filePath,info);
                                        }
                                        OkHttpUtil.getDefault().cancelRequest(downUrl);
                                    } else {
                                        if(callback!=null){
                                            callback.onFailMain(downIndex,filePath,info);
                                        }
                                        OkHttpUtil.getDefault().cancelRequest(downUrl);
                                    }

                                }
                            })
                            .build();

                    OkHttpUtil.Builder()
                            .setReadTimeout(600)
                            .build(downUrl)//绑定请求标识
                            .doDownloadFileAsync(info);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     * 异步批量下载
     */
    public void downBatchFileAsync(List<String> imgList,final DownFileCallback callback) {
        //循环添加上传文件
        if (imgList != null && imgList.size() > 0) {
            for (int x = 0; x < imgList.size(); x++) {
                try{
                    singleFileAsyncDownLoad(x, imgList.get(x),null, callback);
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    continue;
                }

            }
        }
    }
    /**
     * 同步批量下载
     */
    public void downBatchFileSync(final List<String> imgList,final DownFileCallback callback) {
        //循环添加上传文件
        if (imgList != null && imgList.size() > 0) {
                singleFileAsyncDownLoad(0, imgList.get(0), null, new DownFileCallback() {
                    int index=0;
                    @Override
                    public void downProgress(int downIndex, String downPath, int percent, long bytesWritten, long contentLength, boolean done) {
                        index=downIndex;
                        if(callback!=null){
                            callback.downProgress(downIndex,downPath,percent,bytesWritten,contentLength,done);
                        }
                    }

                    @Override
                    public void onSuccessMain(int downIndex,String filePath, HttpInfo info) {
                        if(callback!=null){
                            callback.onSuccessMain(downIndex,filePath,info);
                        }
                        OkHttpUtil.getDefault().cancelRequest(filePath);
                        if(index++!=imgList.size()){
                            singleFileAsyncDownLoad(index,imgList.get(index),null,this);
                        }
                    }

                    @Override
                    public void onFailMain(int downIndex, String filePath, HttpInfo info) {
                        if(callback!=null){
                            callback.onFailMain(downIndex,filePath,info);
                        }
                        if(index++!=imgList.size()){
                            singleFileAsyncDownLoad(index,imgList.get(index),null,this);
                        }
                    }
                });
        }
    }

    //--支持断点下载
   DownloadFileInfo fileInfo;
   public void breakPointFileDownLoad(final int downIndex,final String downUrl,final String fileName, final DownFileCallback callback){
               try{
                   String saveFileName=fileName;
                   if(fileName==null||"".equals(fileName)){
                       saveFileName=System.currentTimeMillis()+"."+ FilePathUtil.parseSuffix(downUrl);
                   }else{
                       if(!fileName.contains(FilePathUtil.parseSuffix(downUrl))){
                           saveFileName=fileName+"."+FilePathUtil.parseSuffix(downUrl);
                       }
                   }

                   ProgressCallback progressCallback=new ProgressCallback(){

                       @Override
                       public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                           super.onProgressMain(percent, bytesWritten, contentLength, done);
                           if(callback!=null){
                               callback.downProgress(downIndex,downUrl,percent,bytesWritten,contentLength,done);
                           }
                       }

                       @Override
                       public void onResponseMain(String filePath, HttpInfo info) {
                           super.onResponseMain(filePath, info);
                           if (info != null && info.isSuccessful()) {
                               if(callback!=null){
                                   callback.onSuccessMain(downIndex,filePath,info);
                               }
                               if(fileInfoMap!=null&&fileInfoMap.containsKey(downUrl)){
                                   fileInfoMap.remove(downUrl);
                               }
                           } else {
                               if(callback!=null){
                                   callback.onFailMain(downIndex,filePath,info);
                               }
                           }
                           OkHttpUtil.getDefault().cancelRequest(downUrl);
                       }
                   };

                   fileInfo=new DownloadFileInfo(downUrl,saveFileName,progressCallback);
                   if(fileInfoMap!=null){
                       if(fileInfoMap.containsKey(downUrl)){
                           fileInfoMap.remove(downUrl);
                           fileInfoMap.put(downUrl,fileInfo);
                       }else{
                           fileInfoMap.put(downUrl,fileInfo);
                       }

                   }
                   HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfo).build();
                   OkHttpUtil.Builder().setReadTimeout(600).build(downUrl).doDownloadFileAsync(info);
               }catch (Exception e){
                   e.printStackTrace();
               }
   }

   public void pauseFileDownLoad(String fileUrl){
       if(fileInfoMap!=null&&fileInfoMap.containsKey(fileUrl)){
           fileInfoMap.get(fileUrl).setDownloadStatus(DownloadStatus.PAUSE);
       }
   }
   public void continueFileDownLoad(String fileUrl){
       if(fileInfoMap!=null&&fileInfoMap.containsKey(fileUrl)&&fileInfoMap.get(fileUrl)!=null){
           HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfoMap.get(fileUrl)).build();
           OkHttpUtil.Builder().setReadTimeout(600).build(fileUrl).doDownloadFileAsync(info);
       }
   }
   public Map<String,DownloadFileInfo> getFileInfoMap(){
       return fileInfoMap;
   }
    public void cancelRequest(String requestTag){
        OkHttpUtil.getDefault().cancelRequest(requestTag);
    }
    public void addPublicParams(HashMap<String, String> params){
        params.put("mode", "json");
        params.put("platformId", platformId);
        params.put("deviceAppId", deviceID);
        params.put("deviceType", deviceType);
        params.put("appVersion", version);
        params.put("sn",uuid);
    }
    public HashMap<String, String> getHeads(){
        HashMap<String, String> heads=new HashMap<String,String>();
        //heads.put("Accept-Encoding","gzip");
        return heads;
    }
}