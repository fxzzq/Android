package com.example.xzq.iot2;

import com.web.WebService;


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.os.Bundle;
        import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class login extends Activity implements OnClickListener {

    private  Intent intent;
    // 登陆按钮
    private Button logbtn;
    private Button regbtn;
    // 调试文本，注册文本
    private TextView infotv, regtv;
    // 显示用户名和密码
    EditText username, password;
    // 创建等待框
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // 获取控件
        username = (EditText) findViewById(R.id.tv_user);
        password = (EditText) findViewById(R.id.tv_psd);
        logbtn = (Button) findViewById(R.id.bt_login);
       // regtv = (TextView) findViewById(R.id.tv2);
        //infotv = (TextView) findViewById(R.id.tv1);
         regbtn=(Button)findViewById(R.id.bt_register);
        // 设置按钮监听器
       logbtn.setOnClickListener(this);
       // regtv.setOnClickListener(this);
        regbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(login.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }

                String userName =  username.getText().toString();
                String pwd = password.getText().toString();
                loginGet(userName,pwd);
                // 提示框
             /*   dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();*/
                break;

               /* // 创建子线程，分别进行Get和Post传输
                new Thread(new MyThread()).start();*/

            case R.id.bt_register:
                intent = new Intent(login.this, register.class);
                // overridePendingTransition(anim_enter);
                startActivity(intent);
                break;
        }

    }

    // 子线程接收数据，主线程修改数据
    private void loginGet(final String userName,final String password){
        new     Thread(new Runnable() {
            @Override
            public void run() {
                    final String state = WebService.executeHttpGet(userName,password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (state.equals("yes")) {
                                Log.i("999","a2aaaa22");
                                Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                //uesername传值到另一个activity

                                 intent = new Intent(login.this, Overall_Function.class);
                                intent.putExtra("username",userName);
                                startActivity(intent);

                                Log.i("999","yyyyy");
                            } else {
                                Log.i("999","ssss2");
                                Toast.makeText(login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        }).start();
    }


    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

}

