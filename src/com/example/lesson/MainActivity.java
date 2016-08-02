package com.example.lesson;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener,SlidingItemListViewAdapter.MySetTopInterface{
	 private SlidingMenu menu;
	    private LinearLayout mmain;
	    private LinearLayout mmatter;
	    private LinearLayout mset;
	    private LinearLayout mabout;
	    private ImageView muserimage;
	    private Button mlogin;

	    private SlidingItemListView mListView;
	    private SlidingItemListViewAdapter adapter;
	    private List<com.example.lesson.SlidingItembean> list = new ArrayList<SlidingItembean>();

	    private Button btn01;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

	        initmenu();
	        initfindView();
	        initData();
	        initEvent();
	    }

	    private void initmenu(){
            menu=new SlidingMenu(this);
	        menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	        menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 5);
	        menu.setFadeDegree(0.35f);
	        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	        menu.setMenu(R.layout.leftmenu_frame);
	    }

	    private void initfindView(){

	        mmain = (LinearLayout)findViewById(R.id.leftmenu_ll_mainmenu);
	        mmatter = (LinearLayout)findViewById(R.id.leftmenu_ll_matter);
	        mset = (LinearLayout)findViewById(R.id.leftmenu_ll_set);
	        mabout = (LinearLayout)findViewById(R.id.leftmenu_ll_about);
	        mlogin = (Button)findViewById(R.id.leftmenu_bu_login);
	        muserimage = (ImageView) findViewById(R.id.main002_btn_user);
	        btn01 = (Button)findViewById(R.id.main002_btn_add);
	        mListView = (SlidingItemListView) findViewById(R.id.main002_lv_listview);

	        mmain.setOnClickListener(this);
	        mmatter.setOnClickListener(this);
	        mset.setOnClickListener(this);
	        mabout.setOnClickListener(this);
	        mlogin.setOnClickListener(this);
	        muserimage.setOnClickListener(this);
	        btn01.setOnClickListener(this);

	    }

	    @Override
	    public void onClick(View v) {
	        Intent intent = new Intent();
	        switch(v.getId()){
	            case R.id.leftmenu_ll_mainmenu:
	                intent.setClass(MainActivity.this, MenuActivity.class);
	                startActivity(intent);
	                break;
	            case R.id.leftmenu_ll_set:
	                intent.setClass(MainActivity.this, SetActivity.class);
	                startActivity(intent);
	                break;
	            case R.id.leftmenu_ll_matter:
	                intent.setClass(MainActivity.this, MatterActivity.class);
	                startActivity(intent);
	                break;
	            case R.id.leftmenu_ll_about:
	                intent.setClass(MainActivity.this, AboutActivity.class);
	                startActivity(intent);
	                break;
	            case R.id.leftmenu_bu_login:
	                intent.setClass(MainActivity.this, MenuActivity.class);
	                startActivity(intent);
	                break;
	            case R.id.main002_btn_user:
	                if(!menu.isMenuShowing())
	                    menu.showMenu();
	                else
	                    menu.showContent();
	                break;
	            case R.id.main002_btn_add:
	                intent.setClass(MainActivity.this,AddActivity.class);
	                startActivity(intent);
	                break;
	            default:
	                return;
	        }

	    }

	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (menu.isMenuShowing()) {
	            menu.showContent();
	            return false;
	        }

	        else
	            return super.onKeyDown(keyCode, event);
	    }


	    private void initEvent() {
	    	 adapter = new SlidingItemListViewAdapter(MainActivity.this, list,
	                 mListView.getRightViewWidth());
	        mListView.setAdapter(adapter);
	        adapter.setMySetTopInterface(this);
	        // mListView.setSelection(position);

	        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	                Toast.makeText(MainActivity.this,
	                        "item onclick " + list.get(position).getNum(),
	                        Toast.LENGTH_SHORT).show();
	            }
	        });
	    }

	    private void initData() {
	        for (int i = 0; i <10; i++) {
	            SlidingItembean slidingItembean = null;
	            if (i % 3 == 0) {
	                slidingItembean = new SlidingItembean(String.valueOf(i),R.drawable.jack,
	                        "Jim", "����",
	                        "�ö�");
	            } else if (i % 3 == 1) {
	                slidingItembean = new SlidingItembean(String.valueOf(i),R.drawable.lily,
	                        "Lucy", "����",
	                        "�ö�");
	            } else {
	                slidingItembean = new SlidingItembean(String.valueOf(i),R.drawable.lucy,
	                        "Jack", "����",
	                        "�ö�");
	            }

	            list.add(slidingItembean);
	        }
	    }


	    @Override
	    public void Onclick_ll_setTop_ll_right(View view, int position) {

	        if (list.get(position).getSetTop().equals("�ö�")) {

	            setTop(position);

	        } else if (list.get(position).getSetTop().equals("ȡ���ö�")) {

	            unSetTop(position);

	        }

	    }

	    /**
	     * ȡ���ö�
	     * @param position
	     */
	    private void unSetTop(int position) {
	        boolean isAdd = false;
	        /** ��ֵ */
	        int min = 9999999;
	        /** ��ǰposition����ֵ */
	        int num;
	        // ��ֵ��С��������
	        int j = 0;
	        int num2 = 0;
	        int jumpNum = 0;
	        list.get(position).setSetTop("�ö�");
	        num = Integer.parseInt(list.get(position).getNum());
	        // list����Ϊ2���⴦��
	        if (list.size() == 2) {
	            // ��һ��ȷ��Ϊȡ���ö�
	            if (list.get(1).getSetTop().equals("ȡ���ö�")) {
	                if (position == 0) {
	                    if (num == 0) {
	                        list.add(2, list.get(position));
	                    }
	                    if (num == 1) {
	                        list.add(2, list.get(position));
	                    }
	                    list.remove(position);
	                    adapter.notifyDataSetChanged();
	                } else {
	                    list.add(2, list.get(position));
	                    list.remove(position);
	                    adapter.notifyDataSetChanged();
	                }
	            } else {
	                if (num == 0) {
	                    list.add(1, list.get(position));
	                }
	                if (num == 1) {
	                    list.add(2, list.get(position));
	                }
	                list.remove(position);
	                adapter.notifyDataSetChanged();
	            }
	        } else {

	            for (int i = 0; i < list.size(); i++) {

	                if (num > Integer.parseInt(list.get(i).getNum())
	                        && num < Integer.parseInt(list.get(i + 1).getNum())) {
	                    list.add(i + 1, list.get(position));
	                    isAdd = true;
	                    break;
	                }
	            }

	            // ���û�б��Լ�С��ֵ ����0 ��isAdd=false
	            // ����list Ѱ�Ҳ�ֵ��С�ĵط�����list
	            if (!isAdd) {
	                for (int i = 0; i < list.size(); i++) {

	                    if (i == position || list.get(i).getSetTop().equals("ȡ���ö�")) {
	                        // �ų���������Ƚ�
	                       
	                        Log.i("TAG", "����" + i);
	                        jumpNum++;
	                        if (jumpNum == list.size()) {
	                            j = list.size();
	                        }

	                        continue;
	                    }

	                    num2 = Integer.parseInt(list.get(i).getNum());
	                    if (num2 - num < min) {
	                        min = num2 - num;
	                        // ��¼�к�
	                        j = i;
	                        Log.i("TAG", "��������J=" + j);
	                    }
	                }
	                // ������ɺ��õ���ֵmin
	                int number = min + num;
	                list.add(j, list.get(position));
	                Log.i("TAG", "*********��������J=" + j);
	            }

	            list.remove(position);
	            adapter.notifyDataSetChanged();
	        }

	    }

	    /**
	     * �ö�
	     * @param position
	     */
	    private void setTop(int position) {
	        list.get(position).setSetTop("ȡ���ö�");
	        list.add(0, list.get(position));
	        // �ö���list.size����һ ����Ҫposition+1
	        list.remove(position + 1);
	        adapter.notifyDataSetChanged();
	    }
	}