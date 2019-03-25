package com.example.barui.dokkos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class DokkOS extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    public BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    ArrayList<String> name;
    ArrayList<String> address;
    private ListView lv;
    private ArrayList<InfoRowdata> infodata;
    String k = "";
    private String TAG = "DEBUG";
    HashMap<String,Boolean> key;
    UUID myUUID = UUID.fromString("9ba03e75-ea2c-48e1-9bfc-b8d49a428bd9");
    int i = 0, check_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokk_os);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final CheckBox checked = findViewById(R.id.checkBox3);
        key=new HashMap<>();

        final LinearLayout list = findViewById(R.id.list1);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String selfname=bluetoothAdapter.getName();
        TextView selfnametext=findViewById(R.id.textView5);
        selfnametext.setText(selfname);
        String selfadd=bluetoothAdapter.getAddress();
        TextView selfaddtext=findViewById(R.id.textView3);
        selfaddtext.setText(selfadd+"     ");
        pairedDevices = bluetoothAdapter.getBondedDevices();

        name = new ArrayList<>();

        address = new ArrayList<>();
        for (BluetoothDevice bt : pairedDevices) {
            name.add(bt.getName());
            address.add(bt.getAddress());
        }
        lv = findViewById(R.id.list);
        infodata = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {

            infodata.add(new InfoRowdata(false, i));

        }
        lv.invalidate();
        lv.setAdapter(new MyAdapter());

        /*CustomAdapter customAdapter=new CustomAdapter();
        lv.setAdapter(customAdapter);*/

        checked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView v = findViewById(R.id.textView2);

                if (checked.isChecked()) {
                    v.setText("준비된     ");
                    v.setTextColor(Color.parseColor("#FFC400"));

                } else {
                    v.setText("기다리는  ");
                    v.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        final CheckBox hide = findViewById(R.id.checkBox);

        hide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView v = findViewById(R.id.textView2);

                if (hide.isChecked()) {
                    list.setVisibility(View.GONE);
                    checked.setChecked(false);
                    v.setText("기다리는  ");
                    v.setTextColor(Color.parseColor("#FFFFFF"));


                } else {
                    list.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    public void hack(View view) {

        CheckBox checked = findViewById(R.id.checkBox3);

        if (checked.isChecked()) {
            //v.setText("준비된");

            startActivity(new Intent(this, HackScreen.class));

        }
        if (check_check > 0) {
            String k = "null";
            Toast.makeText(getApplicationContext(), "장치를 선택하십시오", Toast.LENGTH_SHORT).show();
            new BTreceiver().execute(1);
            this.recreate();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DokkOS.this,Character.class));
    }

    public class BTreceiver extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            i=0;

        }

        @Override
        protected String doInBackground(Integer... integers) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {

                if (key.containsKey(device.getAddress())) {
                    mmDevice = device;

                    BluetoothSocket tmp = null;
                    try {
                        // Get a BluetoothSocket to connect with the given BluetoothDevice.
                        // MY_UUID is the app's UUID string, also used in the server code.
                        tmp = mmDevice.createRfcommSocketToServiceRecord(myUUID);
                    } catch (IOException e) {
                        Log.e(TAG, "Socket's create() method failed", e);
                    }
                    mmSocket = tmp;
                    bluetoothAdapter.cancelDiscovery();

                    try {
                        // Connect to the remote device through the socket. This call blocks
                        // until it succeeds or throws an exception.
                        mmSocket.connect();
                        k = "success";
                    } catch (IOException connectException) {
                        // Unable to connect; close the socket and return.
                        try {
                            mmSocket.close();
                        } catch (IOException closeException) {
                            Log.e(TAG, "Could not close the client socket", closeException);
                        }
                    }
                }
            }
            return k;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("success")) {
                Toast.makeText(getApplicationContext(), "hack complete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            /*convertView=getLayoutInflater().inflate(R.layout.customlayout,null);

            TextView v2=convertView.findViewById(R.id.textViewc2);

            TextView v4=convertView.findViewById(R.id.textViewc4);
            v4.setText(name.get(position).toString());
            v2.setText(address.get(position).toString()+"     ");
            return convertView;*/

            final View row = View.inflate(getApplicationContext(), R.layout.customlayout, null);
            TextView t2 = row.findViewById(R.id.textViewc2);
            TextView v=row.findViewById(R.id.textViewc1);
            t2.setText(address.get(position) + "     ");
            TextView t4 = row.findViewById(R.id.textViewc4);
            t4.setText(name.get(position));


            final CheckBox cb =  row
                    .findViewById(R.id.check_c);
            cb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView v1=row.findViewById(R.id.textViewc1);
                    if (infodata.get(position).isclicked) {
                        infodata.get(position).isclicked = false;
                        check_check--;
                        key.remove(address.get(position));
                        v1.setText("기다리는  ");
                        v1.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        infodata.get(position).isclicked = true;
                        check_check++;
                        key.put(address.get(position),true);
                        v1.setText("준비된     ");
                        v1.setTextColor(Color.parseColor("#FFC400"));

                    }
                }
            });

            if (infodata.get(position).isclicked) {

                cb.setChecked(true);
                v.setText("준비된     ");
                v.setTextColor(Color.parseColor("#FFC400"));

            } else {
                cb.setChecked(false);
                v.setText("기다리는  ");
                v.setTextColor(Color.parseColor("#FFFFFF"));

            }
            return row;
        }
    }
}
