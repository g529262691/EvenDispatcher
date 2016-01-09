package com.jerry.greentree.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.greentree.R;
import com.jerry.greentree.activity.HotelKeywordSelectActivity;
import com.jerry.greentree.activity.HotelSelectActivity;
import com.jerry.greentree.view.HotelSelectItemView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class HotelSelectFragment extends Fragment
{
    @ViewInject(R.id.item_city)
    private HotelSelectItemView item_city;

    @ViewInject(R.id.item_keyword)
    private HotelSelectItemView item_keyword;

    @ViewInject(R.id.item_time)
    private HotelSelectItemView item_time;

    @ViewInject(R.id.item_days)
    private HotelSelectItemView item_days;

    public HotelSelectFragment()
    {

    }

    public static HotelSelectFragment newInstance()
    {
        HotelSelectFragment fragment = new HotelSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }

//        开启一个广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.select");

        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    /**
     * 次广播接收器用于接收查询条件
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_hotel_select, container, false);
        ViewUtils.inject(this, view);

        initViews();

        return view;
    }

    /**
     * 初始化视图
     */
    private void initViews()
    {
        item_city.setContent(getString(R.string.city));
        item_keyword.setContent(getString(R.string.key));
        item_time.setContent(getString(R.string.time));
        item_days.setContent(getString(R.string.day));
    }

    @OnClick(R.id.item_keyword)
    public void selectKeyWord(View view)
    {
        Intent intent = new Intent(getActivity(),HotelKeywordSelectActivity.class);
        intent.putExtra("cityId",226);
        startActivity(intent);
        // 返回结果在Fragment中的onActivityResult方法中获取
//        startActivityForResult(intent,100);

        // 返回结果在Activity中的onActivityResult方法中获取
//        getActivity().startActivity(intent);
    }

    /**
     * 搜索酒店
     *
     * @param view
     */
    @OnClick(R.id.btn_hotel_select)
    public void selectHotel(View view)
    {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(getActivity(), HotelSelectActivity.class);
        getActivity().startActivity(intent);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
