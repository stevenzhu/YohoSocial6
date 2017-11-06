package com.iyoho.social.utils;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static social.iyoho.com.imgselectlibrary.view.ImageSelectorActivity.REQUEST_CAMERA;

/**
 * Created by ab053167 on 2017/9/13.
 */

public class PermissionDispatcherHelper {
    //存储卡
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    private static final String[] EXTERNAL_STORAGE = new String[] {"android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
    //照相
    private static final int REQUEST_CAMERA = 1;
    private static final String[] CAMERA = new String[] {"android.permission.CAMERA"};
    //联系人
    private static final int REQUEST_CONTACTS = 2;
    private static final String[] CONTACTS = new String[] {"android.permission.READ_CONTACTS","android.permission.WRITE_CONTACTS","android.permission.GET_ACCOUNTS"};
    //电话
    private static final int REQUEST_PHONE_SMS = 3;
    private static final String[] PHONE_SMS = new String[] {"android.permission.READ_PHONE_STATE","android.permission.CALL_PHONE","android.permission.ADD_VOICEMAIL","android.permission.USE_SIP","android.permission.PROCESS_OUTGOING_CALLS","android.permission.SEND_SMS","android.permission.RECEIVE_SMS","android.permission.READ_SMS"};
    //位置
    private static final int REQUEST_LOCATION = 4;
    private static final String[] LOCATION = new String[] {"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"};
    //传感器
    private static final int REQUEST_SENSORS = 5;
    private static final String[] SENSORS = new String[] {"android.permission.BODY_SENSORS"};
    //日历
    private static final int REQUEST_CALENDAR = 6;
    private static final String[] CALENDAR = new String[] {"android.permission.READ_CALENDAR","android.permission.WRITE_CALENDAR"};

    private static final int REQUEST_EXTERNAL_STORAGE_CAMERA = 7;
    private static final String[] EXTERNAL_STORAGE_CAMERA = new String[] {"android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE","android.permission.CAMERA"};
    private static final int REQUEST_CONTACTS_PHONE_SMS = 8;
    private static final String[] CONTACTS_PHONE_SMS = new String[] {"android.permission.READ_CONTACTS","android.permission.WRITE_CONTACTS","android.permission.GET_ACCOUNTS","android.permission.READ_PHONE_STATE","android.permission.CALL_PHONE","android.permission.ADD_VOICEMAIL","android.permission.USE_SIP","android.permission.PROCESS_OUTGOING_CALLS","android.permission.SEND_SMS","android.permission.RECEIVE_SMS","android.permission.READ_SMS"};
    /////////Singleton//////////////////////
    private static volatile PermissionDispatcherHelper instance = null;

    public static PermissionDispatcherHelper getInstance() {

        if (instance == null) {
            synchronized (PermissionDispatcherHelper.class) {
                if (instance == null) {
                    instance = new PermissionDispatcherHelper();
                }
            }
        }

        return instance;
    }

    private PermissionDispatcherHelper() {
    }


    /*OnPermissionListener
      * */
    public interface OnPermissionListener {
        void onPermissionCompleted();
    }
    private static OnPermissionListener onPermissionListener;
    public void setOnPermissionListener(OnPermissionListener listener) {
        this.onPermissionListener = listener;
    }



    public void checkExternalStorage(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, EXTERNAL_STORAGE)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    public void checkCamera(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, CAMERA)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, CAMERA, REQUEST_CAMERA);
        }
    }
    public void checkContacts(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, CONTACTS)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, CONTACTS, REQUEST_CONTACTS);
        }
    }
    public void checkPhoneSms(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, PHONE_SMS)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, PHONE_SMS, REQUEST_PHONE_SMS);
        }
    }
    public void checkLocation(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, LOCATION)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, LOCATION, REQUEST_LOCATION);
        }
    }
    public void checkSensors(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, SENSORS)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, SENSORS, REQUEST_SENSORS);
        }
    }
    public void checkCalendar(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, CALENDAR)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, CALENDAR, REQUEST_CALENDAR);
        }
    }
    public void checkExternalStorageCamera(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, EXTERNAL_STORAGE_CAMERA)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, EXTERNAL_STORAGE_CAMERA, REQUEST_EXTERNAL_STORAGE_CAMERA);
        }
    }
    public void checkContactsPhoneSms(Activity context, OnPermissionListener onPermissionListener) {
        setOnPermissionListener(onPermissionListener);
        if (PermissionUtils.hasSelfPermissions(context, CONTACTS_PHONE_SMS)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        } else {
            ActivityCompat.requestPermissions(context, CONTACTS_PHONE_SMS, REQUEST_CONTACTS_PHONE_SMS);
        }
    }
    public static void onRequestPermissionsResult(Activity context, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, EXTERNAL_STORAGE)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_CAMERA:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, CAMERA)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_CONTACTS:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, CONTACTS)) {
                return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_PHONE_SMS:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, PHONE_SMS)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_LOCATION:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, LOCATION)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_CALENDAR:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, CALENDAR)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_EXTERNAL_STORAGE_CAMERA:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, EXTERNAL_STORAGE_CAMERA)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            case REQUEST_CONTACTS_PHONE_SMS:
                if (PermissionUtils.getTargetSdkVersion(context) < 23 && !PermissionUtils.hasSelfPermissions(context, CONTACTS_PHONE_SMS)) {
                    return;
                }
                verifyPermissions(grantResults);
                break;
            default:
                break;
        }
    }
    public static void verifyPermissions(int[] grantResults){
        if (PermissionUtils.verifyPermissions(grantResults)) {
            if(onPermissionListener!=null){
                onPermissionListener.onPermissionCompleted();
            }
        }
    }
}

