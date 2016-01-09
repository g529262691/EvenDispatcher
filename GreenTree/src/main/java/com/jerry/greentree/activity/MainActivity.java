package com.jerry.greentree.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jerry.greentree.R;
import com.jerry.greentree.fragment.MainFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity
{
    private SlidingMenu mSlidingMenu;
    private FragmentManager mFragmentManger;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initSlidingMenu();

        initMainPage();
    }

    /**
     * 侧边栏初始化
     */
    private void initSlidingMenu()
    {
        mSlidingMenu = new SlidingMenu(this);

        mSlidingMenu.setMode(SlidingMenu.LEFT);

        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);

        mSlidingMenu.setShadowDrawable(R.drawable.shape_shadow);

        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

        mSlidingMenu.setFadeDegree(0.5F);

        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        mSlidingMenu.setMenu(R.layout.menu_page);
    }

    /**
     * 首页初始化
     */
    private void initMainPage()
    {
        // 默认显示首页的Fragment MainFragment

        mFragmentManger = getSupportFragmentManager();

        mTransaction = mFragmentManger.beginTransaction();
        mTransaction.replace(R.id.fl_main_content, new MainFragment());
        mTransaction.commit();

    }

    /**
     * 切换导航菜单项目
     * @param view
     */
    public void changePage(View view)
    {
        mSlidingMenu.toggle();
    }


}
