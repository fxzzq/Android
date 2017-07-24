package com.web;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
    public static String REG_URL = "http://118.89.38.92:8080/HelloWeb/RegLet";
    public static String LOGIN_URL = "http://118.89.38.92:8080/HelloWeb/LogLet";
    public static String Gas_URL="http://118.89.38.92:8080/HelloWeb/GasLet";


    //登录处理
    public static String executeHttpGet(String userName,String pwd){
        OutputStream out = null;
        String state = "";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(LOGIN_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            String data="username="+userName+"&password="+pwd;


            out = conn.getOutputStream();
            Log.i("11111", data);

            out.write(data.getBytes());
            out.flush();

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                Log.i("777","111111");
                InputStream is = conn.getInputStream();
                state = getStateFromInputStream(is);
                return state;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (out != null){
                try {
                    out.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return state;
    }
    //注册功能
    public static String executeHttpPostReg(String userName,String pwd){
        OutputStream out = null;
        String state = "";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(REG_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            String data="username="+userName+"&password="+pwd;


            out = conn.getOutputStream();
            Log.i("11111", data);

            out.write(data.getBytes());
            Log.i("11111","77777");
            out.flush();

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                Log.i("11111","88888");
                InputStream is = conn.getInputStream();
                state = getStateFromInputStream(is);
                return state;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (out != null){
                try {
                    out.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return state;
    }
//气体数据接收
    public static String executeHttpGasPost(){
        OutputStream out = null;
        String state = "";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(Gas_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

          //String data="username="+userName+"&password="+pwd;

            out = conn.getOutputStream();
           // Log.i("11111", data);

          //  out.write(data.getBytes());
          //  out.flush();

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                Log.i("777","111111");
                InputStream is = conn.getInputStream();
                state = getStateFromInputStream(is);
                return state;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (out != null){
                try {
                    out.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return state;
    }



    private static String getStateFromInputStream(InputStream is) throws IOException{
        //InputStreamReader也行
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer)) != -1){
            byteArrayOutputStream.write(buffer,0,len);
        }
        is.close();
        byteArrayOutputStream.close();
        String state = byteArrayOutputStream.toString();
        return state;
    }
}
