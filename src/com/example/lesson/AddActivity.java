package com.example.lesson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson.MainActivity;
import com.example.lesson.R;

@SuppressLint("NewApi")
public class AddActivity extends Activity {
    private ImageView ivDeleteText;
    private EditText etSearch;
    private EditText et_search;
    private TextView tv_tip;
    private SearchListView listView;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private SQLiteDatabase db;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);

        initView();


        //back���ذ�ť�¼�
        Button back = (Button)findViewById(R.id.add_btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // ���������ʷ
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });


        //�����ť����
        Button btn_search = (Button)findViewById(R.id.add_btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                // �����������󽫵�ǰ��ѯ�Ĺؼ��ֱ�������,����ùؼ����Ѿ����ھͲ�ִ�б���
                boolean hasData = hasData(et_search.getText().toString().trim());
                if (!hasData) {
                    insertData(et_search.getText().toString().trim());
                    queryData("");
                }
                // TODO �������������ģ����ѯ��Ʒ������ת����һ�����棬�����Լ�ȥʵ��
                Toast.makeText(AddActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // ������ļ�������������ص�
//        et_search.setOnKeyListener(new View.OnKeyListener() {// ������󰴼����ϵ�������
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// �޸Ļس�������
//                    // �����ؼ���
//                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    // �����������󽫵�ǰ��ѯ�Ĺؼ��ֱ�������,����ùؼ����Ѿ����ھͲ�ִ�б���
//                    boolean hasData = hasData(et_search.getText().toString().trim());
//                    if (!hasData) {
//                        insertData(et_search.getText().toString().trim());
//                        queryData("");
//                    }
//                    // TODO �������������ģ����ѯ��Ʒ������ת����һ�����棬�����Լ�ȥʵ��
//                    Toast.makeText(AddActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
//
//                }
//                return false;
//            }
//        });

        // ��������ı��仯ʵʱ����
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("������ʷ");
                } else {
                    tv_tip.setText("�������");
                }
                String tempName = et_search.getText().toString();
                // ����tempNameȥģ����ѯ���ݿ�����û������
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                Toast.makeText(AddActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO ��ȡ��item��������֣����ݸùؼ�����ת����һ��ҳ���ѯ�������Լ�ȥʵ��
            }
        });

        // �������ݣ����ڲ��ԣ������һ�ν���û��������ô����ѽ��
//        Date date = new Date();
//        long time = date.getTime();
//        insertData("Leo" + time);

        // ��һ�ν����ѯ���е���ʷ��¼
        queryData("");




        //����һ��ɾ������
        ivDeleteText = (ImageView) findViewById(R.id.add_iv_DeleteText);
        etSearch = (EditText) findViewById(R.id.add_et_search);

        ivDeleteText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * ��������
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * ģ����ѯ����
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // ����adapter����������
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // ����������
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //�ж��Ƿ�����һ��
        return cursor.moveToNext();
    }

    /**
     * �������
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.add_et_search);
        tv_tip = (TextView) findViewById(R.id.add_tv_tip);
        listView = (SearchListView)findViewById(R.id.add_lv_listView);
        tv_clear = (TextView) findViewById(R.id.add_tv_clear);

        // ����EditText��ߵ�������ť�Ĵ�С
//        Drawable drawable = getResources().getDrawable(R.drawable.search);
//        drawable.setBounds(0, 0, 60, 60);// ��һ0�Ǿ���߾��룬�ڶ�0�Ǿ��ϱ߾��룬60�ֱ��ǳ���
//        et_search.setCompoundDrawables(drawable, null, null, null);// ֻ�����
    }



}

