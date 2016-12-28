package com.zdhx.androidbase.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdhx.androidbase.R;
import com.zdhx.androidbase.entity.Treads;
import com.zdhx.androidbase.util.RoundCornerImageView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lizheng on 2016/12/26.
 */

public class TreadsListViewAdapter extends BaseAdapter {

    private List<Treads> list;

    private Context context;

    private LayoutInflater inflater;

    public TreadsListViewAdapter(List<Treads> list, Context context) {
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
            view = View.inflate(context,R.layout.fragment_home_viewpager_listview_item,null);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        /*** 展示动态的用户头像*/
        private RoundCornerImageView threadUserHead;
        /*** 展示动态的用户名称*/
        private TextView threadUserName;
        /*** 展示动态的用户类型（教师/学生）*/
        private TextView threadUserType;
        /*** 删除*/
        private ImageView delete;
        /*** 展示动态的用户文字内容*/
        private TextView threadTextMessage;
        /*** 单张展示的图片*/
        private ImageView simpleImage;
        /*** 多张展示的图片*/
        private GridView muchImages;
        /*** 展示动态的文件图片*/
        private RoundCornerImageView threadFileHead;
        /*** 展示动态的文件标题*/
        private Text threadFileTitle;
        /*** 展示动态的文件大小*/
        private Text threadFileSpace;
        /*** 展示动态的发送时间*/
        private Text threadDate;
        /*** 展示动态的点赞图片*/
        private ImageView threadThumbImage;
        /*** 展示动态的点赞个数*/
        private TextView threadThumbNumbers;
        /*** 展示动态的评论图片*/
        private ImageView threadCommentImage;
        /*** 展示动态的评论个数*/
        private TextView threadCommentNumbers;
        /*** 输入动态的评论内容*/
        private EditText threadCommentMessageEt;
        /*** 展示动态的评论内容*/
        private TextView threadCommentMessageTv;


    }
}
