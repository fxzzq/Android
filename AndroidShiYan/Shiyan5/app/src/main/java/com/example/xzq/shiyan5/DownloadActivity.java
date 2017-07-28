package com.example.xzq.shiyan5;

        import android.os.AsyncTask;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import java.io.BufferedInputStream;
        import java.io.BufferedOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class DownloadActivity extends AppCompatActivity {

    private static final int MAX = 1;
    private static final int UPDATE = 2;
    ProgressBar progressBar;
    TextView textViewProgress;
    int contentLength;
    Button startDown;
    EditText ET_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar = (ProgressBar)findViewById(R.id.PROGRESS_BAR);
        textViewProgress = (TextView)findViewById(R.id.TV_PROGRESS);
        startDown = (Button)findViewById(R.id.BT_DOWN);
        ET_url = (EditText)findViewById(R.id.ET_IP);

        startDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String url = ET_url.getText().toString();
                //new DownloadTask().execute(url);
                new DownloadTask().execute("http://10.0.2.2:8080/Socket/Wireshark.exe");
            }
        });
    }

    class DownloadTask extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                contentLength = conn.getContentLength();
                Log.d("wjztest", "max: "+contentLength);
                publishProgress(UPDATE,contentLength);

                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

                String filename = params[0].substring(params[0].lastIndexOf("/"));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(
                        Environment.getExternalStorageDirectory()+filename
                )));

                int len = -1;
                byte[] bytes = new byte[1024];
                while((len = bis.read(bytes)) != -1){
                    bos.write(bytes,0,len);
                    /*try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                    bos.flush();
                    publishProgress(UPDATE,len);
                }
                bis.close();
                bos.close();

            }catch(Exception e){
                e.printStackTrace();
            }

            return "successful";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            switch(values[0]){
                case MAX:
                    progressBar.setMax(values[1]);
                    Log.d("xzq11","12333333333");
                    Log.d("wjztest", "max: "+values[1]);
                    break;
                case UPDATE:
                    progressBar.incrementProgressBy(values[1]);

                    double pro = progressBar.getProgress();
                    int i = progressBar.getProgress()*100/contentLength;
                   // int i = (int)((pro/contentLength)*100);

                    Log.d("wjztest", "percent: "+i);
                    Log.d("xzq12","1234");
                    Log.d("wjztest", "getProgress: "+progressBar.getProgress());

                    textViewProgress.setText("已经下载："+i+"%");
                    Log.d("xzq123","12345");
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textViewProgress.setText(s);
        }
    }
}
