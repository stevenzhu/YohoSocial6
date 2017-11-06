package com.iyoho.social.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.iyoho.social.activity.welcome.WelcomeActivity;

import java.util.Map;

/**
 * Created by ab053167 on 2017/10/31.
 */

public class MapUtils {

    public LocationClient mLocationClient = null;
    public LocationClientOption option=null;
    private MapUtils(){};
    private volatile static MapUtils mapUtils;
    public static MapUtils getInstance(){
        if(mapUtils==null){
            synchronized (MapUtils.class){
                if(mapUtils==null){
                    mapUtils=new MapUtils();
                }
            }
        }
        return mapUtils;
    }

    public void getLocation(final Context context,final BDAbstractLocationListener listener){
        if(mLocationClient!=null&&mLocationClient.isStarted()) {
            MapUtils.getInstance().stopLocation();
        }
            mLocationClient = new LocationClient(context);

            //声明LocationClient类
            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                    //以下只列举部分获取经纬度相关（常用）的结果信息
                    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

                    double latitude = bdLocation.getLatitude();    //获取纬度信息
                    double longitude = bdLocation.getLongitude();    //获取经度信息
                    String addr = bdLocation.getAddrStr();    //获取详细地址信息
                    String country = bdLocation.getCountry();    //获取国家
                    String province = bdLocation.getProvince();    //获取省份
                    String city = bdLocation.getCity();    //获取城市
                    String district = bdLocation.getDistrict();    //获取区县
                    String street = bdLocation.getStreet();    //获取街道信息
                    float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
                    String coorType = bdLocation.getCoorType();
                    //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                    int errorCode = bdLocation.getLocType();
                    //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                    System.out.println("-------tag-----"+latitude+"-"+longitude+"-"+radius+"-"+errorCode+"-"+addr+"-"+country+"-"+province+"-"+city+"-"+district+"-"+street);
                    Log.d("-------tag-----", latitude+"-"+longitude+"-"+radius+"-"+errorCode+"-"+addr+"-"+country+"-"+province+"-"+city+"-"+district+"-"+street);
                    Toast.makeText(context,latitude+"-"+longitude+"-"+radius+"-"+errorCode+"-"+addr+"-"+country+"-"+province+"-"+city+"-"+district+"-"+street,Toast.LENGTH_LONG).show();
                    listener.onReceiveLocation(bdLocation);
                }
            });
            //注册监听函数
               option     = new LocationClientOption();
               option.setIsNeedAddress(true);
               option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
               option.setCoorType("bd09ll");
               //如果设置为0，则代表单次定位，即仅定位一次，默认为0.如果设置非0，需设置1000ms以上才有效
               //option.setScanSpan(3000);
               option.setOpenGps(true);
               option.setIgnoreKillProcess(false);
               mLocationClient.setLocOption(option);
               if(!mLocationClient.isStarted()){
                   mLocationClient.start();
               }

    }

   public void stopLocation(){
       if(mLocationClient!=null){
           mLocationClient.stop();
       }
   }
}
