package com.iyoho.social.base;

import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.iyoho.social.utils.AndroidUtilsCode;
import com.iyoho.social.utils.JPushUtils;
import com.mob.MobApplication;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.Encoding;
import com.okhttplib.cookie.PersistentCookieJar;
import com.okhttplib.cookie.cache.SetCookieCache;
import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;

import io.rong.imkit.RongIM;

/**
 * Created by ab053167 on 2017/9/12.
 */

public class IBaseApplication extends MobApplication {
    private static IBaseApplication application;
    public static IBaseApplication getInstance(){
        return application;
    }
    public static String downloadFileDir = Environment.getExternalStorageDirectory()+"/yoho_down/";
    public static String cacheDir = Environment.getExternalStorageDirectory().getPath()+"/yoho_cache/";
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(getApplicationContext());
        AndroidUtilsCode.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        /*初始化推送*/
        JPushUtils.getInstance().initJPush(getApplicationContext());
        JPushUtils.getInstance().registerMessageReceiver(getApplicationContext());
        JPushUtils.getInstance().setStyleCustom(getApplicationContext());

        PlatformConfig.setWeixin("wx1a4482edd9c1d0bf", "076695267e3978b7c245bf50e43b4316");
        PlatformConfig.setQQZone("1106109640", "1ZGhWAQpvUeRrFMA");
        PlatformConfig.setSinaWeibo("1658074632", "a673364d560295197bb3e9b4297f497d", "http://sns.whalecloud.com");
        UMShareAPI.init(getApplicationContext(),"59f714c7a40fa3301900000d");

        application=this;
        OkHttpUtil.init(application)
                .setConnectTimeout(15)//连接超时时间
                .setWriteTimeout(15)//写超时时间
                .setReadTimeout(15)//读超时时间
                .setMaxCacheSize(50 * 1024 * 1024)//缓存空间大小
                .setCacheType(CacheType.FORCE_NETWORK)//缓存类型
                .setHttpLogTAG("HttpLog")//设置请求日志标识
                .setIsGzip(false)//Gzip压缩，需要服务端支持
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(false)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
                .setCachedDir(new File(cacheDir))//设置缓存目录
                .setDownloadFileDir(downloadFileDir)//文件下载保存目录
                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(application)))//持久化cookie
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
