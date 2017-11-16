package com.iyoho.social.fragment.tab;

import android.view.View;

import com.iyoho.social.R;
import com.iyoho.social.base.IBaseFragment;

import io.rong.imkit.RongIM;


public class MineTabFragment extends IBaseFragment {
    private static final String TAG = "MineFragment";

    @Override
    public int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tvBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(getActivity(),"26594","ttlltt");
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}