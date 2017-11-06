package com.iyoho.social.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoho.social.Entry.MessageEvent;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.scanner.CaptureActivity;
import com.iyoho.social.utils.EventBusUtils;
import com.iyoho.social.utils.PermissionDispatcherHelper;

import org.greenrobot.eventbus.Subscribe;

public class EventBus2Activity extends IBaseActivity {
    private TextView textView;

    @Override
    public Bundle getBundle()throws Exception {
        return IBaseActivity.getBundle(EventBus2Activity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_test_main;
    }

    @Override
    public void initView() {
        textView= (TextView) findViewById(R.id.intoOKhttpTextView);
    }

    @Override
    public void initEvent() {
        EventBusUtils.register(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle= null;
        try {
            bundle = getBundle();
            System.out.println("----2"+bundle+"---"+bundle.getString("tag1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.intoOKhttpTextView:
                EventBusUtils.post(new MessageEvent(MainTestActivity.class,"main2"));
                PermissionDispatcherHelper.getInstance().checkCamera(EventBus2Activity.this, new PermissionDispatcherHelper.OnPermissionListener() {
                    @Override
                    public void onPermissionCompleted() {
                        IBaseActivity.start(EventBus2Activity.this,CaptureActivity.class,null);
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionDispatcherHelper.onRequestPermissionsResult(EventBus2Activity.this,requestCode, grantResults);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){
        if(event!=null&&event.getmClass()==EventBus2Activity.class){
            System.out.println("--------EventBus2Activity"+event.getMessage1());
            Toast.makeText(EventBus2Activity.this,event.getMessage1(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

}
