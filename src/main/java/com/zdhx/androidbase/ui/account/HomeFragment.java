package com.zdhx.androidbase.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.entity.Treads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizheng on 2016/12/24.
 * 主页
 */

public class HomeFragment extends Fragment {

    private GridView grid;

    private List<String> gridDatas;

    private HomeGridAdapter homeGridAdapter;

    private ViewPager vp;
    /**
     * 动态数据的四个标题源
     */
    private List<View> viewPagerListDatas;
    /**
     * 动态数据的数据源
     */
    private List<Treads> viewPagerListTreadsDatas;
    /**
     * 动态数据的标题源适配器
     */
    private HomeViewPagerAdapter homeViewPagerAdapter;
    /**
     * 动态数据的适配器
     */
    private TreadsListViewAdapter treadsListViewAdapter;


    private ListView allreadsListView;
    private ListView interactreadsTtListView;
    private ListView myTreadsListView;
    private ListView resourcesTreadsListView;
    private ListView attendsTreadsListView;
    //上下文
    private Context context;
    //碎片五个布局初始化
    private View allTreadsView ;
    private View interacttreadsTreadsView ;
    private View myTreadsView ;
    private View resourcesTreadsView ;
    private View attendsTreadsView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();


        initGridView();
        initViewPager();
    }
    /**
     * 初始化gridView
     */
    private void initGridView(){
        grid = (GridView) getView().findViewById(R.id.fragment_home_grid);
        gridDatas = new ArrayList<String>();
        gridDatas.add("全部动态");
        gridDatas.add("互动交流");
        gridDatas.add("我的动态");
        gridDatas.add("资源动态");
        gridDatas.add("我参与的");
        homeGridAdapter = new HomeGridAdapter(gridDatas,getActivity(),false);
        grid.setAdapter(homeGridAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeGridAdapter.index = i;
                grid.setAdapter(homeGridAdapter);
                vp.setCurrentItem(i);
                initTreadsDatas(i);
            }
        });
    }

    /**
     * 初始化HomeViewPager
     */
    private void initViewPager (){

        vp = (ViewPager) getView().findViewById(R.id.fragment_home_viewpager);

        //实例化布局
        allTreadsView = LayoutInflater.from(context).inflate(R.layout.fragment_home_viewpager_alltreads,null);
        interacttreadsTreadsView = LayoutInflater.from(context).inflate(R.layout.fragment_home_viewpager_interacttreads,null);
        myTreadsView = LayoutInflater.from(context).inflate(R.layout.fragment_home_viewpager_mytreads,null);
        resourcesTreadsView = LayoutInflater.from(context).inflate(R.layout.fragment_home_viewpager_resourcestreads,null);
        attendsTreadsView = LayoutInflater.from(context).inflate(R.layout.fragment_home_viewpager_attendstreads,null);
        //构造viewPager数据源
        viewPagerListDatas = new ArrayList<View>();
        viewPagerListDatas.add(allTreadsView);
        viewPagerListDatas.add(interacttreadsTreadsView);
        viewPagerListDatas.add(myTreadsView);
        viewPagerListDatas.add(resourcesTreadsView);
        viewPagerListDatas.add(attendsTreadsView);

        homeViewPagerAdapter = new HomeViewPagerAdapter(viewPagerListDatas);
        vp.setAdapter(homeViewPagerAdapter);


        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                HomeGridAdapter.index = position;
                grid.setAdapter(homeGridAdapter);
                initTreadsDatas(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //第一次加载默认为加载全部
        initTreadsDatas(0);

    }

    /**
     * 分页显示活动内容
     * @param positon
     */
    private void initTreadsDatas(int positon){
        switch (positon){
            case 0://加载全部
                allreadsListView = (ListView) allTreadsView.findViewById(R.id.fragment_home_viewpager_alltreads_listview);
                viewPagerListTreadsDatas = new ArrayList<Treads>();
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                treadsListViewAdapter = new TreadsListViewAdapter(viewPagerListTreadsDatas,context);
                allreadsListView.setAdapter(treadsListViewAdapter);
                break;
            case 1://加载互动交流
                interactreadsTtListView = (ListView) interacttreadsTreadsView.findViewById(R.id.fragment_home_viewpager_interacttreads_listview);
                viewPagerListTreadsDatas = new ArrayList<Treads>();
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                treadsListViewAdapter = new TreadsListViewAdapter(viewPagerListTreadsDatas,context);
                interactreadsTtListView.setAdapter(treadsListViewAdapter);
                break;
            case 2://加载我的动态
                myTreadsListView = (ListView) myTreadsView.findViewById(R.id.fragment_home_viewpager_myTreads_listview);
                viewPagerListTreadsDatas = new ArrayList<Treads>();
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                treadsListViewAdapter = new TreadsListViewAdapter(viewPagerListTreadsDatas,context);
                myTreadsListView.setAdapter(treadsListViewAdapter);
                break;
            case 3://加载资源动态
                resourcesTreadsListView = (ListView) resourcesTreadsView.findViewById(R.id.fragment_home_viewpager_resourcesTreads_listview);
                viewPagerListTreadsDatas = new ArrayList<Treads>();
                viewPagerListTreadsDatas.add(new Treads());
                treadsListViewAdapter = new TreadsListViewAdapter(viewPagerListTreadsDatas,context);
                resourcesTreadsListView.setAdapter(treadsListViewAdapter);
                break;
            case 4://加载我参与的
                attendsTreadsListView = (ListView) attendsTreadsView.findViewById(R.id.fragment_home_viewpager_attendsTreads_listview);
                viewPagerListTreadsDatas = new ArrayList<Treads>();
                viewPagerListTreadsDatas.add(new Treads());
                viewPagerListTreadsDatas.add(new Treads());
                treadsListViewAdapter = new TreadsListViewAdapter(viewPagerListTreadsDatas,context);
                attendsTreadsListView.setAdapter(treadsListViewAdapter);
                break;
        }


    }


}


