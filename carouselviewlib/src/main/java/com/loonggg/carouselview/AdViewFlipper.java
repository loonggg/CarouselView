package com.loonggg.carouselview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class AdViewFlipper extends ViewFlipper {
	private OnViewFlipperChangeState listener;

	public AdViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AdViewFlipper(Context context) {
		super(context);
	}

	@Override
	public void showNext() {
		super.showNext();
		if (listener != null)
			listener.changeNext();
	}

	@Override
	public void showPrevious() {
		super.showPrevious();
		if (listener != null)
			listener.changePre();
	}

	public interface OnViewFlipperChangeState {
		void changePre();

		void changeNext();
	}

	public void setOnViewFlipperChangeState(OnViewFlipperChangeState listener) {
		this.listener = listener;
	}

}
