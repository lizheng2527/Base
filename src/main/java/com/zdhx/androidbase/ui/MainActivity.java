package com.zdhx.androidbase.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.zdhx.androidbase.R;
import com.zdhx.androidbase.ui.account.HomeFragment;
import com.zdhx.androidbase.ui.account.LoginActivity;
import com.zdhx.androidbase.ui.account.MeFragment;
import com.zdhx.androidbase.ui.account.ScroFragment;
import com.zdhx.androidbase.ui.account.WorkSpaceFragment;
import com.zdhx.androidbase.ui.base.BaseActivity;
import com.zdhx.androidbase.ui.introducetreads.IntroduceTreadsActivity;
import com.zdhx.androidbase.ui.scroSearch.SelectScroActivity;
import com.zdhx.androidbase.util.LogUtil;
import com.zdhx.androidbase.util.StringUtil;
import com.zdhx.androidbase.util.ToastUtil;
import com.zdhx.androidbase.view.dialog.ECListDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements OnClickListener{

	public static HashMap<String,Object> map = new HashMap<String, Object>();
	private Activity context;

	private HomeFragment homeFragment;

	private WorkSpaceFragment workSpaceFragment;

	private MeFragment meFragment;

	private ScroFragment scroFragment;

	private Fragment[] fragments;

	private int index;
	// 当前fragment的index
	private int currentTabIndex;

	private Button[] mTabs;

	private RelativeLayout[] mTabContainers;

	private LinearLayout typeMenu;
	//主页显示的菜单选项
	private ArrayList<String> homeMenuDatas;
	//选择菜单的计数器（用来统计显示的条目）
	private int menuPosition;
	//选择菜单显示的文字
	private TextView menuSelectedTV;

	public static int SELECTMENUINDEX = 0;
	//功能fragment标题
	private TextView fragTitle;
	//标题栏左边第一个图标
	private ImageView titleImgFirst;
	//标题栏左边第二个图标
	private ImageView titleImgSecond;
	//标题栏左边第三个图标
	private ImageView titleImgThird;
	//跳转到选择时间及班级的请求码
	private final  int SELECTSCROACTIVITYCODE = 0;
	//跳转到发布活动页面的请求码
	private final  int INTRODUCETREADSCODE = 1;


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
		getTopBarView().setVisibility(View.GONE);
		initLauncherUIView();
		updata();
	}

	/**
	 * 初始化主界面UI视图
	 */
	private void initLauncherUIView() {
		homeFragment =  new HomeFragment();
		workSpaceFragment = new WorkSpaceFragment();
		meFragment = new MeFragment();
		scroFragment = new ScroFragment();
		fragments = new Fragment[] { homeFragment, workSpaceFragment,scroFragment, meFragment };
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment)
				.add(R.id.fragment_container, workSpaceFragment)
				.add(R.id.fragment_container, scroFragment).add(R.id.fragment_container,meFragment)
				.hide(workSpaceFragment).hide(scroFragment).hide(meFragment)
				.show(homeFragment).commitAllowingStateLoss();
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
		typeMenu = (LinearLayout) findViewById(R.id.home_menu);
		menuSelectedTV = (TextView) findViewById(R.id.menuSelectedTV);
		homeMenuDatas = new ArrayList<String>();
		homeMenuDatas.add("班级");
		homeMenuDatas.add("年级");
		homeMenuDatas.add("学校");
		homeMenuDatas.add("教师");
		typeMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ECListDialog d = new ECListDialog(MainActivity.this,homeMenuDatas,menuPosition);
				d.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
					@Override
					public void onDialogItemClick(Dialog d, int position) {
						menuPosition = position;
						menuSelectedTV.setText(homeMenuDatas.get(position));
						//TODO
					}
				});
				d.show();
			}
		});
		fragTitle = (TextView) findViewById(R.id.main_title);
		titleImgFirst = (ImageView) findViewById(R.id.main_img_left_first);
		titleImgSecond = (ImageView) findViewById(R.id.main_img_left_second);
		titleImgThird = (ImageView) findViewById(R.id.main_img_left_third);
		titleImgThird.setImageResource(R.drawable.send_treads);
		titleImgThird.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				OnRightButtonClick();
			}
		});
	}

	private void OnRightButtonClick() {
		switch (SELECTMENUINDEX){
			case 0:
				doToast("互动交流点击事件");
				startActivityForResult(new Intent(MainActivity.this,IntroduceTreadsActivity.class),INTRODUCETREADSCODE);
				break;
			case 1:
				doToast("工作平台点击事件");
				break;
			case 2:
				doToast("显示时间Dialog");
				startActivityForResult(new Intent(this, SelectScroActivity.class),SELECTSCROACTIVITYCODE);
				break;
		}
	}

	/**
	 * button点击事件
	 * 标题栏文字转换处理及图片显示处理
	 * @param view
	 */
	public void onTabClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_conversation:
				typeMenu.setVisibility(View.VISIBLE);
				titleImgSecond.setVisibility(View.VISIBLE);
				titleImgThird.setVisibility(View.VISIBLE);
				titleImgThird.setImageResource(R.drawable.send_treads);
				fragTitle.setText("互动交流");
				menuSelectedTV.setText("班级");
				if (homeMenuDatas != null){
					homeMenuDatas.clear();
				}else{
					homeMenuDatas = new ArrayList<String>();
				}
				homeMenuDatas.add("班级");
				homeMenuDatas.add("年级");
				homeMenuDatas.add("学校");
				homeMenuDatas.add("教师");
				index = 0;
				SELECTMENUINDEX = 0;
				break;
			case R.id.btn_address_list:
				titleImgSecond.setVisibility(View.VISIBLE);
				typeMenu.setVisibility(View.VISIBLE);
				titleImgThird.setVisibility(View.VISIBLE);
				titleImgThird.setImageResource(R.drawable.btn_index_normal);
				fragTitle.setText("工作平台");
				menuSelectedTV.setText("教师备课资源");
				if (homeMenuDatas != null){
					homeMenuDatas.clear();
				}else{
					homeMenuDatas = new ArrayList<String>();
				}
					homeMenuDatas.add("教师备课资源");
					homeMenuDatas.add("学生生成资源");
					homeMenuDatas.add("学生自我挑战");
					homeMenuDatas.add("学生作业质量");
				index = 1;
				SELECTMENUINDEX = 1;
				break;
			case R.id.btn_circle:
				fragTitle.setText("积分排名");
				titleImgSecond.setVisibility(View.INVISIBLE);
				titleImgThird.setVisibility(View.VISIBLE);
				titleImgThird.setImageResource(R.drawable.icon_sendtime);
				typeMenu.setVisibility(View.INVISIBLE);
				SELECTMENUINDEX = 2;
				index = 2;
				break;
			case R.id.btn_app:
				titleImgSecond.setVisibility(View.INVISIBLE);
				titleImgThird.setVisibility(View.INVISIBLE);
				typeMenu.setVisibility(View.INVISIBLE);
				fragTitle.setText("我的");
				SELECTMENUINDEX = 3;
				index = 3;
				break;
		}
		menuPosition = 0;
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

		if (resultCode == SELECTSCROACTIVITYCODE){
			if (map !=null){
				String s = (String) map.get("data");
				doToast(s);
			}
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
