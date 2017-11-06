package com.iyoho.social.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoho.social.Entry.TestEntry;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.base.IBaseApplication;
import com.iyoho.social.server.ServerCallback;
import com.iyoho.social.server.ServerInterfaces;
import com.iyoho.social.utils.FastJsonUtils;
import com.iyoho.social.utils.FilePathUtil;
import com.iyoho.social.utils.PermissionDispatcherHelper;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.ProgressCallback;
import com.yoho.glide.progress.CircleProgressView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.iyoho.com.imgselectlibrary.view.ImageSelectorActivity;

import static android.R.attr.path;
import static android.os.Build.VERSION_CODES.O;

public class SelectAlbumActivity extends IBaseActivity {
    private Button postAsync;
    private Button getAsync;
    private Button postSync;
    private Button getSync;
    private Button singleFileUpload;
    private Button batchFileAsyncUpload;
    private Button batchFileSyncUpload;
    private Button singleFileDownLoad;
    private Button cancelFileDownLoad;
    private Button moreFileSyncDownLoad;
    private Button moreFileAsyncDownLoad;
    private Button breakPointFileDownLoad;
    private Button pauseFileDownLoad;
    private Button continueFileDownLoad;
    private CircleProgressView progressView;
    private Button intoGlide;
    private TextView content;
    private TAG tag= TAG.postAsync;
    private enum TAG{
        postAsync,getAsync,postSync,getSync,singleFileUpload,batchFileAsyncUpload,batchFileSyncUpload,singleFileDownLoad,cancelFileDownload,breakPointFileDownLoad,pauseFileDownLoad,continueFileDownLoad
    }
    private  final String downUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497276379533&di=71435f66d66221eb36dab266deb9d6d2&imgtype=0&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201608%2F02%2F190418bmy9zqm94qxlmqf4.gif";

    @Override
    public Bundle getBundle()throws Exception {
        return IBaseActivity.getBundle(SelectAlbumActivity.class);
    }
    @Override
    public int initLayout() {
        return R.layout.test_select;
    }
    @Override
    public void initView() {
        postAsync= (Button) findViewById(R.id.postAsync);
        getAsync= (Button) findViewById(R.id.getAsync);
        postSync= (Button) findViewById(R.id.postSync);
        getSync= (Button) findViewById(R.id.getSync);
        singleFileUpload= (Button) findViewById(R.id.singleFileUpload);
        batchFileAsyncUpload= (Button) findViewById(R.id.batchFileAsyncUpload);
        batchFileSyncUpload= (Button) findViewById(R.id.batchFileSyncUpload);
        singleFileDownLoad= (Button) findViewById(R.id.singleFileDownLoad);
        cancelFileDownLoad= (Button) findViewById(R.id.cancelFileDownLoad);
        moreFileAsyncDownLoad= (Button) findViewById(R.id.moreFileAsyncDownLoad);
        moreFileSyncDownLoad= (Button) findViewById(R.id.moreFileSyncDownLoad);
        breakPointFileDownLoad= (Button) findViewById(R.id.breakPointFileDownLoad);
        pauseFileDownLoad= (Button) findViewById(R.id.pauseFileDownLoad);
        continueFileDownLoad= (Button) findViewById(R.id.continueFileDownLoad);
        progressView= (CircleProgressView) findViewById(R.id.progressView);
        intoGlide= (Button) findViewById(R.id.intoGlide);
        content= (TextView) findViewById(R.id.content);

    }

