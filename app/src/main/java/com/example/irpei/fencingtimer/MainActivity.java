package com.example.irpei.fencingtimer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Authenticator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    public BluetoothAdapter b;
    private LinearLayout devices;
    private BluetoothDevice address;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String addr = device.getAddress(); // MAC address
                Button t = new Button(getApplicationContext());
                t.setText(name);
                t.setTag(device);
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        address = (BluetoothDevice)view.getTag();
                    }
                });
                devices.addView(t);
            }
        }
    };
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        devices = findViewById(R.id.devices);
    }

    // Connects to bluetooth
    public void bluetoothOnClick(View v)
    {
        b = BluetoothAdapter.getDefaultAdapter();
        if(b == null)
        {
            Context c = getApplicationContext();
            CharSequence msg = "Bluetooth is not supported on this device.";
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!b.isEnabled())
            {
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i, 0);
            }
            else
            {
                Set<BluetoothDevice> paired = b.getBondedDevices();
                if(paired.size() > 0)
                {
                    for(BluetoothDevice dev : paired)
                    {
                        String name = dev.getName();
                        String addr = dev.getAddress();
                        Button t = new Button(getApplicationContext());
                        t.setText(name);
                        t.setTag(dev);
                        t.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                address = (BluetoothDevice) view.getTag();
                            }
                        });
                        devices.addView(t);
                    }
                }
                if(b.startDiscovery())
                {

                }
            }
        }
    }
    // Starts running the app function
    public void startOnClick(View v)
    {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Set<BluetoothDevice> paired = b.getBondedDevices();
                if(paired.size() > 0)
                {
                    for(BluetoothDevice dev : paired)
                    {
                        String name = dev.getName();
                        String addr = dev.getAddress();
                        Button t = new Button(getApplicationContext());
                        t.setText(name);
                        t.setTag(dev);
                        t.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                address = (BluetoothDevice) view.getTag();
                            }
                        });
                        devices.addView(t);
                    }
                }
            }
        }
    }
}
