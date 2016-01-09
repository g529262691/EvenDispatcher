package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by Gan on 2015/12/29.
 */
public class Hotel
{

    /**
     * result : 0
     * message : 接口通信成功！
     * responseData : {"totalItems":"86","totalPage":"18","currentPage":"1","items":[{"id":"122330","name":"格林豪泰北京市昌平区回龙观平西府地铁站快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122330/Logo/Logo_122330_1.jpg","score":"4.8","distance":"","price":"99.8","isFull":"false","longitude":"116.349761","latitude":"40.09417","service":["41","72"],"isPromotion":"false"},{"id":"122291","name":"格林豪泰北京市通州区马驹桥2号桥快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122291/Logo/Logo_122291_1.jpg","score":"4.3","distance":"","price":"99.8","isFull":"false","longitude":"116.563753","latitude":"39.755982","service":["41","51","72"],"isPromotion":"false"},{"id":"122097","name":"格林豪泰北京市昌平区华北电力大学商务酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122097/Logo/Logo_122097_1.jpg","score":"4.7","distance":"","price":"144","isFull":"false","longitude":"116.319614","latitude":"40.090629","service":["41","51","72"],"isPromotion":"false"},{"id":"120447","name":"格林豪泰北京市学院路五道口地铁站商务酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/120447/Logo/Logo_120447_1.jpg","score":"4.7","distance":"","price":"237","isFull":"false","longitude":"116.34707","latitude":"39.994281","service":["41","51","72","91"],"isPromotion":"false"},{"id":"121073","name":"格林豪泰北京市丰台区云岗路快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/121073/Logo/Logo_121073_1.jpg","score":"4.8","distance":"","price":"177","isFull":"false","longitude":"116.178458","latitude":"39.813249","service":["41","72"],"isPromotion":"false"}]}
     */

    private String result;
    private String message;
    /**
     * totalItems : 86
     * totalPage : 18
     * currentPage : 1
     * items : [{"id":"122330","name":"格林豪泰北京市昌平区回龙观平西府地铁站快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122330/Logo/Logo_122330_1.jpg","score":"4.8","distance":"","price":"99.8","isFull":"false","longitude":"116.349761","latitude":"40.09417","service":["41","72"],"isPromotion":"false"},{"id":"122291","name":"格林豪泰北京市通州区马驹桥2号桥快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122291/Logo/Logo_122291_1.jpg","score":"4.3","distance":"","price":"99.8","isFull":"false","longitude":"116.563753","latitude":"39.755982","service":["41","51","72"],"isPromotion":"false"},{"id":"122097","name":"格林豪泰北京市昌平区华北电力大学商务酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122097/Logo/Logo_122097_1.jpg","score":"4.7","distance":"","price":"144","isFull":"false","longitude":"116.319614","latitude":"40.090629","service":["41","51","72"],"isPromotion":"false"},{"id":"120447","name":"格林豪泰北京市学院路五道口地铁站商务酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/120447/Logo/Logo_120447_1.jpg","score":"4.7","distance":"","price":"237","isFull":"false","longitude":"116.34707","latitude":"39.994281","service":["41","51","72","91"],"isPromotion":"false"},{"id":"121073","name":"格林豪泰北京市丰台区云岗路快捷酒店","imageUrl":"http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/121073/Logo/Logo_121073_1.jpg","score":"4.8","distance":"","price":"177","isFull":"false","longitude":"116.178458","latitude":"39.813249","service":["41","72"],"isPromotion":"false"}]
     */

    private ResponseDataEntity responseData;

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResponseData(ResponseDataEntity responseData)
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

    public ResponseDataEntity getResponseData()
    {
        return responseData;
    }

    public static class ResponseDataEntity
    {
        private String totalItems;
        private String totalPage;
        private String currentPage;
        /**
         * id : 122330
         * name : 格林豪泰北京市昌平区回龙观平西府地铁站快捷酒店
         * imageUrl : http://a3.greentree.cn:8022/UploadFiles/CRS/HotelImg/122330/Logo/Logo_122330_1.jpg
         * score : 4.8
         * distance :
         * price : 99.8
         * isFull : false
         * longitude : 116.349761
         * latitude : 40.09417
         * service : ["41","72"]
         * isPromotion : false
         */

        private List<ItemsEntity> items;

        public void setTotalItems(String totalItems)
        {
            this.totalItems = totalItems;
        }

        public void setTotalPage(String totalPage)
        {
            this.totalPage = totalPage;
        }

        public void setCurrentPage(String currentPage)
        {
            this.currentPage = currentPage;
        }

        public void setItems(List<ItemsEntity> items)
        {
            this.items = items;
        }

        public String getTotalItems()
        {
            return totalItems;
        }

        public String getTotalPage()
        {
            return totalPage;
        }

        public String getCurrentPage()
        {
            return currentPage;
        }

        public List<ItemsEntity> getItems()
        {
            return items;
        }

        public static class ItemsEntity
        {
            private String id;
            private String name;
            private String imageUrl;
            private String score;
            private String distance;
            private String price;
            private String isFull;
            private String longitude;
            private String latitude;
            private String isPromotion;
            private List<String> service;

            public void setId(String id)
            {
                this.id = id;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public void setImageUrl(String imageUrl)
            {
                this.imageUrl = imageUrl;
            }

            public void setScore(String score)
            {
                this.score = score;
            }

            public void setDistance(String distance)
            {
                this.distance = distance;
            }

            public void setPrice(String price)
            {
                this.price = price;
            }

            public void setIsFull(String isFull)
            {
                this.isFull = isFull;
            }

            public void setLongitude(String longitude)
            {
                this.longitude = longitude;
            }

            public void setLatitude(String latitude)
            {
                this.latitude = latitude;
            }

            public void setIsPromotion(String isPromotion)
            {
                this.isPromotion = isPromotion;
            }

            public void setService(List<String> service)
            {
                this.service = service;
            }

            public String getId()
            {
                return id;
            }

            public String getName()
            {
                return name;
            }

            public String getImageUrl()
            {
                return imageUrl;
            }

            public String getScore()
            {
                return score;
            }

            public String getDistance()
            {
                return distance;
            }

            public String getPrice()
            {
                return price;
            }

            public String getIsFull()
            {
                return isFull;
            }

            public String getLongitude()
            {
                return longitude;
            }

            public String getLatitude()
            {
                return latitude;
            }

            public String getIsPromotion()
            {
                return isPromotion;
            }

            public List<String> getService()
            {
                return service;
            }
        }
    }
}
