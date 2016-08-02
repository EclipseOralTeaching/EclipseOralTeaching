package com.example.lesson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson.R;

import java.util.List;

public class SlidingItemListViewAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<SlidingItembean> list;

    private int mRightViewWidth;

    public SlidingItemListViewAdapter(Context mContext,
                                      List<com.example.lesson.SlidingItembean> list, int mRightViewWidth) {
        super();
        this.mContext = mContext;
        this.list = list;
        this.mRightViewWidth = mRightViewWidth;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        onClick listener;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_sliding_listview,
                    null);
            viewHolder = new ViewHolder();
            listener = new onClick();
            viewHolder.Re_left = (RelativeLayout) convertView
                    .findViewById(R.id.sliding_Re_left);
            viewHolder.ll_right = (LinearLayout) convertView
                    .findViewById(R.id.sliding_ll_right);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.sliding_tv_name);
            viewHolder.image =(ImageView)convertView.findViewById(R.id.sliding_ib_user);
            viewHolder.path = (TextView) convertView
                    .findViewById(R.id.sliding_time);
            viewHolder.play = (ImageView) convertView
                    .findViewById(R.id.sliding_iv_attention);
            viewHolder.setTop= (TextView) convertView.findViewById(R.id.sliding_tv_setTop);
            viewHolder.ll_delete = (LinearLayout) convertView
                    .findViewById(R.id.sliding_ll_delete_ll_right);
            viewHolder.ll_setTop = (LinearLayout) convertView
                    .findViewById(R.id.sliding_ll_setTop_ll_right);
            viewHolder.ll_setTop.setOnClickListener(listener);
            viewHolder.ll_delete.setOnClickListener(listener);
            viewHolder.play.setOnClickListener(listener);
            convertView.setTag(viewHolder.play.getId(), listener);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            listener = (onClick) convertView.getTag(viewHolder.play.getId());// 获取实例
        }

        listener.setPosition(position);

      

        LayoutParams lp_left = new LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.Re_left.setLayoutParams(lp_left);

        LayoutParams lp_right = new LayoutParams(mRightViewWidth,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.ll_right.setLayoutParams(lp_right);

        com.example.lesson.SlidingItembean slidingItembean = list.get(position);
        //   viewHolder.num.setText(slidingItembean.getNum());
        viewHolder.image.setImageResource(slidingItembean.getImage());
        viewHolder.name.setText(slidingItembean.getName());
        viewHolder.path.setText(slidingItembean.getPath());
        viewHolder.setTop.setText(slidingItembean.getSetTop());

        return convertView;
    }

    class onClick implements OnClickListener {

        int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sliding_iv_attention:
                    Toast.makeText(mContext, "play--->position=" + position,
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.sliding_ll_delete_ll_right:
                    list.remove(position);
                    SlidingItemListViewAdapter.this.notifyDataSetChanged();

                    break;
                case R.id.sliding_ll_setTop_ll_right:

                    if (mySetTopInterface!=null) {
                        mySetTopInterface.Onclick_ll_setTop_ll_right(v,position);
                    }else {
                        Toast.makeText(mContext, "mySetTopInterface==null",
                                Toast.LENGTH_SHORT).show();
                    }

                    break;

                default:
                    break;
            }

        }
    }

    MySetTopInterface mySetTopInterface;

    public interface MySetTopInterface {
        void Onclick_ll_setTop_ll_right(View view,int position);
    }

    public void setMySetTopInterface(MySetTopInterface mySetTopInterface) {
        this.mySetTopInterface = mySetTopInterface;
    }

    static class ViewHolder {

        RelativeLayout Re_left;
        LinearLayout ll_right;

        LinearLayout ll_delete;
        LinearLayout ll_setTop;

        //        TextView num;
        TextView name;
        TextView path;
        ImageView image;
        ImageView play;

        TextView setTop;
    }

}
