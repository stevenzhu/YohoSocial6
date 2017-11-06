package com.iyoho.social.activity.message;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.iyoho.social.R;
import com.iyoho.social.base.IBaseActivity;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * Created by ab053167 on 2017/11/2.
 */

public class ConversationActivity extends IBaseActivity {
    private ConversationFragment  conversation;
    @Override
    public Bundle getBundle() throws Exception {
        return getBundle(ConversationActivity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_message_conversation;
    }

    @Override
    public void initView() {
        conversation= (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
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
