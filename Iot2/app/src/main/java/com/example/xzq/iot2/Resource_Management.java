package com.example.xzq.iot2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Resource_Management extends AppCompatActivity {
        private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_management);
        Button nodeData_show=(Button)findViewById(R.id.nodeData_show);
        Button Data_real_time_show=(Button)findViewById(R.id.Data_real_time_show);
        Button Node_Real_Show2=(Button)findViewById(R.id.Data_real_time_show2) ;

        nodeData_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Resource_Management.this,NodeData_Show.class);
                startActivity(intent);
            }
        });

        Data_real_time_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Resource_Management.this, Node_Real_Show.class);
                startActivity(intent);

            }
        });
        nodeData_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Resource_Management.this,NodeData_Show.class);
                startActivity(intent);
            }
        });
        Node_Real_Show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Resource_Management.this,Node_Real_Show2.class);
                startActivity(intent);
            }
        });
    }
}
