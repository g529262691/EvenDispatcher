package com.jerry.greentree.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.entity.Banner;
import com.jerry.greentree.entity.HotelDetailMessage;

import java.util.List;

/**
 * Created by jerry on 15/11/2.
 */
public class BannerListAdapter<T> extends PagerAdapter
{
    private List<T> mBanners;
    private Context mContext;

    public BannerListAdapter(Context mContext, List<T> mBanners)
    {
        this.mBanners = mBanners;
        this.mContext = mContext;
    }

    @Override
    public int getCount()
    {
        return mBanners.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        T bannerList = mBanners.get(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        String imageUrl = null;

        // 首页的图片
        if (bannerList instanceof Banner.ResponseData.BannerList)
        {
            Banner.ResponseData.BannerList banner = (Banner.ResponseData.BannerList) bannerList;

            imageUrl = banner.getBannerUrl();
        }
        // 酒店详情页面图片
        else if (bannerList instanceof HotelDetailMessage.ResponseDataEntity.ImagesEntity)
        {
            HotelDetailMessage.ResponseDataEntity.ImagesEntity entity =
                    (HotelDetailMessage.ResponseDataEntity.ImagesEntity) bannerList;

            imageUrl = entity.getBigimg();
        }

        // 使用UIL加载图片
        GreenTreeApplication.getApp()
                .getImageLoader()
                .displayImage(imageUrl, imageView,
                        GreenTreeApplication.getApp().getImageOptions());


        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
