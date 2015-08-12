package com.example.shiza.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText ip_address;
    EditText port_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void connect(View view)
    {
        ip_address = (EditText)findViewById(R.id.ip_address);
        port_number = (EditText)findViewById(R.id.port_number);
        new ConnectToServer(ip_address.getText().toString(),port_number.getText().toString()).execute();

    }
}

class  ConnectToServer extends AsyncTask<Void,Void,Void>
{
    String ip_address;
    int port_number;

    public ConnectToServer(String ip_address,String port_number)
    {
        this.ip_address = "192.168.9.103";
        this.port_number = 8080;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Socket socket = new Socket(ip_address,port_number);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            output.writeUTF("Hello from string");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}