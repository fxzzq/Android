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

public class NodeData_Show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nodeshow_listview);
        sendRequestWithOkHttp();

    }

    private  void sendRequestWithOkHttp(){
    new     Thread(new Runnable() {
        @Override
    public void run() {
        try{
              OkHttpClient client=new OkHttpClient();
            System.out.println("11");
            Request request=new Request.Builder()
                    .url("http://118.89.38.92:8080/HelloWeb/xzq")
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
            final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
            for (int i=0;i<jsonArray.length();i++){
                Map<String,Object> item=new HashMap<String, Object>();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("collectid");
                String name=jsonObject.getString("cllectname");
                String temp=jsonObject.getString("collecttemp");
                String humi=jsonObject.getString("collecthumi");
                String flame=jsonObject.getString("collectflame");
                String gas=jsonObject.getString("collectgas");
                String time=jsonObject.getString("time");
                item.put("collectid",id);
                item.put("cllectname",name);
                item.put("collecttemp",temp);
                item.put("collecthumi",humi);
                item.put("collectflame",flame);
                item.put("collectgas",gas);
                item.put("time",time);
                list.add(item);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAdapter adapter=new SimpleAdapter(NodeData_Show.this,list,R.layout.nodeshow,
                            new String[]{"collectid","cllectname","collecttemp","collecthumi","collectflame","collectgas","time"},
                            new int[]{R.id.kb1,R.id.kb2,R.id.kb3,R.id.kb4,R.id.kb5,R.id.kb6,R.id.kb7});
                    ListView lv=(ListView)findViewById(R.id.ls1);
                    lv.setAdapter(adapter);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

