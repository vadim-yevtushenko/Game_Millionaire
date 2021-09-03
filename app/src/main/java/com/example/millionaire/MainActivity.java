package com.example.millionaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements Keys {
    private Button bStart;
    private EditText etName;
    private Model model;

    private String jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = new Model();

        initViews();
        initListeners();
    }

    private void initListeners() {
        bStart.setOnClickListener(this::startGame);

    }

    private void startGame(View view) {
        prepareListWithQuestion();
        model.setName(etName.getText().toString());
        if (etName.getText().toString().equals("")){
            model.setName("Player");
        }

        if (jsonObject != null) {
            Gson gson = new Gson();
            ArrayList<QuestionAndAnswers> list = gson.fromJson(jsonObject, new TypeToken<ArrayList<QuestionAndAnswers>>(){}.getType());
            model.setQuestionAndAnswersArrayList(list);
        }
        model.shuffle();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(KEY_MODEL, model);
        startActivity(intent);
        finish();
    }

    private void prepareListWithQuestion() {
        model.shuffle();
    }

    private void initViews() {
        bStart = findViewById(R.id.bStart);
        etName = findViewById(R.id.etName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemConnect){
            Intent intent = new Intent(this, ConnectActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK) {
                    jsonObject = data.getStringExtra(KEY_JSON_OBJECT);

                    break;
                }
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }
}