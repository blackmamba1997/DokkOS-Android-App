package com.example.barui.dokkos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView dokkimage = findViewById(R.id.dokk_image);
        text=findViewById(R.id.loading);
        dokkimage.setImageResource(R.drawable.dokkss);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String[] arr={"초기화하는","안녕 안녕, 거기 요...","설정하고 계속해서","우르와","초기화하","해서","승자...","고 계속해서...."," ","프로필에 동기화"};
        new Loading().execute(arr);

    }
    class Loading extends AsyncTask<String,String,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            for (int i=0;i<strings.length;i++){
                publishProgress(strings[i]);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            text.setText(values[0]);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreen.this,DokkOS.class));
                    finish();

                }
            },1500);

        }
    }

}
