package com.iyoho.social.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.iyoho.social.Entry.TestListEntry;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseActivity;
import com.iyoho.social.server.ServerCallback;
import com.iyoho.social.server.ServerInterfaces;
import com.iyoho.social.utils.FastJsonUtils;
import com.yoho.glide.GlideImageLoader;
import com.yoho.glide.GlideImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static android.R.id.list;
import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H;
import static com.iyoho.social.R.id.weatherIcon;
import static com.iyoho.social.R.layout.layout_classic_footer;

public class SwipeToLoadActivity extends IBaseActivity implements OnRefreshListener,OnLoadMoreListener {
    private SwipeToLoadLayout swipeToLoadLayout=null;
    private ListView listView;
    private MyAdapter myAdapter;
    private List<TestListEntry.ResultBean> resultBeanList;
    @Override
    public Bundle getBundle() throws Exception {
        return getBundle(SwipeToLoadActivity.class);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_swipe_to_load;
    }

    @Override
    public void initView() {
        swipeToLoadLayout= (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView= (ListView) findViewById(R.id.swipe_target);
//        LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_item,null);
//        listView.addHeaderView(linearLayout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Toast.makeText(SwipeToLoadActivity.this,resultBeanList.get(position-1).getTemperature(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void initEvent() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        //http://api.k780.com:88/?app=weather.future&weaid=1&&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json
        String url="http://api.k780.com:88";
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("app","weather.future");
        map.put("weaid","1");
        map.put("appkey","10003");
        map.put("format","json");
        map.put("sign","b59bc3ef6191eb9f747dd4e83c99f2a4");
        ServerInterfaces.getInstance().postAsync(SwipeToLoadActivity.this, url, map, new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println("--"+result);
                swipeToLoadLayout.setRefreshing(false);
                try {
                    final TestListEntry entry= FastJsonUtils.getSingleBean(result,TestListEntry.class);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            resultBeanList=new ArrayList<TestListEntry.ResultBean>();
                            resultBeanList.addAll(entry.getResult());
                            resultBeanList.addAll(entry.getResult());
                            myAdapter=new MyAdapter(resultBeanList);
                            listView.setAdapter(myAdapter);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String strMsg) {

            }
        });
    }

    @Override
    public void onLoadMore() {
swipeToLoadLayout.setLoadingMore(false);
    }

    //listview adapter
    class MyAdapter extends BaseAdapter{
        private List<TestListEntry.ResultBean> resultBeanList;
        public MyAdapter(List<TestListEntry.ResultBean> resultBeanList){
            this.resultBeanList=resultBeanList;
        }
        @Override
        public int getCount() {
            return resultBeanList.size()+1;
        }

        @Override
        public Object getItem(int position) {
            return resultBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position==0?0:1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(getItemViewType(position)==0){
                ViewPagerHolder viewHolder;
                if(convertView==null){
                    convertView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_item,parent,false);
                    viewHolder=new ViewPagerHolder(convertView);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder= (ViewPagerHolder) convertView.getTag();
                }
                //System.out.println("-----0"+resultBeanList.get(position).getWeather());
                //viewHolder.setData(resultBeanList.get(position));
            }else{
                ViewHolder viewHolder;
                if(convertView==null){
                    convertView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_swipe_to_layout_item,parent,false);
                    viewHolder=new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                System.out.println("-----0"+resultBeanList.get(position-1).getWeather());
                viewHolder.setData(resultBeanList.get(position-1));
            }

            return convertView;
        }
    }
    class ViewPagerHolder{
        public ViewPagerHolder(View convertView){

        }
    }
    class ViewHolder{
        private TextView daysTv;
        private TextView weekTv;
        private TextView cityNm;
        private TextView temperatureTv;
        private TextView weatherTv;
        private GlideImageView weatherImgView;
        public ViewHolder(View convertView){
            weatherImgView=convertView.findViewById(R.id.weatherIcon);
            cityNm=convertView.findViewById(R.id.cityNm);
            daysTv=convertView.findViewById(R.id.daysTv);
            weekTv=convertView.findViewById(R.id.weekTv);
            temperatureTv=convertView.findViewById(R.id.temperatureTv);
            weatherTv=convertView.findViewById(R.id.weatherTv);
        }
        public void setData(TestListEntry.ResultBean resultBean){
            System.out.println("-----1"+resultBean.getCitynm());
            GlideImageLoader.create(weatherImgView).load(resultBean.getWeather_icon());
            cityNm.setText(resultBean.getCitynm());
            daysTv.setText(resultBean.getDays());
            weekTv.setText(resultBean.getWeek());
            temperatureTv.setText(resultBean.getTemperature());
            weatherTv.setText(resultBean.getWeather());
        }
    }

}
