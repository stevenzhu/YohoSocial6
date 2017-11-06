package com.iyoho.social.fragment.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iyoho.social.Entry.MessageEvent;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseFragment;
import com.iyoho.social.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    private View homeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(homeView==null){
            Log.d("HomeFragment-","onCreateView");
            homeView=inflater.inflate(R.layout.fragment_home, container, false);
        }
        return homeView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("HomeFragment-","onViewCreated");
        initView(homeView);
        initEvent();
        initData();
    }

    public void initView(View view) {
        homeView=view;
        initSetting(homeView);
    }


    public void initEvent() {
       boolean isRegistered= EventBus.getDefault().isRegistered(this);
        if(!isRegistered){
            EventBus.getDefault().register(this);
        }
    }


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
