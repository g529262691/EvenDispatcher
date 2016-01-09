package com.jerry.greentree.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.BannerListAdapter;
import com.jerry.greentree.db.HotelCollect;
import com.jerry.greentree.entity.HotelDetailMessage;
import com.jerry.greentree.fragment.HotelRoomsFragment;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.activity_hotel_datail)
public class HotelDatailActivity extends FragmentActivity
{
    @ViewInject(R.id.vp_hotel_images)
    private ViewPager mVpBanner;

    @ViewInject(R.id.ll_point)
    private LinearLayout mLlPoints;

    @ViewInject(R.id.tv_hotel_name)
    private TextView mTvHotelName;

    @ViewInject(R.id.tv_hotel_addr)
    private TextView mTvHotelAddr;

    @ViewInject(R.id.iv_wifi)
    private ImageView mIvWiFi;

    @ViewInject(R.id.iv_park)
    private ImageView mIvPark;

    @ViewInject(R.id.iv_breakfast)
    private ImageView mIvBreafast;

    private String hotelId = null;

    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        initViews();

        // 获取帮助类
        networkUtil = GreenTreeNetworkUtil.getInstance();

        // 获取酒店id
        Intent intent = getIntent();
        hotelId = intent.getStringExtra("hotelId");

        loadHotelDatail();

    }

    /**
     * 收藏
     * @param view
     */
    @OnClick(R.id.iv_collect)
    public void doCollect(View view)
    {
        HotelCollect hotelCollect = null;
        try
        {
            hotelCollect =
                    dbUtils.findFirst(Selector.from(HotelCollect.class).where("hotel_id", "=", hotelId));

            // 如果收藏了，则取消收藏
            if (hotelCollect != null)
            {
                // 删除收藏的内容
                dbUtils.delete(HotelCollect.class,
                        WhereBuilder.b("hotel_id", "=", hotelId));
                Toast.makeText(HotelDatailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();

            }
            // 如果没有收藏，则收藏
            else if (hotelCollect == null)
            {
                hotelCollect = new HotelCollect();
                hotelCollect.hotelId = hotelId;
                hotelCollect.hotelName = responseData.getName();

                dbUtils.save(hotelCollect);

                Toast.makeText(HotelDatailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

        }
        catch (DbException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 分享
     * @param view
     */
    @OnClick(R.id.iv_share)
    public void doShare(View view)
    {
        // 一键分享对象
        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 设置酒店名称
        oks.setTitle(responseData.getName());

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");

        // text是分享文本，所有平台都需要这个字段
        oks.setText(responseData.getDescription());

        // 设置酒店图片
        oks.setImageUrl(responseData.getImages().get(0).getImg());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");

        // site是分享此内容的网站名称，仅在QQ空间&QQ使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间&QQ使用
        oks.setSiteUrl("http://www.baidu.com");

        // 启动分享GUI
        oks.show(HotelDatailActivity.this);
    }

    // 酒店图片内容
    private BannerListAdapter mAdapter;
    private List<HotelDetailMessage.ResponseDataEntity.ImagesEntity> mList;

    // 酒店房间
    private List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> mRoomsList;

    //视图处理
    private void initViews()
    {
        // 酒店图片显示
        mList = new ArrayList<>();
        mAdapter = new BannerListAdapter(getApplicationContext(), mList);

        // 设置viewpager适配器
        mVpBanner.setAdapter(mAdapter);

        // viewpager设置监听
        mVpBanner.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                // 切换选择圆点
                mImageViews.get(mCurrentPageNO).setSelected(false);
                mCurrentPageNO = position;
                mImageViews.get(position).setSelected(true);

            }
        });


        mRoomsList = new ArrayList<>();
    }

    /**
     * 切换酒店信息
     *
     * @param rg
     * @param checkId
     */
    @OnRadioGroupCheckedChange(R.id.rg_hotel_detail)
    public void changeHotelMessage(RadioGroup rg, int checkId)
    {
        fragmentManager = getSupportFragmentManager();
        switch (checkId)
        {
            case R.id.rb_rooms:
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_message, HotelRoomsFragment.newInstance(mRoomsList));
                break;
            case R.id.rb_desc:
                break;
            case R.id.rb_topic:
                break;
        }

    }

    private GreenTreeNetworkUtil networkUtil;
    private HotelDetailMessage.ResponseDataEntity responseData;

    // 数据处理
    private void loadHotelDatail()
    {
        Map<String, Object> params = new HashMap<>();

        params.put("hotelId", hotelId);

        // 获取酒店详情数据
        networkUtil.getHotelDetailList(params, new RequestDataCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Gson gson = new Gson();
                HotelDetailMessage message = gson.fromJson(response, HotelDetailMessage.class);

                // 获取酒店图片
                if (message != null && message.getResponseData() != null)
                {
                    // 先获取酒店图片
                    mList.addAll(message.getResponseData().getImages());
                    Log.d("gan", "onSuccess() returned: " + message.getResponseData().getImages());
                    mAdapter.notifyDataSetChanged();

                    // 添加导航圆点
                    addNavPoint();

                    // 酒店信息

                    // 酒店名称
                    mTvHotelName.setText(message.getResponseData().getName());
                    Log.d("gan", "onSuccess() returned: " + message.getResponseData().getName());

                    // 酒店地址
                    mTvHotelAddr.setText(message.getResponseData().getAddress());

                    // 提供的服务
                    List<String> services = message.getResponseData().getService();

                    // 显示酒店服务
                    for (String service : services)
                    {
                        if ("41".equals(service))
                        {
                            mIvWiFi.setVisibility(View.VISIBLE);
                        } else if ("51".equals(service))
                        {
                            mIvPark.setVisibility(View.VISIBLE);
                        } else if ("91".equals(service))
                        {
                            mIvBreafast.setVisibility(View.VISIBLE);
                        }

                    }

                    // 获取酒店房间数据
                    mRoomsList.addAll(message.getResponseData().getRooms());

                    showRooms();
                }
            }

            @Override
            public void onFailure(HttpException error)
            {

            }
        });

    }

    private FragmentManager fragmentManager;

    /**
     * 显示房间
     */
    private void showRooms()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_message, HotelRoomsFragment.newInstance(mRoomsList))
                .commit();
    }

    // 用于保存已经添加的圆点
    private List<ImageView> mImageViews = new ArrayList<>();

    // 当前选择页
    private int mCurrentPageNO = 0;

    /**
     * 添加圆点
     */
    private void addNavPoint()
    {
        if (mList == null || mList.isEmpty())
        {
            return;
        }

        ImageView addedImageView = null;

        // 布局参数
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.rightMargin = 10;

        for (int i = 0; i < mList.size(); i++)
        {
            addedImageView = new ImageView(this);
            // 设置背景
            addedImageView.setBackgroundResource(R.drawable.selector_nav_points);

            // 默认不选择
            addedImageView.setSelected(false);

            // 设置布局参数
            addedImageView.setLayoutParams(params);

            // 添加到布局中
            mLlPoints.addView(addedImageView);

            // 添加到集合中
            mImageViews.add(addedImageView);
        }

        // 默认选择第一个
        mImageViews.get(0).setSelected(true);
    }
}
