package com.iyoho.social.fragment.tab;

import android.net.Uri;
import android.view.View;

import com.iyoho.social.R;
import com.iyoho.social.base.IBaseFragment;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class MessageFragment extends IBaseFragment {

    private static final String TAG = "MessageFragment";
    private ConversationListFragment conversationListFragment;
    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        //conversationListFragment= (ConversationListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.conversationlist);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
       /* Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        conversationListFragment.setUri(uri);*/
    }

    @Override
    public void onClick(View v) {

    }
}