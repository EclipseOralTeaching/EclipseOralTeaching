package com.example.lesson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.lesson.R;

import java.util.ArrayList;

/**
 * Created by TianYuan on 2016/7/14.
 */
public class AddNumberBaseAdpater extends BaseAdapter {

    public ArrayList<String> mlist;
    public ArrayList<String> mamount;
    private LayoutInflater minflater;
    private Context context;

    public AddNumberBaseAdpater(Context context){
        mlist=new ArrayList<String>();
        mamount=new ArrayList<String>();
        this.context=context;
        minflater= LayoutInflater.from(this.context);
    }

        public int getCount() {
        // TODO Auto-generated method stub
        return mlist.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mlist.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.gallery_listview, null);


            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        switch (getCount()){
            case 0:
                View viewMain= View.inflate(context.getApplicationContext(), R.layout.activity_main,null);//娉ㄦ剰getApplicationContext()鐨勭敤娉�
                viewMain.findViewById(R.id.iV_backMain_main).setVisibility(View.VISIBLE);break;
            default:
                View viewMaind= View.inflate(context.getApplicationContext(), R.layout.activity_main,null);
                viewMaind.findViewById(R.id.iV_backMain_main).setVisibility(View.INVISIBLE);break;
        }
        return convertView;
    }

}

final class ViewHolder {
    public TextView name;
    public TextView amount;
    public RelativeLayout whole;

}
