package com.jerry.greentree.db;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

/**
 * Created by Gan on 2016/1/5.
 */
@Table(name = "t_hotel_collect")
public class HotelCollect
{
    @Id(column = "_id")
    public int id;

    @Column(column = "hotel_id")
    @NotNull
    @Unique
    public String hotelId;

    @Column(column = "hotel_name")
    @NotNull
    @Unique
    public String hotelName;

    @Column(column = "image_url")
    @NotNull
    public String hotelImageUrl;
}
