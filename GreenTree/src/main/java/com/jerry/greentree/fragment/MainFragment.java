package com.jerry.greentree.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.activity.MainActivity;
import com.jerry.greentree.adapter.BannerListAdapter;
import com.jerry.greentree.entity.Banner;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 15/11/2.
 */
public class MainFragment extends Fragment
{
    @ViewInject(R.id.vp_ad_banner)
    private ViewPager mVpBanner;

    @ViewInject(R.id.ll_point)
    private LinearLayout mLLPoint;

    @ViewInject(R.id.rl_main_banner)
    private RelativeLayout mRlMainBanner;

    private BannerListAdapter mAdapter = null;

    private List<Banner.ResponseData.BannerList> mBanners =
                    new ArrayList<Banner.ResponseData.BannerList>();

    private FragmentManager mFragmentManager = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.frag_main,null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        initViews();

        loadBanner();
    }

    /**
     * 加载广告条
     */
    private void loadBanner()
    {
        GreenTreeNetworkUtil
                .getInstance().getBannaer(new RequestDataCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Gson gson = new Gson();
                Banner banner = gson.fromJson(response, Banner.class);

                if (banner != null)
                {
                    mBanners.addAll(banner.getResponseData().getBannerList());

                    addPoints();

                    timerChangeBanner();
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException error)
            {

            }
        });
    }

    /**
     * 定时切换
     */
    private void timerChangeBanner()
    {
        // mVpBanner选择对应页

        // 圆点切换

        mHander.sendEmptyMessageDelayed(1,5000);
    }

    private int currentPageNO = 0;

    private Handler mHander = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            currentPageNO = ++currentPageNO % mImageViews.size();

            mCurrentImageView.setSelected(false);
            mCurrentImageView = mImageViews.get(currentPageNO);
            mCurrentImageView.setSelected(true);

            // mVpBanner选择对应页
            // 圆点切换
            mVpBanner.setCurrentItem(currentPageNO);

            mHander.sendEmptyMessageDelayed(1,5000);
        }
    };

    private ImageView mCurrentImageView;
    private List<ImageView> mImageViews;

    /**
     * 添加圆点
     */
    private void addPoints()
    {
        if (mBanners == null || mBanners.isEmpty())
        {
            return;
        }

        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.rightMargin = 15;

        int bannerSize = mBanners.size();
        for (int i = 0; i < bannerSize; i++)
        {
            mCurrentImageView = new ImageView(getActivity());
            mCurrentImageView.setLayoutParams(layoutParams);

            mCurrentImageView.setSelected(false);

            mCurrentImageView.setImageResource(R.drawable.selector_nav_points);

            mImageViews.add(mCurrentImageView);

            mLLPoint.addView(mCurrentImageView,i);
        }

        mCurrentImageView = mImageViews.get(0);
        mCurrentImageView.setSelected(true);
    }

    /**
     * 初始化视图 ViewPager 导航 设置ViewPager高度
     */
    private void initViews()
    {
        mImageViews = new ArrayList<ImageView>();

        mAdapter = new BannerListAdapter(getActivity(),mBanners);

        // 获取屏幕显示尺寸对象
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // 动态设置ViewPager高度
        ViewGroup.LayoutParams layoutParams = mRlMainBanner.getLayoutParams();
        layoutParams.height = metrics.heightPixels / 3;
        mRlMainBanner.setLayoutParams(layoutParams);

        mVpBanner.setAdapter(mAdapter);

        mVpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (mCurrentImageView == null)
                {
                    return;
                }

                currentPageNO = position;

                mCurrentImageView.setSelected(false);
                mCurrentImageView = mImageViews.get(position);
                mCurrentImageView.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


        // 初始化显示酒店搜索
        mFragmentManager = getChildFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content,HotelSelectFragment.newInstance())
                .commit();

    }


    @OnRadioGroupCheckedChange(R.id.rg_main)
    public void changeSelect(RadioGroup radioGroup, int checkId)
    {

        switch (checkId)
        {
            case R.id.rb_hotel_select:
                mFragmentManager.beginTransaction()
                        .replace(R.id.fl_main_content,HotelSelectFragment.newInstance())
                        .commit();
                break;
            case R.id.rb_hotel_collect:
                mFragmentManager.beginTransaction()
                        .replace(R.id.fl_main_content,HotelCollectFragment.newInstance())
                        .commit();
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        mHander.removeMessages(1);
    }
}
