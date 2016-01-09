package com.jerry.greentree.util;

import com.lidroid.xutils.exception.HttpException;

/**
 * Created by jerry on 15/11/2.
 */
public interface RequestDataCallback
{
    public void onSuccess(String response);
    public void onFailure(HttpException error);
}
