package com.example.yu.small01;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yu.small01.bean.ContentBean;
import com.example.yu.small01.util.NetUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Banner banner;
    private TextView name,price1,price2;
    private int mpage=1;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner=findViewById(R.id.banner);
        name=findViewById(R.id.name);
        price1=findViewById(R.id.price1);
        price1.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        price2=findViewById(R.id.price2);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoader.getInstance().displayImage((String) path,imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

        });
        initData();

    }
    private void initData(){
        NetUtil.getIntance().requestData("http://www.zhaoapi.cn/product/getProductDetail?pid=" + mpage, ContentBean.class, new NetUtil.CallBack<ContentBean>() {
            @Override
            public void onSuccess(ContentBean contentBean) {
                list = new ArrayList<>();
                String[] split = contentBean.getData().getImages().split("\\|");
                Log.i("TEST",split.length+"one");
                for(int i=0;i<split.length;i++){
                    list.add(split[i]);
                }
                //sub(contentBean.getData().getImages());
                name.setText(contentBean.getData().getTitle());
                price1.setText("原价："+contentBean.getData().getPrice());
                price2.setText("优惠价："+contentBean.getData().getBargainPrice());
                banner.setImages(list);
                banner.start();

            }
        });
    }
    private void sub(String str){
        int indexof=str.indexOf("|");
        if(indexof>=0){
            String string=str.substring(0,indexof);
            list.add(string);
            sub(str.substring(indexof+1,str.length()));
        }else {
            list.add(str);
        }

    }
}
