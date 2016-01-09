package com.jerry.greentree.activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.jerry.greentree.R;
import com.jerry.greentree.adapter.WelcomePagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by jerry on 15/11/2.
 */
@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends Activity
{
    @ViewInject(R.id.vp_welcome)
    private ViewPager mVPWelcome;

    @ViewInject(R.id.iv_welcome_loading)
    private ImageView mIvLoading;

    private boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        initDatas();

        initViews();
    }

    /**
     * 初始化数据
     */
    private void initDatas()
    {
        SharedPreferences sp = getSharedPreferences("greentree", Context.MODE_PRIVATE);
        isFirstRun = sp.getBoolean("isFirstRun", true);
    }

    // 数据源资源ID数组
    private int[] mImageIds = {R.drawable.g1, R.drawable.g2, R.drawable.g3};

    private WelcomePagerAdapter mAdapter;

    /**
     * 初始化视图
     */
    private void initViews()
    {
        // 第一次访问
        if (isFirstRun)
        {
            // 先显示ViewPager
            mVPWelcome.setVisibility(ViewPager.VISIBLE);

            mAdapter = new WelcomePagerAdapter(mImageIds,this);

            mVPWelcome.setAdapter(mAdapter);

            mVPWelcome.setCurrentItem(0);
        }
        else
        {
            mIvLoading.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(1,3000);
        }
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);

            WelcomeActivity.this.finish();
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mHandler.removeMessages(1);
    }
}
