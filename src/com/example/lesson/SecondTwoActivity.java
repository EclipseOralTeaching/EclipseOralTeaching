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
public class SecondTwoActivity extends Activity {

    private EditText sec_two_sign;
    private ImageView btn_goback;
    private Button btn_save;
    private String sign;
    private Editable etext;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi_activity_secondtwo);
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",  Context.MODE_PRIVATE);
        sec_two_sign=(EditText)findViewById(R.id.genggaimingzi_et_sign);
        String sign1 = sharedPreferences.getString("sign1", "");
        sec_two_sign.setText(sign1);
        final  String sign=sign1;
        Editable etext = sec_two_sign.getText();
        int position = etext.length();
        Selection.setSelection(etext, position);
        sec_two_sign.addTextChangedListener(textWatch);
        btn_goback = (ImageView) findViewById(R.id.genggaimingzi_btn_goback);
        btn_goback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                Intent data = new Intent(SecondTwoActivity.this,SecondActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("sign1",sign);
                data.putExtras(bundle);
                setResult(2, data);
                finish();
            }

        });

        btn_save = (Button) findViewById(R.id.genggaimingzi_btn_save);
        btn_save.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                String str_sign=sec_two_sign.getText().toString();
                Intent data = new Intent(SecondTwoActivity.this,SecondActivity.class);
                SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("sign1",str_sign);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putString("sign1",str_sign);
                data.putExtras(bundle);
                setResult(2, data);
                finish();

            }

        });

    }
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        String sign1 = sharedPreferences.getString("sign1", "");
        final  String sign=sign1;
        Intent data = new Intent(SecondTwoActivity.this,SecondActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("sign1",sign);
        data.putExtras(bundle);
        setResult(2, data);
        finish();

    }
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
            if(sec_two_sign.getText()==null){
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

