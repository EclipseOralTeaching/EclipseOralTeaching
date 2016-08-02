package com.example.lesson;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson.R;
import com.example.lesson.FifthActivity;
import com.example.lesson.ForthActivity;
import com.example.lesson.SecondActivity;
import com.example.lesson.SixthActivity;
import com.example.lesson.ThirdActivity;
import com.example.lesson.CustomImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SetActivity extends Activity {
    private ImageView btn_goback;
    private  ImageView btn_toback;
    private RelativeLayout one;
    private RelativeLayout two;
    private RelativeLayout three;
    private RelativeLayout four;
    private RelativeLayout five;
    TextView name,number;
    private ImageView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setactivity);
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        head=(ImageView)findViewById(R.id.shezhi_iv_head);
        name=(TextView)findViewById(R.id.shezhi_tv_name);
        number=(TextView)findViewById(R.id.shezhi_tv_number);
        String sign1 = sharedPreferences.getString("sign1", "");
        name.setText(sign1);
        String sign2 = sharedPreferences.getString("sign2", "");
        number.setText(sign2);

        //从本地读取图片
        Bitmap bm=  getLoacalBitmap("/sdcard/formats/Image.JPEG");//FilePath为图片路径及名字
        head.setImageBitmap(bm);
        btn_goback = (ImageView) findViewById(R.id.shezhi_iv_goback);
        btn_goback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                finish();
            }

        });

        btn_toback = (ImageView) findViewById(R.id.shezhi_btn_toback);
        btn_toback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, SecondActivity.class);
                startActivity(intent);

            }


        });
        one=(RelativeLayout)findViewById(R.id.shezhi_rl_dashang);
        one.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, ThirdActivity.class);
                startActivity(intent);
            }


        });
        two=(RelativeLayout)findViewById(R.id.shezhi_rl_dashangjilu);
        two.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, ForthActivity.class);
                startActivity(intent);
            }


        });
        three=(RelativeLayout)findViewById(R.id.shezhi_rl_shenfenrenzheng);
        three.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, FifthActivity.class);
                startActivity(intent);
            }
        });
        four=(RelativeLayout)findViewById(R.id.shezhi_rl_help);
        four.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, SixthActivity.class);
                startActivity(intent);
            }
        });

        five=(RelativeLayout)findViewById(R.id.shezhi_rl_delete);
        five.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplication(), "已清除", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
    //从本地读取图片
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    //activity 生命周期
    public void onResume()
    {
        head=(ImageView)findViewById(R.id.shezhi_iv_head);
        //从本地读取图片
        Bitmap bm=  getLoacalBitmap("/sdcard/formats/Image.JPEG");//FilePath为图片路径及名字
        head.setImageBitmap(CustomImageView.makeRoundCorner(bm));
        //从本地读取数据
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        name=(TextView)findViewById(R.id.shezhi_tv_name);
        number=(TextView)findViewById(R.id.shezhi_tv_number);
        String sign1 = sharedPreferences.getString("sign1", "");
        name.setText(sign1);
        String sign2 = sharedPreferences.getString("sign2", "");
        number.setText(sign2);
        super.onResume();
    }

}

