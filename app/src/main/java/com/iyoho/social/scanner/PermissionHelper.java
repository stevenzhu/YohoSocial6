package com.iyoho.social.scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionHelper {
    private Context mContext;
    public static final String PACKAGE = "package:";

    public PermissionHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 判断权限集合
     *
     * @param permissions 检测权限的集合
     * @return 权限已全部获取返回true，未全部获取返回false
     */
    public boolean checkPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断权限是否获取
     *
     * @param permission 权限名称
     * @return 已授权返回true，未授权返回false
     */
    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     *
     * @param resultCode
     */
    public void permissionsCheck(String permission, int resultCode) {
        // 注意这里要使用shouldShowRequestPermissionRationale而不要使用requestPermission方法
        // 因为requestPermissions方法会显示不在询问按钮
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
            //如果用户以前拒绝过改权限申请，则给用户提示
            showMissingPermissionDialog();
        } else {
            //进行权限请求
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, resultCode);
        }
    }

    public boolean permissionsGranted(int[] grantResults) {
        if (grantResults != null && grantResults.length > 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public void permissionsRequest(String[] permissions, int resultCode) {
        // 用shouldShowRequestPermissionRationale,requestPermissions方法会显示不在询问按钮
        boolean hasCanceledBefore = hasCanceledBefore(permissions);
        if (hasCanceledBefore) {
            showMissingPermissionDialog();
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, permissions, resultCode);
        }
    }

    /**
     * 判断是否之前拒绝过权限申请
     */
    private boolean hasCanceledBefore(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
                return true;
            }
        }
        return false;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog = builder.create();

        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }

    // 启动应用的设置
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE + mContext.getPackageName()));
        mContext.startActivity(intent);
    }
}
