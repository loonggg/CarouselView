package com.loonggg.carouselview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 作者：loonggg on 2016/10/17 17:00
 */

public class CarouselView extends LinearLayout implements GestureDetector.OnGestureListener, View.OnTouchListener, AdViewFlipper.OnViewFlipperChangeState {
    private GestureDetector detector;
    private AdViewFlipper adVf;
    private Context context;
    private ImageView[] pointIvs;
    private int adCount = 0;
    private int adIndex = 0;
    private ImageCarouselLoaderListener listener;
    private OnCarouselViewItemClickListener onItemClickListener;
    private LinearLayout adPointLayout;
    private Drawable pointFocusBg, pointUnFocusBg;
    private float pointIntervalWidth = 8, pointLayoutMarginBottom = 8;
    private int flipInterval = 2000;
    private boolean isAutoPlay = true;
    private boolean isShowPoint = true;

    public CarouselView(Context context) {
        super(context);
        this.context = context;
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs, 0, 0);
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_carousel, this, true);
        adVf = (AdViewFlipper) view.findViewById(R.id.ad_view_flipper);
        adPointLayout = (LinearLayout) view.findViewById(R.id.ad_point_layout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CarouselView);
        pointFocusBg = typedArray.getDrawable(R.styleable.CarouselView_pointFocusBg);
        pointUnFocusBg = typedArray.getDrawable(R.styleable.CarouselView_pointUnfocusBg);
        flipInterval = typedArray.getInteger(R.styleable.CarouselView_flipInterval, 2000);
        adCount = typedArray.getInteger(R.styleable.CarouselView_pageCount, 0);
        pointIntervalWidth = typedArray.getDimension(R.styleable.CarouselView_pointIntervalWidth, 8f);
        pointLayoutMarginBottom = typedArray.getDimension(R.styleable.CarouselView_pointMarginBottom, 8f);
        isAutoPlay = typedArray.getBoolean(R.styleable.CarouselView_isAutoPlay, true);
        isShowPoint = typedArray.getBoolean(R.styleable.CarouselView_isShowPoint, true);
        adVf.setOnTouchListener(this);
        adVf.setFlipInterval(flipInterval);
        adVf.setLongClickable(true);// 设置长按事件
        adVf.setAutoStart(isAutoPlay);// 设置是否自动播放，默认不自动播放
        detector = new GestureDetector(this);
        if (!isShowPoint) {
            adPointLayout.setVisibility(View.GONE);
        }
        typedArray.recycle();
    }

    public void setFlipInterval(int time) {
        flipInterval = time;
        adVf.setFlipInterval(time);
    }

    public void setPageCount(int pageCount) {
        adCount = pageCount;
        setBanner();
    }

    private void setBanner() {
        adVf.removeAllViews();
        pointIvs = new ImageView[adCount];
        adPointLayout.removeAllViews();
        setAdPoints();
        adVf.setOnViewFlipperChangeState(this);
        for (int i = 0; i < adCount; i++) {
            adVf.addView(getImageView(i));
        }
        adVf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnCarouselViewItemClickListener(adIndex);
            }
        });
        startPlay();
    }

    public int getCurrentIndex() {
        return adIndex;
    }

    private void startPlay() {
        adVf.setInAnimation(AnimationUtils.loadAnimation(context,
                R.anim.ad_push_left_in));
        adVf.setOutAnimation(AnimationUtils.loadAnimation(context,
                R.anim.ad_push_left_out));
    }

    private void setAdPoints() {
        for (int i = 0; i < pointIvs.length; i++) {
            pointIvs[i] = new ImageView(context);
            if (i == 0) {
                if (pointFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
                }
            } else {
                if (pointUnFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointUnFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
                }
            }
            LayoutParams pointIvParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            pointIvParams.setMargins((int) pointIntervalWidth, 0, 0, (int)pointLayoutMarginBottom);
            pointIvs[i].setLayoutParams(pointIvParams);
            adPointLayout.addView(pointIvs[i]);
        }
    }

    private ImageView getImageView(int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        if (listener != null)
            listener.setImageForPosition(position, imageView);
        return imageView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            adVf.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_left_in));
            adVf.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_left_out));
            adVf.showNext();// 向右滑动
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            adVf.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_right_in));
            adVf.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_right_out));
            adVf.showPrevious();
            adVf.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_left_in));
            adVf.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.ad_push_left_out));
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    @Override
    public void changePre() {
        if (adIndex > 0) {
            adIndex--;
        } else {
            adIndex = pointIvs.length - 1;
        }
        for (int i = 0; i < pointIvs.length; i++) {
            if (adIndex == i) {
                if (pointFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
                }
            } else {
                if (pointUnFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointUnFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
                }
            }

        }

    }

    @Override
    public void changeNext() {
        if (adIndex < pointIvs.length - 1) {
            adIndex++;
        } else {
            adIndex = 0;
        }
        for (int i = 0; i < pointIvs.length; i++) {
            if (adIndex == i) {
                if (pointFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
                }
            } else {
                if (pointUnFocusBg != null) {
                    pointIvs[i].setBackgroundDrawable(pointUnFocusBg);
                } else {
                    pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
                }
            }
        }

    }

    public interface ImageCarouselLoaderListener {
        public void setImageForPosition(int position, ImageView imageView);
    }

    public void setImageCarouselLoaderListener(ImageCarouselLoaderListener listener) {
        this.listener = listener;
    }

    public interface OnCarouselViewItemClickListener {
        public void OnCarouselViewItemClickListener(int position);
    }

    public void setOnCarouselViewItemClickListener(OnCarouselViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
