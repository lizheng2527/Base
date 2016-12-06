/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.zdhx.androidbase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import cn.sharesdk.framework.ShareSDK;

import com.pgyersdk.crash.PgyCrashManager;
import com.zdhx.androidbase.entity.User;
import com.zdhx.androidbase.util.LogUtil;
import com.zdhx.androidbase.util.PreferenceUtil;
import com.zdhx.androidbase.util.ToastUtil;
import com.zdhx.volley.Request;
import com.zdhx.volley.RequestQueue;
import com.zdhx.volley.toolbox.Volley;

public class ECApplication extends Application {


    private static ECApplication instance;
    
    private static RequestQueue mVolleyQueue = null;
    
    private List<Activity> activityList = new ArrayList<Activity>();

    /**
     * 单例，返回一个实例
     * @return
     */
    public static synchronized  ECApplication getInstance() {
        if (instance == null) {
            LogUtil.w("[ECApplication] instance is null.");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        PgyCrashManager.register(this); //注册蒲公英Crash反馈
        ShareSDK.initSDK(instance);     //注册分享
    }


    /**
     * 返回配置文件的日志开关
     * @return
     */
    public boolean getLoggingSwitch() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            boolean b = appInfo.metaData.getBoolean("LOGGING");
            LogUtil.w("[ECApplication - getLogging] logging is: " + b);
            return b;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean getAlphaSwitch() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            boolean b = appInfo.metaData.getBoolean("ALPHA");
            LogUtil.w("[ECApplication - getAlpha] Alpha is: " + b);
            return b;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    
    public static RequestQueue getRequestQueue() {
        if (mVolleyQueue == null) {
            mVolleyQueue = Volley.newRequestQueue(getInstance());
        }
        return mVolleyQueue;
    }

    public static void addRequest(Request<?> req, String tag) {
        req.setTag(tag);
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }
    
    /**
     * 返回登录状态
     * @return
     */
    public boolean isLogin(){
    	if (getCurrentUser() != null) {
    		if (getCurrentUser().isLogin()) {
    			return true;
    		} else {
    			ToastUtil.showMessage("请先登录用户");
    			return false;
    		}
    	}
    	ToastUtil.showMessage("请先登录用户");
    	return false;
    }
    
    /**
     * 设置登录状态
     * @param flag
     */
    public void setLoginFlag (boolean flag) {
    	if (getCurrentUser()!=null) {
    		User user = getCurrentUser();
    		user.setLogin(flag);
    		PreferenceUtil.save(user, "user");
    	}
    }
    
    /**
     * 修改密码
     * @param flag
     */
    public void resetPsw (String psw) {
    	if (getCurrentUser()!=null) {
    		User user = getCurrentUser();
    		user.setLoginPsw(psw);
    		PreferenceUtil.save(user, "user");
    	}
    }
    
    /**
     * 存储当前用户
     * @param user
     */
    public void saveUser(User user) {
    	PreferenceUtil.save(user, "user");
    }
    /**
     * 获取当前用户实体
     * @return
     */
    public User getCurrentUser(){
    	return PreferenceUtil.find("user", User.class);
    }
    
    /**
     * 更新头像
     * @return
     */
    public void upDataHeadInfo(String info){
    	if (getCurrentUser()!=null) {
    		User user = getCurrentUser();
    		user.setMemberAvatar(info);
    		PreferenceUtil.save(user, "user");
    	}
    }
    
    
    /**
	 * 存储
	 */
	public void saveValue(String key,String value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
		preferences.edit().putString(key,value).commit();
	}
	public String getValue(String key){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
		return preferences.getString(key, "");
	}
    
	//添加Activity到容器中
	public void addActivity(Activity activity){
		activityList.add(activity);
		System.out.println("activityList"+activityList.size());
	}
		
	//移除Activity到容器中
	public void removeActivity(Activity activity){
		activityList.remove(activity);
		System.out.println("activityList"+activityList.size());
	}
	
	// 遍历所有Activity并finish
	public void exit(){
		for (Activity activity : activityList){
			activity.finish();
		}
		System.exit(0);
	}
}
