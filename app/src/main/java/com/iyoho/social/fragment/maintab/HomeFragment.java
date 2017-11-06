package com.iyoho.social.fragment.maintab;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iyoho.social.Entry.MessageEvent;
import com.iyoho.social.R;
import com.iyoho.social.activity.EventBus1Activity;
import com.iyoho.social.activity.MainTabActivity;
import com.iyoho.social.base.IBaseFragment;
import com.iyoho.social.utils.EventBusUtils;
import com.iyoho.social.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeFragment extends IBaseFragment {
    private static final String TAG = "HomeFragment";
    private View homeView;
    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        homeView=view;
        initSetting(homeView);
    }

    @Override
    public void initEvent() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
    public void initSetting(View viewHome){
        String homeType=new SPUtils("home").getString("homeType","list");
        //String homeType="list";
        if(homeType!=null&&"list".equals(homeType)){
            viewHome.findViewById(R.id.homeListFragment).setVisibility(View.GONE);
            viewHome.findViewById(R.id.homeMapFragment).setVisibility(View.VISIBLE);
        }else{
            viewHome.findViewById(R.id.homeListFragment).setVisibility(View.VISIBLE);
            viewHome.findViewById(R.id.homeMapFragment).setVisibility(View.GONE);
        }
    }
    @Subscribe
    public void onEventMainThread(MessageEvent event){
        if(event!=null&&event.getmClass()==HomeFragment.class){
            System.out.println("--------HomeFragment"+event.getMessage1());
            initSetting(homeView);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
