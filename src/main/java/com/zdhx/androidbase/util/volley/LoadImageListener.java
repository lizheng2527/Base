package com.zdhx.androidbase.util.volley;

import android.graphics.Bitmap;
public interface LoadImageListener {
	public void onSuccess(Bitmap bitmap);
	public void onError();
}
