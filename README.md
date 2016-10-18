# CarouselView
banner轮播效果

## 效果图

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
	        compile 'com.github.loonggg:CarouselView:v1.0'
	}
  ```
### Step 3. There are a few xml attributes to customise the  CarouselView
* pointFocusBg 显示当前图片时，底部小圆点图片
* pointUnfocusBg 没有显示当前图片时，小圆点图片
* pageCount 设置轮播图数
* flipInterval 设置轮播图间隔滚动时间
* pointIntervalWidth 设置底部小圆点之间的间隔宽度
* isAutoPlay 设置是否自动滚动

#### Example
```xml
<com.loonggg.carouselview.CarouselView
        android:id="@+id/carousel_view"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="#8f8f8f"
        app:flipInterval="3000"
        app:isAutoPlay="true"
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

  
