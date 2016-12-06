package com.zdhx.androidbase.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import com.zdhx.androidbase.SystemConst;

/**
 * 数据接口
 * 
 * @Title: UrlUtil.java
 * @Package com.bj.android.hzth.parentcircle.utils
 * @author 容联•云通讯 Modify By Li.Xin @ 立思辰合众
 * @date 2014-11-21 上午11:16:38
 */

public class UrlUtil {
	public static String getUrl(String url, Map<String, String> map) {
		url = url + "?";
		Set<String> keySet = map.keySet();
		int i = 0;
		for (String key : keySet) {
			// String
			try {
				if (StringUtil.isNotBlank(map.get(key))) {
					url += (i == 0 ? "" : "&") + key + "=" + URLEncoder.encode(map.get(key), "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		return url;
	}
	
	public static String getImgUrl(String url) {
		url = SystemConst.DEFAULT_IMAGE_URL +  url;
		return url;
	}
}
