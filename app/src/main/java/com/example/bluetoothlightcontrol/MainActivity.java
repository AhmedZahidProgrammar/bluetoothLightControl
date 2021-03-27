package com.example.bluetoothlightcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket=null;
    private BluetoothDevice hc05;
    private View layout;
    private TextView toastTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        hc05=bluetoothAdapter.getRemoteDevice("98:D3:51:F5:CA:17");
        layout= getLayoutInflater().inflate(R.layout.custon_toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        toastTextView = layout.findViewById(R.id.toastTextView);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void sendDataToArduino(byte[] bcopy,String message){
    InputStream inputStream= null;
    byte receiveByte=0;
    byte[] b=bcopy;
    try {
        OutputStream outputStream = bluetoothSocket.getOutputStream();
        outputStream.write(b);
        inputStream = bluetoothSocket.getInputStream();
        inputStream.skip(inputStream.available());
        receiveByte=(byte)inputStream.read();
        if(receiveByte==45){
            toastTextView.setText("Button is already on");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT); // set the duration for the Toast
            toast.setView(layout);// set the inflated layout
            toast.show(); // display the custom Toast
        }else {
            toastTextView.setText(message);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT); // set the duration for the Toast
            toast.setView(layout);// set the inflated layout
            toast.show(); // display the custom Toast
        }
    } catch (IOException e) {
        Toast.makeText(getApplicationContext(),"Data not send successfully",Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

}
    public void onClicOnkButton1(View view) {
        byte[] b={1,1};
        sendDataToArduino(b,"Button1 is on");
    }
    public void onClicOnkButton2(View view) {
        byte[] b={2,1};
        sendDataToArduino(b,"Button2 is on");
    }
    public void onClicOnkButton3(View view) {
        byte[] b={3,1};
        sendDataToArduino(b,"Button3 is on");
    }
    public void onClicOnkButton4(View view) {
        byte[] b={4,1};
        sendDataToArduino(b,"Button4 is on");
    }
    public void onCLickOffButton1(View view) {
        byte[] b={1,0};
        sendDataToArduino(b,"Button1 is off");
    }
    public void onCLickOffButton2(View view) {
        byte[] b={2,0};
        sendDataToArduino(b,"Button2 is off");
    }
    public void onCLickOffButton3(View view) {
        byte[] b={3,0};
        sendDataToArduino(b,"Button3 is off");
    }
    public void onCLickOffButton4(View view) {
        byte[] b={4,0};
        sendDataToArduino(b,"Button4 is off");
    }

    public void onCLickConnetButton(View view) {
        try {
            bluetoothSocket = hc05.createInsecureRfcommSocketToServiceRecord(mUUID);
            bluetoothSocket.connect();
        } catch (IOException e) {

            e.printStackTrace();
        }
        if(bluetoothSocket.isConnected())
            toastTextView.setText("BlueTooth is connected");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT); // set the duration for the Toast
        toast.setView(layout);// set the inflated layout
        toast.show(); // display the custom Toast
    }
}