package com.zdhx.androidbase.ui.scroSearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.zdhx.androidbase.R;
import com.zdhx.androidbase.ui.MainActivity;
import com.zdhx.androidbase.ui.base.BaseActivity;
import com.zdhx.androidbase.util.DateUtil;
import com.zdhx.androidbase.util.ProgressUtil;

import java.util.Calendar;

import static com.zdhx.androidbase.util.NetUtils.isNetworkConnected;

public class SelectScroActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	//回退按钮
	private ImageView backImg;

	private TextView dateFrom;
	private TextView dateTo;
	private TextView selectTree;
	private Button commit;

	private String DATAPAGERTAG = null;



	@Override
	protected int getLayoutId() {
		return R.layout.activity_selectscro;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTopBarView().setVisibility(View.GONE);
		backImg = (ImageView) findViewById(R.id.activity_selectscro_goback);
		dateFrom = (TextView) findViewById(R.id.activity_selectscro_date_from);
		dateFrom.setText(DateUtil.getCurrDateStringChinaYearAndMonth());
		dateTo = (TextView) findViewById(R.id.activity_selectscro_date_to);
		dateTo.setText(DateUtil.getCurrDateStringChinaYearAndMonth());
		selectTree = (TextView) findViewById(R.id.activity_selectscro_tree);
		commit = (Button) findViewById(R.id.activity_selectscro_but);
		backImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}
	//点击事件分项处理
	public void onClick(View view){
		switch (view.getId()){
			case R.id.activity_selectscro_date_from:
				Calendar calendarFrom = Calendar.getInstance();
				final DatePickerDialog datePickerDialogFrom = DatePickerDialog.newInstance(SelectScroActivity.this,calendarFrom.get(java.util.Calendar.YEAR),
						calendarFrom.get(java.util.Calendar.MONTH),
						calendarFrom.get(java.util.Calendar.DAY_OF_MONTH));
				datePickerDialogFrom.show(getFragmentManager(),"DATEPICKERTAGFROM");
				DATAPAGERTAG = "DATEPICKERTAGFROM";
				break;
			case R.id.activity_selectscro_date_to:
				Calendar calendarTo = Calendar.getInstance();
				final DatePickerDialog datePickerDialogTo = DatePickerDialog.newInstance(SelectScroActivity.this,calendarTo.get(java.util.Calendar.YEAR),
						calendarTo.get(java.util.Calendar.MONTH),
						calendarTo.get(java.util.Calendar.DAY_OF_MONTH));
				datePickerDialogTo.show(getFragmentManager(),"DATEPICKERTAGTO");
				DATAPAGERTAG = "DATEPICKERTAGTO";
				break;
			case R.id.activity_selectscro_tree:
				doToast("树选择");
				break;
			case R.id.activity_selectscro_but:
				String from = dateFrom.getText().toString();
				String to = dateTo.getText().toString();
				String tree = "树选择的内容";
				ProgressUtil.show(SelectScroActivity.this,"正在搜索");
				searchScro(from,to,tree);
				break;
		}
	}

	private void searchScro(String from,String to,String tree){
		if (!isNetworkConnected()){
			doToast("网络连接不可用");
			return;
		}
		new Thread(){
			@Override
			public void run() {
				try {
					sleep(3000);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ProgressUtil.hide();
							SelectScroActivity.this.finish();
							MainActivity.map.put("data","返回了数据");
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}



	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

	}

	@Override
	public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
		int month = monthOfYear+1;
		if (DATAPAGERTAG.equals("DATEPICKERTAGFROM"))
			dateFrom.setText(year+"年"+month+"月");
		if (DATAPAGERTAG.equals("DATEPICKERTAGTO"))
			dateTo.setText(year+"年"+month+"月");
	}
}
