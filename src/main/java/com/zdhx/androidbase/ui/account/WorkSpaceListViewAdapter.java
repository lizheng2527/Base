package com.zdhx.androidbase.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.entity.WorkSpaceDatasBean;

import java.util.List;

/**
 * Created by lizheng on 2016/12/26.
 */

public class WorkSpaceListViewAdapter extends BaseAdapter {

    private List<WorkSpaceDatasBean> list;

    private Context context;

    private LayoutInflater inflater;

    public WorkSpaceListViewAdapter(List<WorkSpaceDatasBean> list, Context context) {
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
        View item = view ;
        ViewHolder vh = null;
        if (view == null){
            vh = new ViewHolder();
            view = View.inflate(context,R.layout.fragment_workspace_listview_item,null);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{

    }
}
