package com.jerry.greentree.util;

import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.constant.Urls;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Map;
import java.util.Set;

/**
 * Created by jerry on 15/11/2.
 */
public class GreenTreeNetworkUtil
{
    private static GreenTreeNetworkUtil util = new GreenTreeNetworkUtil();
    private HttpUtils httpUtils;

    private GreenTreeNetworkUtil()
    {
        httpUtils = GreenTreeApplication.getApp().getHttpUtils();
    }

    public static GreenTreeNetworkUtil getInstance()
    {
        return util;
    }

    /**
     * 获取首页广告信息
     *
     * @param callback
     */
    public void getBannaer(RequestDataCallback callback)
    {
        doGet(Urls.BANNER_LIST, null, callback);
    }

    /**
     * 获取酒店集合
     * @param params
     * @param callback
     */
    public void getHotelList(Map<String, Object> params, RequestDataCallback callback)
    {
        doPost(Urls.Hotel_LIST, params, callback);
    }

    /**
     * 获取酒店详情
     * @param params
     * @param callback
     */
    public void getHotelDetailList(Map<String, Object> params, RequestDataCallback callback)
    {
        doPost(Urls.HOTEL_DETAIL, params, callback);
    }

    /**
     * post请求
     *
     * @param requestUrl
     * @param params
     */
    private void doPost(String requestUrl, Map<String, Object> params, final RequestDataCallback callback)
    {
        RequestParams requestParams = new RequestParams();

        if (params != null)
        {
            Set<Map.Entry<String, Object>> set = params.entrySet();

            for (Map.Entry<String, Object> entry : set)
            {
                requestParams.addBodyParameter(
                        entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        httpUtils.send(HttpRequest.HttpMethod.POST,
                requestUrl, requestParams,
                new RequestCallBack<String>()
                {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo)
                    {
                        callback.onSuccess(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg)
                    {
                        callback.onFailure(error);
                    }
                });
    }

    /**
     * get请求
     *
     * @param requestUrl
     * @param params
     */
    private void doGet(String requestUrl, Map<String, Object> params, final RequestDataCallback callback)
    {
        RequestParams requestParams = new RequestParams();

        if (params != null)
        {
            Set<Map.Entry<String, Object>> set = params.entrySet();

            for (Map.Entry<String, Object> entry : set)
            {
                requestParams.addQueryStringParameter(
                        entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        httpUtils.send(HttpRequest.HttpMethod.GET,
                requestUrl, requestParams,
                new RequestCallBack<String>()
                {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo)
                    {
                        callback.onSuccess(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg)
                    {
                        callback.onFailure(error);
                    }
                });
    }

}
