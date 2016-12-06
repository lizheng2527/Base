package com.zdhx.androidbase.util.imageLoader.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {

	private static final String TAG = "MemoryCache";
	// 锟斤拷锟诫缓锟斤拷时锟角革拷同锟斤拷锟斤拷锟斤拷
	// LinkedHashMap锟斤拷锟届方锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟絫rue锟斤拷锟斤拷锟斤拷锟絤ap锟斤拷锟皆拷亟锟斤拷锟斤拷锟斤拷锟斤拷使锟矫达拷锟斤拷锟斤拷锟劫碉拷锟斤拷锟斤拷锟叫ｏ拷锟斤拷LRU
	// 锟斤拷锟斤拷锟侥好达拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷械锟皆拷锟斤拷婊伙拷锟斤拷锟斤拷缺锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷使锟矫碉拷元锟斤拷锟斤拷锟芥换锟斤拷锟斤拷锟叫э拷锟�
	private Map<String, Bitmap> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	// 锟斤拷锟斤拷锟斤拷图片锟斤拷占锟矫碉拷锟街节ｏ拷锟斤拷始0锟斤拷锟斤拷通锟斤拷锟剿憋拷锟斤拷锟较革拷锟斤拷苹锟斤拷锟斤拷锟秸硷拷玫亩锟斤拷诖锟�
	private long size = 0;// current allocated size
	// 锟斤拷锟斤拷只锟斤拷占锟矫碉拷锟斤拷锟斤拷锟节达拷
	private long limit = 1000000;// max memory in bytes

	public MemoryCache() {
		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 10);
	}

	public void setLimit(long new_limit) {
		limit = new_limit;
//		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			return cache.get(id);
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * 锟较革拷锟斤拷贫锟斤拷诖妫拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟芥换锟斤拷锟斤拷锟斤拷锟绞癸拷玫锟斤拷歉锟酵计拷锟斤拷锟�
	 * 
	 */
	private void checkSize() {
		Log.i(TAG, "cache size=" + size + " length=" + cache.size());
		if (size > limit) {
			// 锟饺憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞癸拷玫锟皆拷锟�
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();
				size -= getSizeInBytes(entry.getValue());
				iter.remove();
				if (size <= limit)
					break;
			}
//			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}

	public void clear() {
		cache.clear();
	}

	/**
	 * 图片占锟矫碉拷锟节达拷
	 * 
	 * [url=home.php?mod=space&uid=2768922]@Param[/url] bitmap
	 * 
	 * @return
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
