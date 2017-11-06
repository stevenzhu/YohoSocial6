package com.iyoho.social.base;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ab053167 on 2017/9/12.
 */

public abstract class IBaseFragment extends Fragment implements View.OnClickListener {
    private static Context mContext;
    @Override
    public void onAttach(Context context) {
        this.mContext=context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
              View  view = inflater.inflate(initLayout(), container, false);
                initView(view);
                initData();
                initEvent();
        return view;
    }

    public abstract  int initLayout();
    public abstract  void initView(View view);
    public abstract  void initEvent();
    public abstract  void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
