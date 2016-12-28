package com.zdhx.androidbase.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.entity.GridListBean;
import com.zdhx.androidbase.entity.WorkSpaceDatasBean;

import java.util.ArrayList;

/**
 * Created by lizheng on 2016/12/24.
 * 主页
 */

public class WorkSpaceFragment extends Fragment {
    //workSpace加载的gridView
    private GridView gridView;

    private ArrayList gridList;

    private WorkSpaceGridAdapter workSpaceGridAdapter;

    private Context context;

    private ListView lv;

    private ArrayList<WorkSpaceDatasBean> list;

    private WorkSpaceListViewAdapter workSpaceListViewAdapter;

    private int listViewIndexConut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workspace, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initGridView();
        initListView();
    }
    /**
     * 初始化listView数据源及展示
     */
    private void initListView(){
        lv = (ListView) getView().findViewById(R.id.fragment_workspace_listview);
        ListViewDatas(0);
    }
    /**
     * 初始化GridView布局
     */
    private void initGridView(){
        gridView = (GridView) getView().findViewById(R.id.fragment_workspace_grid);
        gridList = new ArrayList();
        gridList.add(new GridListBean("全部",0));
        gridList.add(new GridListBean("图片",R.drawable.ic_launcher));
        gridList.add(new GridListBean("视频",R.drawable.ic_launcher));
        gridList.add(new GridListBean("PPT",R.drawable.ic_launcher));
        gridList.add(new GridListBean("Word",R.drawable.ic_launcher));
        gridList.add(new GridListBean("音频",R.drawable.ic_launcher));
        gridList.add(new GridListBean("Excel",R.drawable.ic_launcher));
        gridList.add(new GridListBean("PDF",R.drawable.ic_launcher));
        gridList.add(new GridListBean("压缩包",R.drawable.ic_launcher));
        gridList.add(new GridListBean("其他",R.drawable.ic_launcher));
        workSpaceGridAdapter = new WorkSpaceGridAdapter(gridList,context,false);
        initGridViewWidth();
        gridView.setAdapter(workSpaceGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listViewIndexConut == i){//两次点击的按钮一样返回
                    return;
                }
                workSpaceGridAdapter.index = i;
                listViewIndexConut = i;
                gridView.setAdapter(workSpaceGridAdapter);
                ListViewDatas(i);
            }
        });
    }

    /**
     * 更改listView内的数据源
     */
    private void ListViewDatas(int i){
        if (list == null){
            list = new ArrayList<WorkSpaceDatasBean>();
        }else{
            list.clear();
        }
        for (int j = 0; j < i; j++) {
            list.add(new WorkSpaceDatasBean());
        }
        workSpaceListViewAdapter = new WorkSpaceListViewAdapter(list,context);
        lv.setAdapter(workSpaceListViewAdapter);
        listViewIndexConut = i;
    }

    /**
     * 加载gridView的宽度
     */
    private void initGridViewWidth(){
        int size = gridList.size();
        int length = 80;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(0); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
    }
}
