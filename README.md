# CarouselView
banner轮播效果

## 效果图
![](https://raw.githubusercontent.com/loonggg/CarouselView/master/image/ss.gif)

## 使用方法（usage）
### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
### Step 2. Add the dependency
```java
	dependencies {
	        compile 'com.github.loonggg:CarouselView:v1.2.1'
	}
  ```
### Step 3. There are a few xml attributes to customise the  CarouselView
* pointFocusBg 显示当前图片时，底部小圆点图片
* pointUnfocusBg 没有显示当前图片时，小圆点图片
* pageCount 设置轮播图数
* flipInterval 设置轮播图间隔滚动时间
* pointIntervalWidth 设置底部小圆点之间的间隔宽度
* isAutoPlay 设置是否自动滚动
* isShowPoint 设置是否显示底部指示导航小圆点

#### Example
```xml
<com.loonggg.carouselview.CarouselView
        android:id="@+id/carousel_view"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        android:background="#8f8f8f"
        app:flipInterval="3000"
        app:isAutoPlay="true"
        app:pointFocusBg="@mipmap/point_pressed"
        app:pointUnfocusBg="@mipmap/point_normal"
        app:pointIntervalWidth="8dp" />
```

### Step 4. Impelement Listener
```java
        final List<String> list = new ArrayList<>();
        list.add("http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg");
        list.add("http://d.hiphotos.baidu.com/image/pic/item/0823dd54564e92584a00b4e99e82d158ccbf4e84.jpg");
        list.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=15c6eac033adcbef1e3479069cae2e0e/6d81800a19d8bc3e7451d5ce808ba61ea8d3455d.jpg");
        carouselView = (CarouselView) findViewById(R.id.carousel_view);
        //设置加载显示的banner广告轮播图
        carouselView.setImageCarouselLoaderListener(new CarouselView.ImageCarouselLoaderListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(MainActivity.this)
                        .load(list.get(position))
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .crossFade()
                        .into(imageView);
            }
        });
        //也可以在这里设置轮播banner数
        carouselView.setPageCount(3);
        //设置点击事件
        carouselView.setOnCarouselViewItemClickListener(new CarouselView.OnCarouselViewItemClickListener() {
            @Override
            public void OnCarouselViewItemClickListener(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //获取当前展示的item索引
        //carouselView.getCurrentIndex();

```

### 公众号
欢迎大家关注我的微信公众号：非著名程序员（smart_android），更多好的原创文章均首发于微信订阅号：非著名程序员
![](https://raw.githubusercontent.com/loonggg/BlogImages/master/%E5%85%AC%E4%BC%97%E5%8F%B7%E4%BA%8C%E7%BB%B4%E7%A0%81/erweima.jpg)

# License
```xml
Copyright 2016 loonggg

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

  
