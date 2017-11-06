package com.iyoho.social.utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.iyoho.social.R;
import com.iyoho.social.jpush.LocalBroadcastManager;
import com.iyoho.social.jpush.Logger;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import cn.jpush.android.api.TagAliasCallback;

import static com.umeng.socialize.utils.DeviceConfig.context;
import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by ab053167 on 2017/10/17.
 */

public class JPushUtils {
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private static volatile JPushUtils mInstance;
    private String tag="JPushLog";
    private JPushUtils() {
    }
    public static JPushUtils getInstance() {
        if (mInstance == null) {
            synchronized (JPushUtils.class) {
                if (mInstance == null) {
                    mInstance = new JPushUtils();
                }
            }
        }
        return mInstance;
    }

    public void initJPush(Context context){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(context);     		// 初始化 JPush
    }

    public String getImei(Context context){
        return  ExampleUtil.getImei(context, "");
    }

    public String getAppKey(Context context){
        return ExampleUtil.getAppKey(context);
    }

    public String getDeviceId(Context context){
        return ExampleUtil.getDeviceId(context);
    }
    public String getPackageName(Context context){
        return context.getPackageName();
    }
    public void stopPush(Context context){
        JPushInterface.stopPush(context);
    }
    public void resumePush(Context context){
        JPushInterface.resumePush(context);
    }
    public String getRegistrationID(Context context){
        return JPushInterface.getRegistrationID(context);
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    public void setStyleBasic(Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    public void setStyleCustom(Context context) {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(context, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.mipmap.ic_launcher;
        builder.developerArg0 = "developerArg2";
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


    public void setAddActionsStyle(Context context) {
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(context);
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(10, builder);
    }

    public void setAliasAndTags(final Context context, String alias, Set<String> tags){
        JPushInterface.setAliasAndTags(context, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });

    }

    int setAliasCount=0;
    public void setAlias(final Context context,final String alias){
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(context, R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return ;
        }
        JPushInterface.setAlias(context, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(i==0){
                    Logger.d(tag,setAliasCount+"-"+"alias setting success---"+s);
                }else{
                    setAliasCount=setAliasCount+1;
                    if(setAliasCount<=5){
                        setAlias(context,alias);
                    }
                }
            }
        });
    }

    int setTagsCount=0;
    public void setTags(final Context context,final Set<String> tags){
        if (tags==null) {
            Toast.makeText(context, R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
            return ;
        }
        JPushInterface.setTags(context, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(i==0){
                    Logger.d(tag,setTagsCount+"-"+"tags setting success---"+s);
                }else{
                    setTagsCount=setTagsCount+1;
                    if(setTagsCount<=5){
                        setTags(context,tags);
                    }
                }
            }
        });
    }

    int setAliasAndTagsCount=0;
    public void setAliasTags(final Context context,final String alias,final Set<String> tags){
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(context, R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return ;
        }
        if (tags==null) {
            Toast.makeText(context, R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
            return ;
        }
        JPushInterface.setAliasAndTags(context, alias,tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(i==0){
                    Logger.d(tag,setAliasAndTagsCount+"-"+"AliasAndTags setting success---"+s);
                }else{
                    setAliasAndTagsCount=setAliasAndTagsCount+1;
                    if(setAliasAndTagsCount<=5){
                        setAliasTags(context,alias,tags);
                    }
                }
            }
        });
    }
    MessageReceiver mMessageReceiver;
    public void registerMessageReceiver(Context context) {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, filter);
    }

    public void unregisterReceiver(Context context){
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
        }
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Toast.makeText(context,showMsg,Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
            }
        }
    }
}
