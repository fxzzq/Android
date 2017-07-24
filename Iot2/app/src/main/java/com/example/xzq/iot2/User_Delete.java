package com.example.xzq.iot2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Delete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user__delete_listview);
        sendRequestWithOkHttp2();
    }

    private  void sendRequestWithOkHttp2(){
        new     Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    System.out.println("11");
                    Request request=new Request.Builder()
                            .url("http://118.89.38.92:8080/HelloWeb/xzq2")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    System.out.println("22");
                    Log.i("数据2",responseData);
                    System.out.println("33");
                    System.out.println(responseData);
                    parseJSONWithJSONObject2(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void  parseJSONWithJSONObject2(String jsonData){
        try{
            System.out.print(jsonData);
            JSONArray jsonArray=new JSONArray(jsonData);
            final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
            for (int i=0;i<jsonArray.length();i++){
                Map<String,Object> item=new HashMap<String, Object>();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("username");
                String pwd=jsonObject.getString("password");
                item.put("id",id);
                item.put("username",name);
                item.put("password",pwd);
                list.add(item);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAdapter adapter=new SimpleAdapter(User_Delete.this,list,R.layout.user_delete,
                            new String[]{"id","username","password"},
                            new int[]{R.id.users_kb1,R.id.users_kb2,R.id.users_kb3});
                    ListView lv=(ListView)findViewById(R.id.ls2);
                    lv.setAdapter(adapter);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


