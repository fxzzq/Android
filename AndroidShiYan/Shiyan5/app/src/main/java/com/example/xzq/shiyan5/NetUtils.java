package com.example.xzq.shiyan5;

/**
 * Created by xzq on 2017/4/12.
 */
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {
    public static String LOGIN_URL = "http://10.0.2.2:8080/Socket/login";
    public static String loginofPost(String userName,String pwd){
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

            String data="userName="+userName+"&pwd="+pwd;


            out = conn.getOutputStream();
            Log.d("wjzP5", data);

            out.write(data.getBytes());
            out.flush();

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
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
