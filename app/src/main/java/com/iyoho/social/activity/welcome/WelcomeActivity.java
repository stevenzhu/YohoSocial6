package com.iyoho.social.activity.welcome;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.iyoho.social.R;
import com.iyoho.social.activity.MainActivity;
import com.iyoho.social.activity.MainTabActivity;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.utils.MapUtils;
import com.iyoho.social.utils.PermissionDispatcherHelper;

/**
 * Created by ab053167 on 2017/10/31.
 */

public class WelcomeActivity extends IBaseActivity {
    private ImageView welcomeImg;

    @Override
    public Bundle getBundle() throws Exception {
        return getBundle(WelcomeActivity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        welcomeImg= (ImageView) findViewById(R.id.welcomeImg);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Glide.with(this).load(R.drawable.icon_welcome).into(welcomeImg);

        PermissionDispatcherHelper.getInstance().checkLocation(WelcomeActivity.this, new PermissionDispatcherHelper.OnPermissionListener() {
            @Override
            public void onPermissionCompleted() {
                MapUtils.getInstance().getLocation(getApplicationContext(), new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                IBaseActivity.start(WelcomeActivity.this, MainActivity.class,null);
                                finish();
                            }
                        },1000);
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionDispatcherHelper.onRequestPermissionsResult(WelcomeActivity.this,requestCode, grantResults);
    }
}
