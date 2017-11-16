package com.iyoho.social.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iyoho.social.R;
import com.iyoho.social.base.IBaseFragment;
import com.iyoho.social.view.CustomViewPager;
import java.util.ArrayList;
import java.util.List;

public class SocialTabFragment extends Fragment implements View.OnClickListener{
    public static List<Fragment> fragments;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(initLayout(), container, false);
       initView(view);
       initData();
        initEvent();
        return view;
    }


    public int initLayout() {
        return R.layout.fragment_social_tab;
    }


    public void initView(View view) {
        fragments = new ArrayList<>();
        TopicFragment topicFragment = new TopicFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putString("socialType", "topic");
        topicFragment.setArguments(bundle0);
        fragments.add(topicFragment);

        FindFragment findFragment = new FindFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("socialType", "find");
        findFragment.setArguments(bundle2);
        fragments.add(findFragment);

        CircleFragment circleFragment = new CircleFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("socialType", "circle");
        circleFragment.setArguments(bundle1);
        fragments.add(circleFragment);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mViewPager.setOffscreenPageLimit(3);
    }


    public void initData() {

    }


    public void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
