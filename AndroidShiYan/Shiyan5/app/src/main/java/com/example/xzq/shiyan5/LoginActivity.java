package com.example.xzq.shiyan5;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameET;
    private EditText pwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.LOGIN);
        userNameET = (EditText) findViewById(R.id.ET_NAME);
        pwdET  = (EditText) findViewById(R.id.ET_PWD);

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.LOGIN:
                String userName = userNameET.getText().toString();
                String pwd = pwdET.getText().toString();
                loginPost(userName,pwd);
                break;
            default:
                break;
        }
    }

    private void loginPost(final String userName,final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String state = NetUtils.loginofPost(userName, password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (state.equals("yes")) {
                            Intent intent = new Intent(LoginActivity.this, DownloadActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}

