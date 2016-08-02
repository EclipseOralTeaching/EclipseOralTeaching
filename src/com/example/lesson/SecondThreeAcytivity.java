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
public class SecondThreeAcytivity extends Activity {
    private EditText sec_three_sign;
    private ImageView btn_goback;
    private Button btn_save;
    private String sign;
    private Editable etext;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi_activity_secondthree);
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",  Context.MODE_PRIVATE);
        sec_three_sign=(EditText)findViewById(R.id.gengaidianhuahaoma_et_sign);
        String sign1 = sharedPreferences.getString("sign2", "");
        sec_three_sign.setText(sign1);
        final  String sign=sign1;
        Editable etext = sec_three_sign.getText();
        int position = etext.length();
        Selection.setSelection(etext, position);
        sec_three_sign.addTextChangedListener(textWatch);
        btn_goback = (ImageView) findViewById(R.id.gengaidianhuahaoma_btn_goback);
        btn_goback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                Intent data = new Intent(SecondThreeAcytivity.this,SecondActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("sign2",sign);
                data.putExtras(bundle);
                setResult(3, data);
                finish();
            }

        });

        btn_save = (Button) findViewById(R.id.gengaidianhuahaoma_btn_save);
        btn_save.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                String str_sign=sec_three_sign.getText().toString();
                Intent data = new Intent(SecondThreeAcytivity.this,SecondActivity.class);
                SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("sign2",str_sign);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putString("sign2",str_sign);
                data.putExtras(bundle);
                setResult(3, data);
                finish();
            }

        });

    }
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        String sign1 = sharedPreferences.getString("sign2", "");
        final  String sign=sign1;
        Intent data = new Intent(SecondThreeAcytivity.this,SecondActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("sign2",sign);
        data.putExtras(bundle);
        setResult(3, data);
        finish();

    }
    private TextWatcher textWatch=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:�仯ǰ�������ַ��� start:�ַ���ʼ��λ�ã� count:�仯ǰ�����ֽ�����after:�仯����ֽ���

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S���仯��������ַ���start���ַ���ʼ��λ�ã�before: �仯֮ǰ�����ֽ�����count:�仯����ֽ���

        }
        @Override
        public void afterTextChanged(Editable s) {
            //s:�仯��������ַ�
            if(sec_three_sign.getText()==null){
                //���ð�ť���ɵ��
                btn_save.setClickable(false);
                //���ð�ťΪ����״̬
                btn_save.setPressed(false);
            }else{
                //���ð�ť�ɵ��
                btn_save.setClickable(true);
                //���ð�ťΪ����״̬
                btn_save.setPressed(true);
            }
        }
    };

}

