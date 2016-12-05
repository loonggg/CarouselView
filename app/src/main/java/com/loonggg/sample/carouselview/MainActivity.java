package com.loonggg.sample.carouselview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loonggg.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<String> list = new ArrayList<>();
        list.add("http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg");
        list.add("http://d.hiphotos.baidu.com/image/pic/item/0823dd54564e92584a00b4e99e82d158ccbf4e84.jpg");
        list.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=15c6eac033adcbef1e3479069cae2e0e/6d81800a19d8bc3e7451d5ce808ba61ea8d3455d.jpg");
        carouselView = (CarouselView) findViewById(R.id.carousel_view);
        //设置加载显示的banner广告轮播图
        carouselView.setImageCarouselLoaderListener(new CarouselView.ImageCarouselLoaderListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
//                Glide.with(MainActivity.this)
//                        .load(list.get(position))
//                        .centerCrop()
//                        .placeholder(R.mipmap.ic_launcher)
//                        .crossFade()
//                        .into(imageView);
                if(position ==0){
                    imageView.setImageResource(R.mipmap.ad_demo);
                }else if(position == 1){
                    imageView.setImageResource(R.mipmap.ad_two_demo);
                }else if(position == 2){
                    imageView.setImageResource(R.mipmap.ad_three_demo);
                }else if(position == 3){
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
        //也可以在这里设置轮播banner数
        carouselView.setPageCount(4);
        //设置点击事件
        carouselView.setOnCarouselViewItemClickListener(new CarouselView.OnCarouselViewItemClickListener() {
            @Override
            public void OnCarouselViewItemClickListener(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //获取当前展示的item索引
        //carouselView.getCurrentIndex();

    }

}
