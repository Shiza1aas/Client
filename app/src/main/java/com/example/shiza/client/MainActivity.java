package com.example.shiza.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CLIENT_MESSAGE";
    EditText ip_address;
    EditText port_number;
    EditText message_client;
    Button button_send;
    Button button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void connect(View view) {
//        ip_address = (EditText) findViewById(R.id.ip_address);
//        ip_address.setText("192.168.9.100");
//        port_number = (EditText) findViewById(R.id.port_number);
//        port_number.setText("8080");
        message_client = (EditText) findViewById(R.id.message_client);
        button_send = (Button)findViewById(R.id.button_send);
        button_cancel = (Button)findViewById(R.id.button_cancel);
        Log.d(TAG, "connecting to the server.");
//        new ConnectToServer(ip_address.getText().toString(), port_number.getText().toString(), message_client,button_send,button_cancel).execute();
        new ConnectToServer("192.168.9.100","8080", message_client,button_send,button_cancel).execute();

    }
}

class ConnectToServer extends AsyncTask<Void, DataOutputStream, Void> {
    private static final String TAG = "CLIENT_MESSAGE";
    String ip_address;
    int port_number;
    EditText message_client;
    Button button_send;
    Button button_cancel;
    boolean send = false;
    boolean cancel = false;

    public ConnectToServer(String ip_address, String port_number, EditText message_client,Button button_send,Button button_cancel) {
        this.ip_address = ip_address;
        this.port_number = Integer.parseInt(port_number);
        this.message_client = message_client;
        this.button_cancel = button_cancel;
        this.button_send = button_send;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Socket socket = new Socket(ip_address, port_number);

            if (LoggerConfig.TAG) {
                Log.d(TAG, "the socket is created at " + ip_address);
            }

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            while (!cancel )
                publishProgress(output);
//            output.writeUTF("Hello from string");
            if (LoggerConfig.TAG) {
                Log.d(TAG, "I have written and closed the loop.");
            }
            socket.close();
        } catch (IOException e) {
            if (LoggerConfig.TAG) {
                Log.d(TAG, "Could not connect.");
            }
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(DataOutputStream... values) {
        super.onProgressUpdate(values);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send = true;
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel = true;
            }
        });

        Log.d(TAG, "I am in onProgressUpdate");

            if ( send )
            {
                try {
                    values[0].writeUTF(message_client.getText().toString());
                    Log.d(TAG, "I am in onProgressUpdate try.");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "I am in onProgressUpdate catch.");

                }

                send = false;
            }

    }
}