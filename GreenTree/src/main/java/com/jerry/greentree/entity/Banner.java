package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by jerry on 15/11/2.
 */
public class Banner
{
    private String result;
    private String message;
    private ResponseData responseData;

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResponseData(ResponseData responseData)
    {
        this.responseData = responseData;
    }

    public String getResult()
    {
        return result;
    }

    public String getMessage()
    {
        return message;
    }

    public ResponseData getResponseData()
    {
        return responseData;
    }

    public static class ResponseData
    {
        private List<BannerList> bannerList;

        public void setBannerList(List<BannerList> bannerList)
        {
            this.bannerList = bannerList;
        }

        public List<BannerList> getBannerList()
        {
            return bannerList;
        }

        public static class BannerList
        {
            private int bannerId;
            private String bannerUrl;

            public void setBannerId(int bannerId)
            {
                this.bannerId = bannerId;
            }

            public void setBannerUrl(String bannerUrl)
            {
                this.bannerUrl = bannerUrl;
            }

            public int getBannerId()
            {
                return bannerId;
            }

            public String getBannerUrl()
            {
                return bannerUrl;
            }
        }
    }
}
