package com.example.xzq.iot2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//import com.example.smarthome.R;
import com.example.xzq.iot2.Node_Real_Show1;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.web.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

    public class Node_Real_Show2 extends AppCompatActivity {

        private Node_Real_Show1 GasShow;
        private String GASDATA="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ae);
            GasShow = (Node_Real_Show1) findViewById(R.id.mv_showgas);
            init(GasShow);
            Log.i("LXY","123");
            sendRequestWithOkHttp1();
            Log.i("LXY","1222222");


            Log.i("LXY","144444444444");
        }

        private void init(Node_Real_Show1 myCircleView) {
            myCircleView.setMinDegree(0);
            myCircleView.setMaxDegree(0);
            myCircleView.setCurrentDegree(0);
        }

        private void showDegree(final int currentGas, final Node_Real_Show1 myCircleView) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < currentGas * 3; i++) {
                        //每三小格代表一度，degree*3才能对应到温度计上相应的degree的显示
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        myCircleView.setMaxDegree(i);
                        myCircleView.postInvalidate();
                    }
                    for (int j = 0; j <= currentGas * 3; j++) {
                        if (j <= (currentGas - 0) * 3 + 0) {//23*3+10=79
                            myCircleView.setCurrentDegree(j);
                        }
                        myCircleView.postInvalidate();
                    }
                }
            }).start();

        }
        private  void sendRequestWithOkHttp1(){
            new     Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client=new OkHttpClient();
                        System.out.println("11");
                        Request request=new Request.Builder()
                                .url("http://118.89.38.92:8080/HelloWeb/gasLet")
                                .build();
                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        System.out.println("22");
                        Log.i("数据",responseData);
                        System.out.println("33");
                        System.out.println(responseData);
                        parseJSONWithJSONObject(responseData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        private void  parseJSONWithJSONObject(String jsonData){
            try{
                System.out.print(jsonData);
                JSONArray jsonArray=new JSONArray(jsonData);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                   String gas=jsonObject.getString("collectgas");
                    GASDATA=gas;
                    Integer x=Integer.parseInt(GASDATA);
                    Log.i("x",x.toString());
                    Log.i("LXY","1333333333");
                    showDegree(x, GasShow);
                    Log.i("数据",GASDATA);

                    Thread.sleep(1000);//每1秒钟更新一次数据

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

