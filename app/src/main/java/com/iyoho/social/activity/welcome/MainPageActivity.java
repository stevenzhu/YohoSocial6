package com.iyoho.social.activity.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.iyoho.social.R;
import com.iyoho.social.fragment.tab.HomeFragment;
import com.iyoho.social.fragment.tab.MineFragment;
import com.iyoho.social.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import im.quar.autolayout.view.AutoLinearLayout;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class MainPageActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

   // private FragmentTabHost mTabHost;
    private CustomViewPager mViewPager;
    private AutoLinearLayout socialTabLayout;
    private ImageView socialTabImgView;
    private TextView socialTabTextView;
    private int tabId =0;
    private AutoLinearLayout mineTabLayout;
    private ImageView mineTabImgView;
    private TextView mineTabTextView;

    private List<Fragment> mFragmentList;
    private LinearLayout sendTagLayout;
    private Class mClass[] = {HomeFragment.class, MineFragment.class};
    private Fragment mFragment[] = {new HomeFragment(), new MineFragment()};
    private String mTitles[] = {"搭伴", "我的"};


    private View mPanelView;
    private View mCloseButton;
    private View mIdeaButton;
    private View mPhotoButton;
    private View mWeiboButton;
    private View mLbsButton;
    private View mReviewButton;
    private View mMoreButton;

    private Animation mButtonInAnimation;
    private Animation mButtonOutAnimation;
    private Animation mButtonScaleLargeAnimation;
    private Animation mButtonScaleSmallAnimation;
    private Animation mCloseRotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
    }

    private void init() {
        initView();
        initEvent();
        initAnimation();
    }

    private void initView() {

        connect("XEb4SeUAR9OK5INscXMpeTBwF6b3ugcqxL5xchQAHbppyT/nVUusULlm1uSFR9wF8icXprLw8TG+mY0O3o46S/TIL0ph7OKF");

        socialTabLayout= (AutoLinearLayout) findViewById(R.id.socialTabLayout);
        socialTabImgView=socialTabLayout.findViewById(R.id.image);
        socialTabTextView=socialTabLayout.findViewById(R.id.title);
        mineTabLayout= (AutoLinearLayout) findViewById(R.id.mineTabLayout);
        mineTabImgView=mineTabLayout.findViewById(R.id.image);
        mineTabTextView=mineTabLayout.findViewById(R.id.title);

        socialTabLayout.setBackgroundResource(R.drawable.tab_select);
        mineTabLayout.setBackgroundResource(R.drawable.tab_nor);
        socialTabImgView.setImageResource(R.drawable.tab_counter_light);
        socialTabTextView.setText(mTitles[0]);
        mineTabImgView.setImageResource(R.drawable.tab_center_gray);
        mineTabTextView.setText(mTitles[1]);
        mineTabLayout.setBackgroundResource(R.drawable.tab_nor);
        socialTabImgView.setImageResource(R.drawable.tab_counter_light);

        mViewPager = (CustomViewPager) findViewById(R.id.view_pager);
        sendTagLayout= (LinearLayout) findViewById(R.id.sendTagLayout);
        mFragmentList = new ArrayList<Fragment>();

        for (int i = 0; i < mFragment.length; i++) {
            mFragmentList.add(mFragment[i]);

        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mPanelView = findViewById(R.id.panel);
        mCloseButton = findViewById(R.id.close);
        mIdeaButton = findViewById(R.id.idea_btn);
        mPhotoButton = findViewById(R.id.photo_btn);
        mWeiboButton = findViewById(R.id.weibo_btn);
        mLbsButton = findViewById(R.id.lbs_btn);
        mReviewButton = findViewById(R.id.review_btn);
        mMoreButton = findViewById(R.id.more_btn);



    }


    private void initEvent() {
        sendTagLayout.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);

        mIdeaButton.setOnTouchListener(this);
        mPhotoButton.setOnTouchListener(this);
        mWeiboButton.setOnTouchListener(this);
        mLbsButton.setOnTouchListener(this);
        mReviewButton.setOnTouchListener(this);
        mMoreButton.setOnTouchListener(this);
        socialTabLayout.setOnClickListener(this);
        mineTabLayout.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabId=position;
                if(position==0){
                    socialTabLayout.setBackgroundResource(R.drawable.tab_select);
                    mineTabLayout.setBackgroundResource(R.drawable.tab_nor);
                    socialTabImgView.setImageResource(R.drawable.tab_counter_light);
                    socialTabTextView.setTextColor(getResources().getColor(R.color.titleColorSelected));
                    mineTabImgView.setImageResource(R.drawable.tab_center_gray);
                    mineTabTextView.setTextColor(getResources().getColor(R.color.titleColor));
                }else if(position==1){
                    mineTabLayout.setBackgroundResource(R.drawable.tab_select);
                    socialTabLayout.setBackgroundResource(R.drawable.tab_nor);
                    socialTabImgView.setImageResource(R.drawable.tab_counter_gray);
                    socialTabTextView.setTextColor(getResources().getColor(R.color.titleColor));
                    mineTabImgView.setImageResource(R.drawable.tab_center_light);
                    mineTabTextView.setTextColor(getResources().getColor(R.color.titleColorSelected));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendTagLayout:// 添加按钮
                if (mPanelView.getVisibility() == View.GONE) {
                    openPanelView();
                }
                break;
            case R.id.close:// 关闭按钮
                if (mPanelView.getVisibility() == View.VISIBLE) {
                    closePanelView();
                }
                break;
            case R.id.socialTabLayout:
                if(tabId==1){
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.mineTabLayout:
                if(tabId==0){
                mViewPager.setCurrentItem(1);
                }
                break;
        }
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下，按钮执行放大动画
                v.startAnimation(mButtonScaleLargeAnimation);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 手指移开，按钮执行缩小动画
                v.startAnimation(mButtonScaleSmallAnimation);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 缩小动画执行完毕后，将按钮的动画清除。这里的150毫秒是缩小动画的执行时间。
                        v.clearAnimation();
                    }
                }, 150);
                break;
        }
        return true;
    }


    // 初始化动画
    private void initAnimation() {
        mButtonInAnimation = AnimationUtils.loadAnimation(this, R.anim.button_in);
        mButtonOutAnimation = AnimationUtils.loadAnimation(this, R.anim.button_out);
        mButtonScaleLargeAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale_to_large);
        mButtonScaleSmallAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale_to_small);
        mCloseRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.close_rotate);

        mButtonOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 6个按钮的退出动画执行完毕后，将面板隐藏
                mPanelView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    // 打开面板视图
    private void openPanelView() {
        mPanelView.setVisibility(View.VISIBLE);

        mIdeaButton.startAnimation(mButtonInAnimation);
        mPhotoButton.startAnimation(mButtonInAnimation);
        mWeiboButton.startAnimation(mButtonInAnimation);
        mLbsButton.startAnimation(mButtonInAnimation);
        mReviewButton.startAnimation(mButtonInAnimation);
        mMoreButton.startAnimation(mButtonInAnimation);

        mCloseButton.startAnimation(mCloseRotateAnimation);
    }

    // 关闭面板视图
    private void closePanelView() {
        // 给6个按钮添加退出动画
        mIdeaButton.startAnimation(mButtonOutAnimation);
        mPhotoButton.startAnimation(mButtonOutAnimation);
        mWeiboButton.startAnimation(mButtonOutAnimation);
        mLbsButton.startAnimation(mButtonOutAnimation);
        mReviewButton.startAnimation(mButtonOutAnimation);
        mMoreButton.startAnimation(mButtonOutAnimation);
    }
    @Override
    public void onBackPressed() {
        if (mPanelView.getVisibility() == View.VISIBLE) {
            closePanelView();
            return;
        }
        super.onBackPressed();
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
