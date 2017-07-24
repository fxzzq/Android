package com.example.xzq.iot2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Overall_Function extends AppCompatActivity {
    private     Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_function);
        Button ziyuanBtn=(Button)findViewById(R.id.bt3);
        Button zhishenBtn=(Button)findViewById(R.id.bt7);
        TextView cn=(TextView)findViewById(R.id.cn);
        intent=getIntent();
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        String username=bundle.getString("username");
        cn.setText(username);



        ziyuanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                 intent=new Intent(Overall_Function.this,Resource_Management.class);
                startActivity(intent);
            }
        });

        zhishenBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                intent=new Intent(Overall_Function.this,Own_Management.class);
                startActivity(intent);
            }
        });


    }
}
