package com.jerry.greentree.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.HotelListAdapter;
import com.jerry.greentree.entity.Hotel;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_hotel_select)
public class HotelSelectActivity extends Activity
{
    @ViewInject(R.id.plv_hotel_list)
    private PullToRefreshListView mPtrLvHotelList;

    // 查询类型
    private int mSelectType = 0;

    private ProgressDialog mProgressDialog;

    // 适配器对象
    private HotelListAdapter<Hotel.ResponseDataEntity.ItemsEntity> mHotelListAdapter;

    // 酒店集合
    private List<Hotel.ResponseDataEntity.ItemsEntity> mHotelList;

    private GreenTreeNetworkUtil mNetWorkUtils;
    private int mCurrentPageNo = 1;

    private int mPriceCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        mNetWorkUtils = GreenTreeNetworkUtil.getInstance();

        initViews();

        loadData();
    }

    /**
     * 初始化视图
     */
    private void initViews()
    {
        // 显示正在加载
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在加载当中...");
        mProgressDialog.show();

        // 进度条不能取消
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        // 适配器
        mHotelList = new ArrayList<>();
        mHotelListAdapter =
                new HotelListAdapter<Hotel.ResponseDataEntity.ItemsEntity>(getApplicationContext(), mHotelList);

        mPtrLvHotelList.setAdapter(mHotelListAdapter);

        // item之间的间隙
        mPtrLvHotelList.getRefreshableView().setDivider(null);
        mPtrLvHotelList.getRefreshableView().setSelector(android.R.color.transparent);

        // 同时支持上下拉刷新，监听则用listener2
        mPtrLvHotelList.setMode(PullToRefreshBase.Mode.BOTH);

        // 设置事件

        // 设置PullToRefreshListView的监听
        mPtrLvHotelList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            // 处理下拉刷新事件
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
//                mHotelList.clear();

                // 加载第一页
                mCurrentPageNo = 1;

                loadData();
            }

            // 处理上拉刷新事件
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                mCurrentPageNo++;
                loadData();
            }
        });

        //        // 如果只是需要上拉或者下拉刷新，则只需要设置为OnRefreshListener
//        mPtrLvHotelList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        mPtrLvHotelList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
//        {
//            // 无论是下拉，还是上拉，都只会调用此方法
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView)
//            {
//
//            }
//        });


    }

    // 事件设置

    /**
     * 回到首页
     */
    @OnClick(R.id.iv_home)
    private void goHome(View view)
    {
        this.finish();

    }

    /**
     * 切换酒店类型
     */
    @OnRadioGroupCheckedChange(R.id.rg_select_type)
    private void changeSelectType(RadioGroup radioGroup, int checkedId)
    {
        mCurrentPageNo = 1;
        mHotelList.clear();
        mPtrLvHotelList.getRefreshableView().setSelection(0);

        switch (checkedId)
        {
            case R.id.rb_referee:
                mSelectType = 0;
                break;
            case R.id.rb_distance:
                mSelectType = 1;
                break;
            case R.id.rb_price:
                RadioButton radioBtnPrice = (RadioButton) findViewById(R.id.rb_price);
                mPriceCount++;
                if (mPriceCount % 2 == 0)
                {
                    mSelectType = 2;
                    radioBtnPrice.setText(R.string.price_s);
                } else
                {
                    mSelectType = 3;
                    radioBtnPrice.setText(R.string.price_x);
                }
                break;
            case R.id.rb_score:
                mSelectType = 4;
                break;
            case R.id.rb_filter:
                break;
        }

        loadData();

    }

    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 加载数据
     */
    private void loadData()
    {
        params.clear();

        // 设置参数
        params.put("pagesize", 10);
        params.put("ordertype", mSelectType);
        params.put("pageindex", 1);
        // 访问网络，获取酒店数据

        // 获取酒店数据
        mNetWorkUtils.getHotelList(params, new RequestDataCallback()
        {
            // 成功获取酒店数据
            @Override
            public void onSuccess(String response)
            {
                // 解析酒店数据
                Gson gson = new Gson();
                Hotel hotel = gson.fromJson(response, Hotel.class);
                if (hotel != null && hotel.getResponseData() !=null){

                    // 添加酒店数据
                    mHotelList.addAll(hotel.getResponseData().getItems());

                    // 属性列表视图
                    mHotelListAdapter.notifyDataSetChanged();
                }

                mPtrLvHotelList.onRefreshComplete();

                //  取消progressdialog
                if (mProgressDialog != null && mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(HttpException error)
            {
                mPtrLvHotelList.onRefreshComplete();

                //  取消progressdialog
                if (mProgressDialog != null && mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    // 生命周期方法
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
