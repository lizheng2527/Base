package com.zdhx.androidbase.ui.account;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.entity.ScroListBean;

import java.util.List;

/**
 * Created by lizheng on 2016/12/26.
 */

public class ScroListViewAdapter extends BaseAdapter {

    private List<ScroListBean> list;

    private Context context;

    private LayoutInflater inflater;

    public ScroListViewAdapter(List<ScroListBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null){
            vh = new ViewHolder();
            view = View.inflate(context,R.layout.fragment_scro_viewpager_listview_item,null);

            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        vh.ranking = (TextView) view.findViewById(R.id.fragment_scro_viewpager_listview_item_ranking);
        vh.name = (TextView) view.findViewById(R.id.fragment_scro_viewpager_listview_item_name);
        vh.scro = (TextView) view.findViewById(R.id.fragment_scro_viewpager_listview_item_scro);
        vh.upNumbers = (TextView) view.findViewById(R.id.fragment_scro_viewpager_listview_item_upnumbers);
        vh.downNumbers = (TextView) view.findViewById(R.id.fragment_scro_viewpager_listview_item_downnumbers);

        vh.ranking.setText(list.get(i).getRanking()+"");
        vh.name.setText(list.get(i).getName());
        vh.scro.setText(list.get(i).getScro());
        vh.upNumbers.setText(list.get(i).getUpNumbers());
        vh.downNumbers.setText(list.get(i).getDownNumbers());

        switch (i){
            case 0:
                vh.ranking.setBackgroundColor(Color.parseColor("#aa0000"));
                break;
            case 1:
                vh.ranking.setBackgroundColor(Color.parseColor("#ee7e00"));
                break;
            case 2:
                vh.ranking.setBackgroundColor(Color.parseColor("#f9d919"));
                break;
            default:
                vh.ranking.setBackgroundColor(Color.parseColor("#33Ccff"));
                break;
        }
        return view;
    }
    class ViewHolder{
        private TextView ranking;
        private TextView name;
        private TextView scro;
        private TextView upNumbers;
        private TextView downNumbers;
    }
}
