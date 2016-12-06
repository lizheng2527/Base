package com.zdhx.androidbase.ui;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.zdhx.androidbase.ECApplication;
import com.zdhx.androidbase.R;
import com.zdhx.androidbase.ui.account.LoginActivity;
import com.zdhx.androidbase.ui.base.BaseActivity;
import com.zdhx.androidbase.util.LogUtil;
import com.zdhx.androidbase.util.StringUtil;
import com.zdhx.androidbase.util.ToastUtil;
import com.zdhx.androidbase.util.imageLoader.cache.ImageLoader;

public class MainActivity extends BaseActivity implements OnClickListener{

	private Activity context;

	private Fragment indexFragment;

	private Fragment brandFragment;

	private Fragment expenseFragment;

	private Fragment meFragment;

	private Fragment[] fragments;

	private int index;
	// 当前fragment的index
	private int currentTabIndex;
	
	private Button[] mTabs;
	
	private RelativeLayout[] mTabContainers;
	
	/**
	 * 缓存三个TabView
	 */
	private final HashMap<Integer, Fragment> mTabViewCache = new HashMap<Integer, Fragment>();
	
	@Override
	protected int getLayoutId() {return R.layout.activity_main;} 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		checkVerson();
		getTopBarView().setTopBarToStatus(1, -1,-1,"AndroidBase", this);
		initLauncherUIView();
		updata();
	}

	/**
	 * 初始化主界面UI视图
	 */
	private void initLauncherUIView() {
		indexFragment = new Fragment();
		brandFragment = new Fragment();
		expenseFragment = new Fragment();
		meFragment = new Fragment();
		fragments = new Fragment[] { indexFragment, brandFragment,expenseFragment, meFragment };
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, indexFragment)
				.add(R.id.fragment_container, brandFragment)
				.add(R.id.fragment_container, expenseFragment)
				.hide(brandFragment).hide(expenseFragment)
				.show(indexFragment).commitAllowingStateLoss();
		mTabs = new Button[4];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_address_list);
		mTabs[2] = (Button) findViewById(R.id.btn_circle);
		mTabs[3] = (Button) findViewById(R.id.btn_app);
		mTabContainers = new RelativeLayout[4];
		mTabContainers[0] = (RelativeLayout) findViewById(R.id.btn_container_conversation);
		mTabContainers[1] = (RelativeLayout) findViewById(R.id.btn_container_brand);
		mTabContainers[2] = (RelativeLayout) findViewById(R.id.btn_container_expense);
		mTabContainers[3] = (RelativeLayout) findViewById(R.id.btn_container_me);
		
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		mTabContainers[0].setSelected(true);
	}

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.btn_conversation:
			index = 0;
			getTopBarView().setTitle("AndroidBase-1");
			break;
		case R.id.btn_address_list:
			index = 1;
			getTopBarView().setTitle("AndroidBase-2");
			break;
		case R.id.btn_circle:
			index = 2;
			getTopBarView().setTitle("AndroidBase-2");
			break;
		case R.id.btn_app:
			index = 3;
			getTopBarView().setTitle("AndroidBase-3");
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		mTabContainers[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		mTabContainers[index].setSelected(true);
		currentTabIndex = index;
	}
	
	public void onUpdateApp (View v) {
		updata();
	}
	
	public void updata() {
		ToastUtil.showMessage("正在检查版本更新，请稍等");
		PgyUpdateManager.register(MainActivity.this,
				new UpdateManagerListener() {

					@Override
					public void onUpdateAvailable(final String result) {

						// 将新版本信息封装到AppBean中
						final AppBean appBean = getAppBeanFromString(result);

						final AlertDialog alertDialog = new AlertDialog.Builder(
								MainActivity.this).create();
						alertDialog.show();
						Window window = alertDialog.getWindow();
						window.setContentView(R.layout.umeng_update_dialog);
						TextView umeng_update_content = (TextView) window
								.findViewById(R.id.umeng_update_content);
						umeng_update_content.setText("v" + appBean.getVersionName() + "版本更新日志：\n" + (StringUtil.isBlank(appBean.getReleaseNote()) ? "无"
										: appBean.getReleaseNote()));
						Button umeng_update_id_ok = (Button) window
								.findViewById(R.id.umeng_update_id_ok);
						umeng_update_id_ok
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										startDownloadTask(MainActivity.this,
												appBean.getDownloadURL());
										alertDialog.dismiss();
									}
								});
						Button umeng_update_id_cancel = (Button) window
								.findViewById(R.id.umeng_update_id_cancel);
						umeng_update_id_cancel
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										alertDialog.dismiss();
									}
								});
					}

					@Override
					public void onNoUpdateAvailable() {
						ToastUtil.showMessage("已是最新版本");
					}
				});
	}
	
	public void onFeedBack (View v) {
		startActivity(new Intent(context, LoginActivity.class));
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private String getTitleText() {
		String text = "AndroidBase-1";
		switch (index) {
		case 0:
			text = "AndroidBase-1";
			break;
		case 1:
			text = "AndroidBase-2";
			break;
		case 2:
			text = "AndroidBase-3";
			break;
		case 3:
			text = "AndroidBase-4";
			break;
		}
		return text;
	}
	
	private long exitTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 9000) {
			
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_right:
			startActivity(new Intent(context, LoginActivity.class));
			break;
		default:
			break;
		}
	}
	
	public void checkVerson() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		int currentVersion = info.versionCode;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int lastVersion = prefs.getInt("VERSION_KEY", 0);
		LogUtil.e("lastVersion:"+lastVersion+"---currentVersion:"+currentVersion);
		if (currentVersion > lastVersion) {
		     //如果当前版本大于上次版本，该版本属于第一次启动
		     //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
		     prefs.edit().putInt("VERSION_KEY",currentVersion).commit();
		}
	}
}
