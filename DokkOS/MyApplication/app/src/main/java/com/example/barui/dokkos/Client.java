package com.example.barui.dokkos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Client extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothServerSocket mmServerSocket;

    private UUID myUUID;
    private String myName;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myUUID = UUID.fromString("9ba03e75-ea2c-48e1-9bfc-b8d49a428bd9");
        myName = myUUID.toString();
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(myName, myUUID);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        mmServerSocket = tmp;

        new btreceiver().execute(myName);

       /* b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BluetoothServerSocket tmp = null;
                try {
                    // MY_UUID is the app's UUID string, also used by the client code.
                    tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(myName, myUUID);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                mmServerSocket = tmp;
                new btreceiver().execute(myName);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Client.this,Character.class));
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class btreceiver extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {
            BluetoothSocket socket;
            // Keep listening until exception occurs or a socket is returned.
            if (mmServerSocket != null) {
                while (true) {

                    try {
                        socket = mmServerSocket.accept();
                    } catch (IOException e) {
                        Log.e(TAG, "Socket's accept() method failed", e);
                        break;
                    }

                    if (socket != null) {
                        // A connection was accepted. Perform work associated with
                        // the connection in a separate thread.
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "success";

                    } else {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return "failure";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("success")) {


                Intent intent = new Intent(Client.this, HackScreen.class);
                //String message ="android:resource://"+ getPackageName() + "/"+ (R.raw.kkuqxy);
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                finish();
            }
        }
    }
}

