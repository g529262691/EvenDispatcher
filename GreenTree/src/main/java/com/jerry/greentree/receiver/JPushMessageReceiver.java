package com.jerry.greentree.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jerry.greentree.activity.HotelDatailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Gan on 2016/1/5.
 */
public class JPushMessageReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        Bundle bundle = intent.getExtras();

        // 处理通知消息
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action))
        {

            // 获取酒店id
            String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);

            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);

                int hotelId = jsonObject.getInt("hotelId");

                // 打开酒店详情的Activity
                Intent hotelDetailIntent = new Intent();
                hotelDetailIntent.setClass(context, HotelDatailActivity.class);
                hotelDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                hotelDetailIntent.putExtra("hotelId", hotelId);
                context.startActivity(hotelDetailIntent);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action))
        {
            // 获取酒店id
            String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);

            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);

                int hotelId = jsonObject.getInt("hotelId");

                // 打开酒店详情的Activity
                Intent hotelDetailIntent = new Intent();
                hotelDetailIntent.setClass(context, HotelDatailActivity.class);
                hotelDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                hotelDetailIntent.putExtra("hotelId", hotelId + "");
                context.startActivity(hotelDetailIntent);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        // 处理自定义消息
    }
}
