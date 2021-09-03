package com.example.millionaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConnectActivity extends AppCompatActivity implements Keys{
    private EditText etIp;
    private EditText etPort;
    private Button bConnect;
    private Button bBack;
    private ProgressBar pbConnect;
    private TextView tvInfo2;
    private Socket socket;
    private String jsonObject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        initViews();

        bConnect.setOnClickListener(this::connect);
        bBack.setOnClickListener(this::back);

    }

    private void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_JSON_OBJECT, jsonObject);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void connect(View view) {
        socket = new Socket();
        ConnectionToServer connection = new ConnectionToServer();
        connection.execute();
        pbConnect.setVisibility(View.VISIBLE);

        pbConnect.setVisibility(View.INVISIBLE);

    }

    private void initViews() {
        etIp = findViewById(R.id.etIp);
        etPort = findViewById(R.id.etPort);
        bConnect = findViewById(R.id.bConnect);
        bBack = findViewById(R.id.bBack);
        pbConnect = findViewById(R.id.pbConnect);
        tvInfo2 = findViewById(R.id.tvInfo2);

    }

    class ConnectionToServer extends AsyncTask<Void, Void, Void> {

        private Scanner scanner;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                runOnUiThread(()-> {
                    tvInfo2.setText("");
                    pbConnect.setVisibility(View.VISIBLE);
                });

                String ip = etIp.getText().toString();
                int port = Integer.parseInt(etPort.getText().toString());
                socket.connect(new InetSocketAddress(ip, port), 10000);
                scanner = new Scanner(socket.getInputStream());

                jsonObject = scanner.nextLine();

                runOnUiThread(()-> {
                    pbConnect.setVisibility(View.INVISIBLE);
                    tvInfo2.setText("connected successfully");
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(()-> {
                    pbConnect.setVisibility(View.INVISIBLE);
                    tvInfo2.setText("error connection");
                });
            }
            return null;
        }
    }


}