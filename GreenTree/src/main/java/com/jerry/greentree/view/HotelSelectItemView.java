package com.jerry.greentree.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.greentree.R;

import org.w3c.dom.Text;

/**
 * Created by jerry on 15/11/3.
 */
public class HotelSelectItemView extends LinearLayout
{
    private ImageView mIvHotel = null;
    private TextView mTvTitle = null;
    private TextView mTvContent = null;

    public HotelSelectItemView(Context context)
    {
        this(context,null);
    }

    public HotelSelectItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);

        inflater.inflate(R.layout.select_item_layout,this);

        mIvHotel = (ImageView) findViewById(R.id.iv_item);

        mTvTitle = (TextView) findViewById(R.id.tv_tip);

        mTvContent = (TextView) findViewById(R.id.tv_content);

        initAttribute(context, attrs);
    }

    /**
     * 初始化属性
     * @param context
     * @param attrs
     */
    private void initAttribute(Context context, AttributeSet attrs)
    {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.HotelSelect);

        // 获取图片
        Drawable icon =
                array.getDrawable(R.styleable.HotelSelect_hotel_icon);

        mIvHotel.setImageDrawable(icon);


        // 获取文本
        String title =
                array.getString(R.styleable.HotelSelect_hotel_title);

        mTvTitle.setText(title);


        array.recycle();
    }

    /**
     * 设置条件
     * @param content
     */
    public void setContent(String content)
    {
        mTvContent.setText(content);
    }

}
