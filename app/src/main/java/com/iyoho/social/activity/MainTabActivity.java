package com.iyoho.social.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.iyoho.social.Entry.ColorShades;
import com.iyoho.social.Entry.MessageEvent;
import com.iyoho.social.R;
import com.iyoho.social.activity.message.ConversationActivity;
import com.iyoho.social.activity.welcome.WelcomeActivity;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.fragment.maintab.HomeFragment;
import com.iyoho.social.fragment.maintab.MessageFragment;
import com.iyoho.social.fragment.maintab.StarFragment;
import com.iyoho.social.utils.EventBusUtils;
import com.iyoho.social.utils.MapUtils;
import com.iyoho.social.utils.PermissionDispatcherHelper;
import com.iyoho.social.utils.SPUtils;
import com.iyoho.social.view.TypeFaceTextView;
import com.yoho.glide.GlideImageView;

import java.util.Map;

import im.quar.autolayout.view.AutoLinearLayout;
import im.quar.autolayout.view.AutoRelativeLayout;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by ab053167 on 2017/10/20.
 */

public class MainTabActivity extends IBaseActivity implements TabHost.OnTabChangeListener,DrawerLayout.DrawerListener{
    public static final String TAG = "MainTabActivity";
    private DrawerLayout drawerLayout;
    private AutoLinearLayout mainCenterLayout;
    private RelativeLayout mainLeftMenuLayout;
    private AutoRelativeLayout headLayout;
    private GlideImageView headImg;
    private TypeFaceTextView headTitle;
    private GlideImageView headAddImg;
    private TypeFaceTextView headAddText;
    private TypeFaceTextView headMoreText;
    private FragmentTabHost mTabHost;
    private String[] mTabTexts;
    private int[] mTabIcons;
    private ColorShades mColorShades;
    @Override
    public Bundle getBundle() throws Exception {
        return getBundle(MainActivity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_maintab;
    }

    @Override
    public void initView() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mainCenterLayout= (AutoLinearLayout) findViewById(R.id.mainCenterLayout);
        mainLeftMenuLayout= (RelativeLayout) findViewById(R.id.mainLeftMenuLayout);
        mTabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        headLayout= (AutoRelativeLayout) findViewById(R.id.headLayout);
        headImg= (GlideImageView) findViewById(R.id.headImg);
        headTitle= (TypeFaceTextView) findViewById(R.id.headTitle);
        headAddImg= (GlideImageView) findViewById(R.id.headAddImg);
        headAddText= (TypeFaceTextView) findViewById(R.id.headAddText);
        headMoreText= (TypeFaceTextView) findViewById(R.id.headMoreText);

        headAddText.setVisibility(View.GONE);
        headMoreText.setVisibility(View.GONE);

        connect("XEb4SeUAR9OK5INscXMpeTBwF6b3ugcqxL5xchQAHbppyT/nVUusULlm1uSFR9wF8icXprLw8TG+mY0O3o46S/TIL0ph7OKF");
    }

    @Override
    public void initEvent() {
        mTabHost.setOnTabChangedListener(this);
        headImg.setOnClickListener(this);
        drawerLayout.addDrawerListener(this);
        headAddImg.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mColorShades = new ColorShades();
        mTabTexts = getResources().getStringArray(R.array.tab_texts);
        mTabIcons = new int[]{R.drawable.selector_nvg_message, R.drawable.selector_nvg_contacts, R.drawable.selector_nvg_star};
        //关联主布局
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //默认设置选中第一个
        mTabHost.setCurrentTab(0);
        //去掉在版本中的横线,FragmentTabHost在低版本中,每个Tab之间会有条横线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        initBottomNavigationView();
}
    private void initBottomNavigationView() {

        for (int i = 0; i < mTabTexts.length; i++) {
            View mTabView = View.inflate(this, R.layout.layout_item_tab, null);
            TypeFaceTextView tabText=mTabView.findViewById(R.id.tabText);
            GlideImageView tabIcon=mTabView.findViewById(R.id.tabIcon);
            TypeFaceTextView msgCount = mTabView.findViewById(R.id.msgCount);
            tabText.setText(mTabTexts[i]);
            //GlideImageLoader.create(tabIcon).loadLocalImage(mTabIcons[i],null);
            tabIcon.setImageResource(mTabIcons[i]);
            //创建TabSpec
            TabHost.TabSpec messageTabSpec = mTabHost.newTabSpec(mTabTexts[i]).setIndicator(mTabView);
            Bundle bundle = new Bundle();
            bundle.putString(TAG, mTabTexts[i]);
            switch (i) {
                case 0:
                    msgCount.setText("99");
                    msgCount.setVisibility(View.GONE);
                    mTabHost.addTab(messageTabSpec, HomeFragment.class, bundle);
                    break;
                case 1:
                    msgCount.setText("8");
                    mTabHost.addTab(messageTabSpec, MessageFragment.class, bundle);
                    break;
                case 2:
                    msgCount.setText("");
                    msgCount.setVisibility(View.GONE);
                    mTabHost.addTab(messageTabSpec, StarFragment.class, bundle);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.headImg:
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.headAddImg:
                String homeType=new SPUtils("home").getString("homeType","list");
                if(homeType.equals("list")){
                    new SPUtils("home").put("homeType","map");
                }else{
                    new SPUtils("home").put("homeType","list");
                }
                EventBusUtils.post(new MessageEvent(HomeFragment.class));
                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        headTitle.setText(tabId);
        headAddImg.setVisibility(mTabTexts[0].equals(tabId) ? View.VISIBLE : View.GONE);
        headAddText.setVisibility(mTabTexts[1].equals(tabId) ? View.VISIBLE : View.GONE);
        headMoreText.setVisibility(mTabTexts[2].equals(tabId) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        Log.d("tag",slideOffset+"");
        //设置主布局随菜单滑动而滑动
        int drawerViewWidth = drawerView.getWidth();
        mainCenterLayout.setTranslationX(drawerViewWidth * slideOffset);

        //设置控件最先出现的位置
        double padingLeft = drawerViewWidth * (1 - 0.618) * (1 - slideOffset);
        mainLeftMenuLayout.setPadding((int) padingLeft, 0, 0, 0);

        //设置Title颜色渐变
        mColorShades.setFromColor("#001AA7F2").setToColor(Color.WHITE).setShade(slideOffset);
        headLayout.setBackgroundColor(mColorShades.generate());
    }

    @Override
    public void onDrawerOpened(View view) {

    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    //RongIM.getInstance().startPrivateChat(MainTabActivity.this,"26594","ttlltt");

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--ErrorCode" + errorCode);
                }
            });
        }
    }


}
