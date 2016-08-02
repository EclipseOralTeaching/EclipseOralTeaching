package com.example.lesson;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lesson.R;

/**
 * Created by lenovo on 2016/7/26.
 */
public class SecondFourActivity extends Activity {
    private EditText sec_four_sign;
    private ImageView btn_goback;
    private Button btn_save;
    private String sign;
    private Editable etext;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //读取数据
        setContentView(R.layout.shezhi_activity_secondfour);
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",  Context.MODE_PRIVATE);
        String sign1 = sharedPreferences.getString("sign3", "");
        sec_four_sign=(EditText)findViewById(R.id.gerenjianjie_et_sign);
        sec_four_sign.setText(sign1);
        final  String sign=sign1;
        //读取光标位置
        Editable etext = sec_four_sign.getText();
        int position = etext.length();
        Selection.setSelection(etext, position);

        sec_four_sign.addTextChangedListener(textWatch);
        btn_goback = (ImageView) findViewById(R.id.gerenjianjie_btn_goback);
        btn_goback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                //传值
                Intent data = new Intent(SecondFourActivity.this,SecondActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("sign3",sign);
                data.putExtras(bundle);
                setResult(4, data);
                finish();
            }

        });

        btn_save = (Button) findViewById(R.id.gerenjianjie_btn_save);
        btn_save.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                String str_sign=sec_four_sign.getText().toString();
                Intent data = new Intent(SecondFourActivity.this,SecondActivity.class);
                SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("sign3",str_sign);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putString("sign3",str_sign);
                data.putExtras(bundle);
                setResult(4, data);
                finish();
            }

        });
    }
    //手机返回键设置
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        String sign1 = sharedPreferences.getString("sign3", "");
        final  String sign=sign1;
        Intent data = new Intent(SecondFourActivity.this,SecondActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("sign3",sign);
        data.putExtras(bundle);
        setResult(4, data);
        finish();

    }
    //Edittext输入按钮可点击
    private TextWatcher textWatch=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数

        }
        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
            if(sec_four_sign.getText()==null){
                //设置按钮不可点击
                btn_save.setClickable(false);
                //设置按钮为按下状态
                btn_save.setPressed(false);
            }else{
                //设置按钮可点击
                btn_save.setClickable(true);
                //设置按钮为正常状态
                btn_save.setPressed(true);
            }
        }
    };
}