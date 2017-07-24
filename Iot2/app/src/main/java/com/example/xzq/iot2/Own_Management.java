package com.example.xzq.iot2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Own_Management extends AppCompatActivity {
        private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_management);
        Button zhuce=(Button)findViewById(R.id.zhuce);
        final Button user_delete=(Button)findViewById(R.id.user_delete);

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Own_Management.this,register.class);
                startActivity(intent);
            }
        });
        user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(Own_Management.this,User_Delete.class);
                startActivity(intent);
            }
        });
    }
}
