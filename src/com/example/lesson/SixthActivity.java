package com.example.lesson;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lesson.R;

/**
 * Created by lenovo on 2016/7/26.
 */
public class SixthActivity extends Activity {
    private ImageView btn_goback;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi_activity_six);
        btn_goback = (ImageView) findViewById(R.id.help_btn_goback);
        btn_goback.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                finish();
            }

        });

    }
}
