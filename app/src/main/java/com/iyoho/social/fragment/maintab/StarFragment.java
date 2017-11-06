package com.iyoho.social.fragment.maintab;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.iyoho.social.R;
import com.iyoho.social.activity.MainTabActivity;
import com.iyoho.social.base.IBaseFragment;
import com.iyoho.social.view.TypeFaceTextView;
import com.mob.commons.SMSSDK;

import cn.smssdk.OnSendMessageHandler;


public class StarFragment extends IBaseFragment {
    private TypeFaceTextView tvText;
    private static final String TAG = "StarFragment";
    private String mTagtext;

    @Override
    public int initLayout() {
        return R.layout.fragment_star;
    }

    @Override
    public void initView(View view) {
        tvText = (TypeFaceTextView) view.findViewById(R.id.tv_text);
//        cn.smssdk.SMSSDK.re
//        cn.smssdk.SMSSDK.getVerificationCode("86", "18801103745", new OnSendMessageHandler() {
//            @Override
//            public boolean onSendMessage(String s, String s1) {
//                Toast.makeText(getActivity(),s+"["+s1,Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        mTagtext = getArguments().getString(MainTabActivity.TAG);
        if (mTagtext != null && !TextUtils.isEmpty(mTagtext)) {
            tvText.setText(mTagtext);
        } else {
            tvText.setText("Null");
        }
    }

    @Override
    public void onClick(View v) {

    }
}