    @Override
    public void initEvent() {
        postAsync.setOnClickListener(this);
        getAsync.setOnClickListener(this);
        postSync.setOnClickListener(this);
        getSync.setOnClickListener(this);
        singleFileUpload.setOnClickListener(this);
        batchFileAsyncUpload.setOnClickListener(this);
        batchFileSyncUpload.setOnClickListener(this);
        singleFileDownLoad.setOnClickListener(this);
        cancelFileDownLoad.setOnClickListener(this);
        moreFileAsyncDownLoad.setOnClickListener(this);
        moreFileSyncDownLoad.setOnClickListener(this);
        breakPointFileDownLoad.setOnClickListener(this);
        pauseFileDownLoad.setOnClickListener(this);
        continueFileDownLoad.setOnClickListener(this);
        intoGlide.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//         PermissionDispatcherHelper.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            final ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            if(tag== TAG.singleFileUpload){
                  singleFileUpload(images.get(0));
            }else if(tag== TAG.batchFileAsyncUpload){
                  batchFileAsyncUpload(images);
            }else if(tag== TAG.batchFileSyncUpload){
                  setBatchFileSyncUpload(images);
            }
        }else if(requestCode==0&&resultCode==1){
            Toast.makeText(SelectAlbumActivity.this,"select",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.postAsync:
            tag= TAG.postAsync;
            postAsync();
            break;
        case R.id.getAsync:
            tag= TAG.getAsync;
            getAsync();
            break;
        case R.id.postSync:
            tag= TAG.postSync;
            postSync();
            break;
        case R.id.getSync:
            tag= TAG.getSync;
            getSync();
            break;
        case R.id.singleFileUpload:
            tag= TAG.singleFileUpload;
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    ImageSelectorActivity.start(SelectAlbumActivity.this,1,ImageSelectorActivity.MODE_SINGLE,true,true,false);
                }
            });
            break;
        case R.id.batchFileAsyncUpload:
            tag= TAG.batchFileAsyncUpload;
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    ImageSelectorActivity.start(SelectAlbumActivity.this,5,ImageSelectorActivity.MODE_MULTIPLE,true,true,false);
                }
            });
            break;
        case R.id.batchFileSyncUpload:
            tag= TAG.batchFileSyncUpload;
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    ImageSelectorActivity.start(SelectAlbumActivity.this,5,ImageSelectorActivity.MODE_MULTIPLE,true,true,false);
                }
            });
            break;
        case R.id.singleFileDownLoad:
            tag= TAG.singleFileDownLoad;
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    singleFileDownLoad(downUrl,null);
                }
            });
            break;
        case R.id.cancelFileDownLoad:
            tag= TAG.cancelFileDownload;
            OkHttpUtil.getDefault().cancelRequest(downUrl);

        break;
        case R.id.moreFileAsyncDownLoad:
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    List<String> downFiles=new ArrayList<String>();
                    downFiles.add("http://img.ivsky.com/img/tupian/slides/201708/14/bailu.jpg");
                    downFiles.add("http://img8.zol.com.cn/bbs/upload/16955/16954476.png");
                    downFiles.add("http://image52.360doc.com/DownloadImg/2012/06/0316/24581213_2.jpg");
                    downFiles.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497276379533&di=71435f66d66221eb36dab266deb9d6d2&imgtype=0&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201608%2F02%2F190418bmy9zqm94qxlmqf4.gif");
                    downFiles.add("https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/girl.jpg");
                    downBatchFileAsync(downFiles);
                }
            });
            break;
        case R.id.moreFileSyncDownLoad:
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    List<String> downFiles=new ArrayList<String>();
                    downFiles.add("http://img.ivsky.com/img/tupian/slides/201708/14/bailu.jpg");
                    downFiles.add("http://img8.zol.com.cn/bbs/upload/16955/16954476.png");
                    downFiles.add("http://image52.360doc.com/DownloadImg/2012/06/0316/24581213_2.jpg");
                    downFiles.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497276379533&di=71435f66d66221eb36dab266deb9d6d2&imgtype=0&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201608%2F02%2F190418bmy9zqm94qxlmqf4.gif");
                    downFiles.add("https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/girl.jpg");
                    downBatchFileSync(downFiles);
                }
            });
            break;
        case R.id.breakPointFileDownLoad:
            tag= TAG.breakPointFileDownLoad;
            PermissionDispatcherHelper.getInstance().checkExternalStorage(this, new PermissionDispatcherHelper.OnPermissionListener() {
                @Override
                public void onPermissionCompleted() {
                    breakPointFileDownLoad(0,downUrl,null);
                }
            });

            break;
        case R.id.pauseFileDownLoad:
            tag= TAG.pauseFileDownLoad;
            ServerInterfaces.getInstance().pauseFileDownLoad(downUrl);
            break;
        case R.id.continueFileDownLoad:
            tag= TAG.continueFileDownLoad;
            ServerInterfaces.getInstance().continueFileDownLoad(downUrl);
            break;
        case R.id.intoGlide:
            IBaseActivity.startForResult(SelectAlbumActivity.this, EventBus1Activity.class, null,0);
            break;
    }
    }

    public void postAsync(){
        final String url = "http://api.k780.com:88";
        HashMap<String,String> params=new HashMap<String,String>();
        params.put("app","life.time");
        params.put("appkey","10003");
        params.put("sign","b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format","json");
        ServerInterfaces.getInstance().postAsync(this, url,params,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    TestEntry te = FastJsonUtils.getSingleBean(result, TestEntry.class);
                    System.out.println("######获取完成#######\n"+result);
                    content.setText("######获取完成#######\n"+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int errorCode, String strMsg) {
                System.out.println("######获取失败#######\n"+strMsg);
                content.setText("######获取失败#######\n"+strMsg);
            }
        });
    }
    public void getAsync(){
        final String url = "http://api.k780.com:88?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        ServerInterfaces.getInstance().getAsync(this, url,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    TestEntry te = FastJsonUtils.getSingleBean(result, TestEntry.class);
                    System.out.println("######获取完成#######\n"+result);
                    content.setText("######获取完成#######\n"+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int errorCode, String strMsg) {
                System.out.println("######获取失败#######\n"+strMsg);
                content.setText("######获取失败#######\n"+strMsg);
            }
        });
    }
    public void postSync(){
        final String url = "http://api.k780.com:88";
        HashMap<String,String> params=new HashMap<String,String>();
        params.put("app","life.time");
        params.put("appkey","10003");
        params.put("sign","b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format","json");
        ServerInterfaces.getInstance().postSync(this, url,params,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    TestEntry te = FastJsonUtils.getSingleBean(result, TestEntry.class);
                    System.out.println("######获取完成#######\n"+result);
                    content.setText("######获取完成#######\n"+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int errorCode, String strMsg) {
                System.out.println("######获取失败#######\n"+strMsg);
                content.setText("######获取失败#######\n"+strMsg);
            }
        });
    }
    public void getSync(){
        final String url = "http://api.k780.com:88?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        ServerInterfaces.getInstance().getSync(this, url,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    TestEntry te = FastJsonUtils.getSingleBean(result, TestEntry.class);
                    System.out.println("######获取完成#######\n"+result);
                    content.setText("######获取完成#######\n"+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int errorCode, String strMsg) {
                System.out.println("######获取失败#######\n"+strMsg);
                content.setText("######获取失败#######\n"+strMsg);
            }
        });
    }
    public void singleFileUpload(String filePath){
        ServerInterfaces.getInstance().uploadFileAsync(filePath, new ServerInterfaces.UploadFileCallback() {
            @Override
            public void uploadProgress(int uploadIndex, String uploadPath, int percent, long bytesWritten, long contentLength, boolean done) {
                System.out.println("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                content.setText("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                progressView.setProgress(percent);
            }

            @Override
            public void uploadCompleted(final int uploadIndex,final String uploadPath, String result) {
                final String result1=result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                        content.setText("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                    }
                });
            }

            @Override
            public void uploadFailure(int uploadIndex, String uploadPath, String errorMsg) {
                System.out.println("######fail>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
                content.setText("######fial>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
            }
        });
    }
    public void setBatchFileSyncUpload(List<String> images){
        if(images!=null&&images.size()>0){
            ServerInterfaces.getInstance().uploadBatchFileSync(images, new ServerInterfaces.UploadFileCallback() {
                @Override
                public void uploadProgress(int uploadIndex, String uploadPath, int percent, long bytesWritten, long contentLength, boolean done) {
                    System.out.println("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                    content.setText("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                    progressView.setProgress(percent);
                }

                @Override
                public void uploadCompleted(final int uploadIndex,final String uploadPath, String result) {
                    final String result1=result;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                            content.setText("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                        }
                    });
                }

                @Override
                public void uploadFailure(int uploadIndex, String uploadPath, String errorMsg) {
                    System.out.println("######fail>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
                    content.setText("######fail>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
                }


            });


        }
    }
    public void batchFileAsyncUpload(List<String> images){
        if(images!=null&&images.size()>0){
            ServerInterfaces.getInstance().uploadBatchFileAsync(images, new ServerInterfaces.UploadFileCallback() {
                @Override
                public void uploadProgress(int uploadIndex, String uploadPath, int percent, long bytesWritten, long contentLength, boolean done) {
                    System.out.println("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                    content.setText("######"+uploadIndex+"#上传进度#"+percent+"（"+uploadPath+")");
                    progressView.setProgress(percent);
                }

                @Override
                public void uploadCompleted(final int uploadIndex, final  String uploadPath, String result) {
                    final String result1=result;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                            content.setText("######success>>"+uploadIndex+"#上传完成#"+"（"+uploadPath+")");
                        }
                    });
                }

                @Override
                public void uploadFailure(int uploadIndex, String uploadPath, String errorMsg) {
                    System.out.println("######fial>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
                    content.setText("######fail>>"+uploadIndex+"#上传失败#"+"（"+uploadPath+")"+errorMsg);
                }


            });


        }
    }
    public void singleFileDownLoad(String downUrl,String fileName){
        ServerInterfaces.getInstance().singleFileAsyncDownLoad(0,downUrl,fileName,new ServerInterfaces.DownFileCallback(){
            @Override
            public void downProgress(int downIndex, String downPath, int percent, long bytesWritten, long contentLength, boolean done) {
                content.setText("####"+downIndex+"####"+percent+"####");
                progressView.setProgress(percent);
            }

            @Override
            public void onSuccessMain(int downIndex,String filePath, HttpInfo info) {
                System.out.println("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
            @Override
            public void onFailMain(int downIndex, String filePath, HttpInfo info) {
                System.out.println("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
        });
    }
    public void downBatchFileAsync(List<String> imgList){
        ServerInterfaces.getInstance().downBatchFileAsync(imgList,new ServerInterfaces.DownFileCallback(){
            @Override
            public void downProgress(int downIndex, String downPath, int percent, long bytesWritten, long contentLength, boolean done) {
                System.out.println("####"+downIndex+"#"+percent+"#"+downPath);
                content.setText("####"+downIndex+"#"+percent+"#"+downPath);
                progressView.setProgress(percent);
            }

            @Override
            public void onSuccessMain(int downIndex,String filePath, HttpInfo info) {
                System.out.println("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }

            @Override
            public void onFailMain(int downIndex, String filePath, HttpInfo info) {
                System.out.println("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
        });
    }
    public void downBatchFileSync(List<String> imgList){
        ServerInterfaces.getInstance().downBatchFileSync(imgList,new ServerInterfaces.DownFileCallback(){
            @Override
            public void downProgress(int downIndex, String downPath, int percent, long bytesWritten, long contentLength, boolean done) {
                System.out.println("####"+downIndex+"#"+percent+"#"+downPath);
                content.setText("####"+downIndex+"#"+percent+"#"+downPath);
                progressView.setProgress(percent);
            }

            @Override
            public void onSuccessMain(int downIndex,String filePath, HttpInfo info) {
                System.out.println("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
            @Override
            public void onFailMain(int downIndex, String filePath, HttpInfo info) {
                System.out.println("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
        });
    }
    public void breakPointFileDownLoad(final int downIndex, String downUrl, final String fileName){
        ServerInterfaces.getInstance().breakPointFileDownLoad(downIndex,downUrl, fileName,new ServerInterfaces.DownFileCallback(){
            @Override
            public void downProgress(int downIndex, String downPath, int percent, long bytesWritten, long contentLength, boolean done) {
                System.out.println("####break="+percent+"####");
                content.setText("####break="+percent+"####");
                progressView.setProgress(percent);

            }

            @Override
            public void onSuccessMain(int downIndex,String filePath, HttpInfo info) {
                System.out.println("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail()+"##"+ServerInterfaces.getInstance().getFileInfoMap().get(filePath).getDownloadStatus());
                content.setText("####Success>>"+downIndex+"####"+filePath+"####"+info.getRetDetail()+"##"+ServerInterfaces.getInstance().getFileInfoMap().get(filePath).getDownloadStatus());
            }
            @Override
            public void onFailMain(int downIndex, String filePath, HttpInfo info) {
                System.out.println("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
                content.setText("####fail>>"+downIndex+"####"+filePath+"####"+info.getRetDetail());
            }
        });
    }
}
