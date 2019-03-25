package com.example.barui.dokkos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class HackScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_screen);
        Intent intent = getIntent();
        /*String message = intent.getStringExtra(DokkOS.EXTRA_MESSAGE);
        Uri uri=Uri.parse(message);
        VideoView videoView=findViewById(R.id.videoView);



        videoView.setVideoURI(uri);
        videoView.start();*/
        Toast.makeText(getApplicationContext(),"내가 너를 해킹 했어 *_-", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 1 seconds
        long[] mVibratePattern = new long[]{0,1000, 500, 1000, 500, 2000, 1000};

        // -1 : Do not repeat this pattern
        // pass 0 if you want to repeat this pattern from 0th index
        v.vibrate(mVibratePattern, 0);

    }
    @Override
    public void onBackPressed() {
        // Write your code here
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.cancel();
        super.onBackPressed();
    }
}
