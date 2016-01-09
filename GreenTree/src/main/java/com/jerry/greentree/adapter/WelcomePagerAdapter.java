package com.jerry.greentree.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jerry.greentree.activity.MainActivity;
import com.jerry.greentree.activity.WelcomeActivity;

/**
 * Created by jerry on 15/11/2.
 */
public class WelcomePagerAdapter extends PagerAdapter
{
    private int[] mImageIds;
    private Context mContext;

    public WelcomePagerAdapter(int[] mImageIds, Context mContext)
    {
        this.mImageIds = mImageIds;
        this.mContext = mContext;
    }

    @Override
    public int getCount()
    {
        return mImageIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mImageIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        if (position == mImageIds.length - 1)
        {
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // 设置为非第一次运行
                    SharedPreferences sp = mContext
                            .getSharedPreferences("greentree", Context.MODE_PRIVATE);
                    SharedPreferences.Editor et = sp.edit();
                    et.putBoolean("isFirstRun",false);
                    et.commit();

                    // 跳转到首页
                    Intent intent = new Intent();
                    intent.setClass(mContext, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(intent);

                    ((WelcomeActivity)mContext).finish();
                }
            });
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
