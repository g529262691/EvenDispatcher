package com.jerry.greentree.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jerry.greentree.R;
import com.jerry.greentree.adapter.RoomListAdapter;
import com.jerry.greentree.entity.HotelDetailMessage;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomsFragment extends Fragment
{
    @ViewInject(R.id.lv_rooms)
    private ListView mLvRooms;
    private List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> rooms;

    public HotelRoomsFragment()
    {
    }

    public static HotelRoomsFragment
                newInstance(List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> rooms)
    {
        HotelRoomsFragment fragment = new HotelRoomsFragment();
        Bundle args = new Bundle();

        args.putSerializable("rooms",(ArrayList)rooms);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null)
        {
            rooms = (List<HotelDetailMessage.ResponseDataEntity.RoomsEntity>) bundle.getSerializable("rooms");
        }
    }

    /**
     * 创建布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hotel_rooms, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    private RoomListAdapter roomListAdapter;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        roomListAdapter = new RoomListAdapter(getActivity(),rooms);
        mLvRooms.setAdapter(roomListAdapter);

//        setHeight();
    }

    private void setHeight()
    {
        int listViewheight = 0;
        int count = roomListAdapter.getCount();
        for (int i = 0; i < count; i++)
        {
            View temp = roomListAdapter.getView(i,null,mLvRooms);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
