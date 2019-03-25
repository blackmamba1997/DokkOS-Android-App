package com.example.barui.dokkos;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class HackScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        startActivity(new Intent(HackScreen.this,Character.class));
        super.onBackPressed();
    }
}
