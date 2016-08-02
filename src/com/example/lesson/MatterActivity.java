package com.example.lesson;

import android.app.Activity;
import android.graphics.AvoidXfermode;
import android.os.Bundle;

import com.example.lesson.AddNumberBaseAdpater;
import com.example.lesson.CustomRefreshListView;
import com.example.lesson.Main2Activity;
import com.example.lesson.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MatterActivity extends Activity {

    //    private ListView myList; // ListView�ؼ�
    private AddNumberBaseAdpater addNumberBaseAdpater;
    private static CustomRefreshListView mLv;
    private ImageButton add_Gallery;
    //titlebar
    private View titlebar_ll_backBTN;
    private TextView activitymain2_tv_title;
    private TextView activitymain2_tv_editTitle;


//    private List<Gallery> datas = new ArrayList<Gallery>();
//    private List<String> name = new ArrayList<String>();
//    private List<String> amount = new ArrayList<String>();

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.matteractivity);
        //��ʼ��titlebar
        titlebar_ll_backBTN=findViewById(R.id.titlebar_ll_backBTN);
        activitymain2_tv_editTitle= (TextView) findViewById(R.id.activitymain_tv_editTitle);
        activitymain2_tv_title= (TextView) findViewById(R.id.activitymain_tv_title);
        /** �༭�� **/
        activitymain2_tv_editTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MatterActivity.this,"������ɾ���زļ�",Toast.LENGTH_SHORT).show();
            }
        });
        /** ���ؼ� **/
        titlebar_ll_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //���ö�̬ListView
        addNumberBaseAdpater = new AddNumberBaseAdpater(MatterActivity.this);
//        myList = (ListView)findViewById(R.id.listview_main);
//        myList.setAdapter(addNumberBaseAdpater);

        //����CustomRefreshListView
//        View view_list=View.inflate(MainActivity.this,R.layout.activity_main,null);
        mLv = ((CustomRefreshListView) findViewById(R.id.listview_main));

//        addNumberBaseAdpater = new AddNumberBaseAdpater(this);

        mLv.setAdapter(addNumberBaseAdpater);

        mLv.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {

            }

            @Override
            public void onLoadingMore() {

            }
        });
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedPosition = adapterView.getSelectedItemPosition();
                Toast.makeText(MatterActivity.this,""+selectedPosition,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("name",selectedPosition);//��ֵ
                intent.setClass(MatterActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
//���·���д��AddNumberBaseAdapter.java����
        add_Gallery=(ImageButton)findViewById(R.id.iV_addGallery_main);
        add_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //����AlertDialog
//                AlertDialogNewGallery alertDialogNewGallery;
                LayoutInflater alertInflate=LayoutInflater.from(MatterActivity.this);
                final View alertView=alertInflate.inflate(R.layout.new_gallery_alertdialog,null);
                AlertDialog.Builder alertbuilder=new AlertDialog.Builder(MatterActivity.this);
                alertbuilder.setTitle("�½��زļ�").setView(alertView)
                        .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText name_ET=(EditText)alertView.findViewById(R.id.newGalleryAlertDialog_et_prompt);
                                Toast.makeText(MatterActivity.this,"�زļн����ɹ�,���زļе�������"+name_ET.getText().toString(),Toast.LENGTH_SHORT).show();
                                addNumberBaseAdpater.mlist.add(name_ET.getText().toString());
                                addNumberBaseAdpater.mamount.add("0"+"��");



                            }
                        })
                        .setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MatterActivity.this,"���زļн���ʧ�ܣ�",Toast.LENGTH_LONG).show();
                            }
                        });
                //����show()������ʾAlertDialog
                alertbuilder.show();
            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
    }
}

