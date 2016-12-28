package com.zdhx.androidbase.ui.introducetreads;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroduceTreadsActivity extends BaseActivity implements OnClickListener{


	private GridView circleGV;

	private ArrayList<String> gridList;

	private Context context;

	private ImageGVAdapter adapter;

	private EditText circleET;

	private Button sendNoticeBT;

	private List<Bitmap> nowBmp = new ArrayList<Bitmap>();
	@Override
	protected int getLayoutId() {
		return R.layout.activity_sendnewcircle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		getTopBarView().setVisibility(View.GONE);
		circleGV = (GridView)findViewById(R.id.circleGV);
		circleET = (EditText) findViewById(R.id.circleET);
		sendNoticeBT = (Button) findViewById(R.id.sendNoticeBT);
		gridList = new ArrayList<String>();
		gridList.add("fds");
		gridList.add("fds");
		gridList.add("fds");
		gridList.add("fds");
		gridList.add("fds");
		gridList.add("fds");
		gridList.add("fds");
		gridList.add(null);
		adapter = new ImageGVAdapter();
		circleGV.setAdapter(adapter);
	}

	@Override
	public void onClick(View view) {

	}

	class ImageGVAdapter extends BaseAdapter{

		public ImageGVAdapter() {
			super();
		}

		@Override
		public int getCount() {
			return gridList.size();
		}

		@Override
		public Object getItem(int i) {
			return gridList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			Holder holder = null;
			if (view == null) {
				holder = new Holder();
				view = View.inflate(context, R.layout.gv_item_image,
						null);
				holder.imageGV = (ImageView) view
						.findViewById(R.id.imageGV);
				holder.delBT = (ImageView) view.findViewById(R.id.delBT);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			doToast("进入到adapter中");
			if (gridList.get(position) == null) {
				holder.imageGV.setImageResource(R.drawable.btn_add_pic);
				holder.delBT.setVisibility(View.INVISIBLE);
			} else {
				if (gridList.get(position) != null) {
//					holder.imageGV.setImageBitmap(nowBmp.get(position));
					holder.imageGV.setImageResource(R.drawable.btn_brand_normal);
					holder.delBT.setVisibility(View.VISIBLE);
				}
			}
			addListener(holder, position);
			return view;
		}

		/**
		 * 删除一个图片点击事件
		 * @param holder
		 * @param position
		 */
		private void addListener(Holder holder, final int position) {
			holder.delBT.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (position == gridList.size() - 1) {

					} else {
						gridList.remove(position);
						for (int i = 0; i < gridList.size(); i++) {
							if (gridList.get(i) == null) {
								gridList.remove(i);
							}
						}
						gridList.add(null);
						checkSend();
						adapter.notifyDataSetChanged();
					}
				}
			});
		}
		class Holder {
			private ImageView imageGV;
			private ImageView delBT;
		}
	}
	public void checkSend() {
		if (gridList.size() > 1
				|| circleET.getText().toString().trim().length() != 0) {
			sendNoticeBT.setClickable(true);
			sendNoticeBT.setSelected(false);
		} else {
			sendNoticeBT.setSelected(true);
			sendNoticeBT.setClickable(false);
		}
	}

}
