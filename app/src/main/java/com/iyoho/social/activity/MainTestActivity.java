package com.iyoho.social.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoho.social.Entry.MessageEvent;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.utils.EventBusUtils;
import com.iyoho.social.utils.JPushUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

public class MainTestActivity extends IBaseActivity {
    private TextView swipeToLayoutTextView;
    private TextView intoOKhttpTextView;
    private TextView intoScannerTextView;
    private TextView shareSMSTextView;
    private TextView uMengTextView;
    @Override
    public Bundle getBundle() throws Exception {
        return getBundle(MainTestActivity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_test_main;
    }
    static{
        System.loadLibrary("weibosdkcore");
    }
    @Override
    public void initView() {
        intoScannerTextView= (TextView) findViewById(R.id.intoScannerTextView);
        intoOKhttpTextView= (TextView) findViewById(R.id.intoOKhttpTextView);
        swipeToLayoutTextView= (TextView) findViewById(R.id.swipeToLayoutTextView);
        shareSMSTextView= (TextView) findViewById(R.id.shareSMSTextView);
        uMengTextView= (TextView) findViewById(R.id.uMengTextView);
    }

    @Override
    public void initEvent() {
        EventBusUtils.register(this);
        /*設置推送Alias*/
        JPushUtils.getInstance().setAlias(this,"steven18801103745");
        intoScannerTextView.setOnClickListener(this);
        intoOKhttpTextView.setOnClickListener(this);
        swipeToLayoutTextView.setOnClickListener(this);
        shareSMSTextView.setOnClickListener(this);
        uMengTextView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){
     if(event!=null&&event.getmClass()==MainTestActivity.class){
         System.out.println("--------MainActivity"+event.getMessage1());
     }
     };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.intoOKhttpTextView:
              EventBus1Activity.start(MainTestActivity.this,SelectAlbumActivity.class,null);
              break;
          case R.id.intoScannerTextView:
              EventBus1Activity.start(MainTestActivity.this,EventBus2Activity.class,null);
              break;
          case R.id.swipeToLayoutTextView:
              EventBus1Activity.start(MainTestActivity.this,SwipeToLoadActivity.class,null);
              break;
          case R.id.uMengTextView:
//              UMWeb web = new UMWeb("http://wwwfdsfsdf.bafdsfdsfidu.cfsdfsdom");
//              web.setTitle("fsdfafdsfsfsd");
//              web.setDescription("3esfsdferfw");
//              UMImage image = new UMImage(MainActivity.this, "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=92e00c9b8f5494ee8722081f15ce87c3/29381f30e924b899c83ff41c6d061d950a7bf697.jpg");
//              image.compressStyle = UMImage.CompressStyle.SCALE;
//              web.setThumb(image);
//              new ShareAction(MainActivity.this).withMedia(web).setPlatform(SINA.toSnsPlatform().mPlatform).setCallback(listener).share();
              ShareAction mShareAction= new ShareAction(MainTestActivity.this).setDisplayList(
                      SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)

                      .setShareboardclickCallback(new ShareBoardlistener() {
                          @Override
                          public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                  UMWeb web = new UMWeb("http://www.baidu.com");
                                  web.setTitle("来自分享面板标题");
                                  web.setDescription("来自分享面板内容");
//                              UMImage image = new UMImage(MainActivity.this, "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=92e00c9b8f5494ee8722081f15ce87c3/29381f30e924b899c83ff41c6d061d950a7bf697.jpg");
//                       image.compressStyle = UMImage.CompressStyle.SCALE;
//                       web.setThumb(image);
                                  new ShareAction(MainTestActivity.this).withMedia(web)
                                          .setPlatform(share_media)
                                          .setCallback(new CustomShareListener(MainTestActivity.this))
                                          .share();
                          }
                      });

              ShareBoardConfig config = new ShareBoardConfig();
              config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
              config.setIndicatorVisibility(false);
              config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
              mShareAction.open(config);



              break;
      }
    }

    class CustomShareListener implements UMShareListener {

        private WeakReference<MainTestActivity> mActivity;

        private CustomShareListener(MainTestActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    //    UMShareListener listener = new UMShareListener() {
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//            Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onResult(SHARE_MEDIA share_media) {
//            Toast.makeText(MainActivity.this, "onResult", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//            Toast.makeText(MainActivity.this, "error=" + throwable.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA share_media) {
//            Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_LONG).show();
//        }
//    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
        //System.out.println("----media="+media+"-----isCompleted="+isCompleted);
        //boolean isInstallQQ=UMShareAPI.get(MainActivity.this).isInstall(MainActivity.this,SHARE_MEDIA.QQ);
        // boolean isInstallQZONE=UMShareAPI.get(MainActivity.this).isInstall(MainActivity.this,SHARE_MEDIA.QZONE);
        // if(!isInstallQQ||!isInstallQZONE){
       // if(requestCode==1&&resultCode==2){
            String media=data.getStringExtra("media");
            boolean isCompleted=data.getBooleanExtra("isCompleted",false);
            System.out.println("----media="+media+"-----isCompleted="+isCompleted);
       // }
        // }else{

        // }

    }

}
