package com.example.vinicius.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;


    public void turnBluetoothOff(View view){

        bluetoothAdapter.disable();

        if (bluetoothAdapter.isEnabled()) {

            Toast.makeText(this, "Bluetooth could not be disabled", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "bluetooth is now been turned off", Toast.LENGTH_SHORT).show();
        }
    }

    public void findDiscoverableDevices(View view) {

        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        startActivity(i);

    }

    public void viewPairedDevices(View view) {

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        ListView pairedDevicesListView = (ListView) findViewById(R.id.pairedDevicesListView);

        ArrayList<String> pairedDevicesArrayList = new ArrayList<>();

        for (BluetoothDevice bluetoothDevice : pairedDevices){

            pairedDevicesArrayList.add(bluetoothDevice.getName());

        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pairedDevicesArrayList);

        pairedDevicesListView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (bluetoothAdapter.isEnabled()) {

            Toast.makeText(this, "bluetooth is on", Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivity(intent);

            if (bluetoothAdapter.isEnabled()){

                Toast.makeText(this, "bluetooth is now been turned on", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
