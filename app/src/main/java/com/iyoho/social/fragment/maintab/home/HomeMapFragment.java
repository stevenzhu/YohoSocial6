package com.iyoho.social.fragment.maintab.home;

import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.iyoho.social.R;
import com.iyoho.social.base.IBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMapFragment extends IBaseFragment {
    private MapView mMapView;

    @Override
    public int initLayout() {
        return R.layout.fragment_home_map;
    }

    @Override
    public void initView(View view) {
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        BaiduMap mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //定义Maker坐标点
        List<Map<String,Object>> maps=new ArrayList<Map<String,Object>>();
        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("lat",39.963175f);
        map1.put("lng",116.400244f);
        map1.put("url","");
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("lat",45.963175f);
        map2.put("lng",100.400244f);
        map2.put("url","");
        maps.add(map1);
        maps.add(map2);

        List<OverlayOptions> overlayOptions=new ArrayList<OverlayOptions>();
        for(int x=0;x<maps.size();x++){
            Map<String,Object> map=maps.get(x);
            LatLng point = new LatLng((float)(map.get("lat")), (float)(map.get("lng")));
            //构建Marker图标
            //BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_map_marker,null);
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
            //构建MarkerOption，用于在地图上添加Marker
            // OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            OverlayOptions overlayOptionss=new MarkerOptions().position(point).flat(false).title("aaaa"+x).icon(bitmap);
            overlayOptions.add(overlayOptionss);
        }
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlays(overlayOptions);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
