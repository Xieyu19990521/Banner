package com.example.yu.small01.util;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.yu.small01.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .diskCacheSize(10*1024*1024)
                .memoryCacheSize(10)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build())
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
