package com.jerry.greentree.app;

import android.app.Application;
import android.os.Environment;

import com.jerry.greentree.R;
import com.jerry.greentree.db.HotelCollect;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jerry on 15/11/2.
 */
public class GreenTreeApplication extends Application
{
    private static GreenTreeApplication app;
    private HttpUtils httpUtils;
    private ImageLoader imageLoader = null;
    private DisplayImageOptions imageOptions;

    private DbUtils dbUtils;

    @Override
    public void onCreate()
    {
        super.onCreate();

        app = this;

        // ShareSDK初始化
        ShareSDK.initSDK(this);
        // 初始化JPush
        JPushInterface.init(this);

        // 开发状态下设置为调试模式
        // 正式上线后，设置成false
        JPushInterface.setDebugMode(true);

        initHttpUtils();

        initDbUtils();

        initUniversalImageLoader();
    }

    public static GreenTreeApplication getApp()
    {
        return app;
    }

    /**
     * 初始化dbUtils
     */
    private void initDbUtils()
    {
        DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(this);
        daoConfig.setDbName("greentree");
        daoConfig.setDbVersion(1);
        daoConfig.setDbDir("/mnt/sdcard/greentree");

        dbUtils = DbUtils.create(daoConfig);
        try
        {
            // 创建t_collect表
            dbUtils.createTableIfNotExist(HotelCollect.class);
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
    }

    public DbUtils getDbUtils()
    {
        return dbUtils;
    }

    /**
     * 初始化HttpUtils
     */
    private void initHttpUtils()
    {
        httpUtils = new HttpUtils();

        httpUtils.configRequestThreadPoolSize(5);

        httpUtils.configRequestRetryCount(3);

        httpUtils.configResponseTextCharset("utf-8");

        httpUtils.configSoTimeout(30 * 1000);

        httpUtils.configTimeout(60 * 1000);
    }

    public HttpUtils getHttpUtils()
    {
        return this.httpUtils;
    }

    /**
     * 初始化UniversalImageLoader
     */
    public void initUniversalImageLoader()
    {
        imageLoader = ImageLoader.getInstance();

        // 内存大小
        int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;

        String cachePath = null;

        // 磁盘缓存路径
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            cachePath = getApplicationContext().getExternalCacheDir().getPath();
        } else
        {
            cachePath = getApplicationContext().getCacheDir().getPath();
        }

        File cacheFileDir = new File(cachePath, "/greentree/images");
        cacheFileDir.mkdirs();

        // UniversalImageLoader配置
        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration
                        .Builder(getApplicationContext())
                        .memoryCacheSize(cacheSize)
                        .diskCache(new UnlimitedDiskCache(cacheFileDir))
                        .diskCacheFileCount(300)
                        .diskCacheSize(200 * 1024 * 1024)
                        .build();

        // 初始化配置
        ImageLoader.getInstance().init(configuration);

        // 全局图片选项
        imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.list_bg_200)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .build();
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public DisplayImageOptions getImageOptions()
    {
        return imageOptions;
    }

}
