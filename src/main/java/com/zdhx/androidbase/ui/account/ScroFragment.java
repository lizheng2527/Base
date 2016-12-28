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
import com.zdhx.androidbase.entity.ScroListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizheng on 2016/12/24.
 * 主页
 */

public class ScroFragment extends Fragment {

    private GridView grid;

    private ArrayList<String> gridListDatas;

    private ScroGridAdapter gridAdapter;

    private Context context;

    private HomeViewPagerAdapter scroViewPagerAdapter;

    private ViewPager vp;

    private View studentScro;
    private View teacherScro;

    private List<View> viewPagerListDatas;

    private ListView studentLv;
    private ListView teacherLv;

    private ArrayList<ScroListBean> studentListDatas;
    private ArrayList<ScroListBean> teacherListDatas;

    private ScroListViewAdapter scroListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scro, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initGridView ();
        initScroViewPager();
    }

    /**
     * 初始化教师、学生ViewPager
     */
    private void initScroViewPager(){
        vp = (ViewPager) getView().findViewById(R.id.fragment_scro_viewpager);
        studentScro = LayoutInflater.from(context).inflate(R.layout.fragment_scro_viewpager_student,null);
        teacherScro = LayoutInflater.from(context).inflate(R.layout.fragment_scro_viewpager_teacher,null);
        studentLv = (ListView) studentScro.findViewById(R.id.fragment_scro_viewpager_student_listview);
        teacherLv = (ListView) teacherScro.findViewById(R.id.fragment_scro_viewpager_teacher_listview);
        viewPagerListDatas = new ArrayList<View>();
        viewPagerListDatas.add(studentScro);
        viewPagerListDatas.add(teacherScro);
        scroViewPagerAdapter = new HomeViewPagerAdapter(viewPagerListDatas);
        vp.setAdapter(scroViewPagerAdapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ScroGridAdapter.index = position;
                grid.setAdapter(gridAdapter);
                initScroDatas(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initScroDatas(0);
    }

    /**
     * 加载学生、教师积分数据
     * @param position
     */
    private void initScroDatas(int position){
        switch (position){
            case 0:
                studentListDatas = new ArrayList<ScroListBean>();
                studentListDatas.add(new ScroListBean(1,"教师1","259.1","456","54"));
                studentListDatas.add(new ScroListBean(2,"教师2","259.1","456","54"));
                studentListDatas.add(new ScroListBean(3,"教师3","259.1","456","54"));
                studentListDatas.add(new ScroListBean(4,"教师4","259.1","456","54"));
                studentListDatas.add(new ScroListBean(5,"教师5","259.1","456","54"));
                studentListDatas.add(new ScroListBean(6,"教师6","259.1","456","54"));
                studentListDatas.add(new ScroListBean(7,"教师7","259.1","456","54"));
                studentListDatas.add(new ScroListBean(8,"教师8","259.1","456","54"));
                scroListViewAdapter = new ScroListViewAdapter(studentListDatas,context);
                studentLv.setAdapter(scroListViewAdapter);

                break;
            case 1:
                teacherListDatas = new ArrayList<ScroListBean>();
                teacherListDatas.add(new ScroListBean(1,"学生1","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(2,"学生2","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(3,"学生3","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(4,"学生4","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(5,"学生5","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(6,"学生6","259.1","456","54"));
                teacherListDatas.add(new ScroListBean(7,"学生7","259.1","456","54"));
                scroListViewAdapter = new ScroListViewAdapter(teacherListDatas,context);
                teacherLv.setAdapter(scroListViewAdapter);
                break;
        }
    }
    /**
     * 初始化GridView（教师、学生）
     */
    private void initGridView (){
        grid = (GridView) getView().findViewById(R.id.fragment_scro_grid);
        gridListDatas = new ArrayList<String>();
        gridListDatas.add("教师");
        gridListDatas.add("学生");
        gridAdapter = new ScroGridAdapter(gridListDatas,context,false);
        grid.setAdapter(gridAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScroGridAdapter.index = i;
                grid.setAdapter(gridAdapter);
                vp.setCurrentItem(i);
            }
        });
    }
}
