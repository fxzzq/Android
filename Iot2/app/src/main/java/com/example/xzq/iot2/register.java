package com.example.xzq.iot2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.web.WebService;

public class register extends AppCompatActivity {
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final EditText reguser=(EditText)findViewById(R.id.rg_user);
        final EditText regpwd=(EditText)findViewById(R.id.reg_psd);
        Button regfuction=(Button)findViewById(R.id.bt2_register);
        regfuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName =reguser.getText().toString();
                String pwd = regpwd.getText().toString();
                RegPost(userName,pwd);
            }
        });
    }
    private void RegPost(final String userName,final String password){
        new     Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("777","111111");
                final String state = WebService.executeHttpPostReg(userName,password);
                Log.i("777","222222");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("777","33333");
                        if (state.equals("yes")) {
                            Log.i("777","a2aaaa22");
                            Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                            intent = new Intent(register.this, login.class);
                            startActivity(intent);
                            Log.i("777","yyyyy");
                        } else {
                            Log.i("777","ssss2");
                            Toast.makeText(register.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}
