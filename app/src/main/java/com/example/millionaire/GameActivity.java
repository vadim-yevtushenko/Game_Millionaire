package com.example.millionaire;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GameActivity extends AppCompatActivity implements Keys {
    private ListView lvLevels;
    private TextView tvQuestion;
    private TextView tvInfo;
    private TextView tvMoney;
    private TextView tvRed1;
    private TextView tvRed2;
    ProgressBar pbTimer;
    private Button b50;
    private Button bA;
    private Button bB;
    private Button bC;
    private Button bD;

    private Model model;
    private LevelsAdapter levelsAdapter;
    private ArrayList<Map<String, String>> levels;
    private ArrayList<Button> buttons;
    private TimerTask timerTask;


    private String rightAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        model = (Model) intent.getSerializableExtra(KEY_MODEL);

        initViews();
        createLevelListView();
        initListeners();
        timerTask = new TimerTask();
        tvMoney.setText("Your money:\n" + model.getMoneyBalance());
        if (model.isUsedPrompt50()) {
            b50.setClickable(false);
            tvRed1.setVisibility(View.VISIBLE);
            tvRed2.setVisibility(View.VISIBLE);
        }
        buttons = new ArrayList<>();
        buttons.add(bA);
        buttons.add(bB);
        buttons.add(bC);
        buttons.add(bD);
        start();

    }

    private void setMoneyBalance() {

        model.setMoneyBalance(getMoneyBalance());
        tvMoney.setText("Yor money:\n" + getMoneyBalance());
    }

    private String getMoneyBalance() {
        String[] strings = model.getLevels().get(model.getLevels().size() - 1).split(":");
        String balance = strings[1].trim();
        return balance;
    }

    private void initListeners() {
        b50.setOnClickListener(this::usePrompt50);
        bA.setOnClickListener((v) -> {
            selectAnswer(bA);
        });
        bB.setOnClickListener((v) -> {
            selectAnswer(bB);
        });
        bC.setOnClickListener((v) -> {
            selectAnswer(bC);
        });
        bD.setOnClickListener((v) -> {
            selectAnswer(bD);

        });
    }

    private void selectAnswer(Button b) {
        timerTask.cancel(true);
        b.setBackgroundColor(Color.parseColor("#FF9800"));
        tvInfo.setText("answer accepted!\n\nis the answer correct?");
        for (Button button : buttons) {
            button.setClickable(false);
        }
        b50.setClickable(false);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            detectRightAnswer(b);
        }, 3000);


    }

    private void detectRightAnswer(Button selectedButton) {
        if (selectedButton.getText().toString().endsWith(rightAnswer)) {
            selectedButton.setBackgroundColor(Color.parseColor("#FFEB3B"));
            setMoneyBalance();
            model.getLevels().remove(model.getLevels().size() - 1);
            if (model.getCounter() == model.getNumberLevels()) {
                tvInfo.setText("Congratulation!!!\n\n" + model.getName() + ", you WIN\n" + model.getMoneyBalance());
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    gameOver();
                }, 3000);
            } else {
                model.incrementCounter();
                tvInfo.setText("Right answer!\n\nGO to the next level");
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    startNewLevel();
                }, 3000);
            }

        } else {
            selectedButton.setBackgroundColor(Color.parseColor("#FF0000"));
            for (Button button : buttons) {
                if (button.getText().toString().endsWith(rightAnswer)) {
                    button.setBackgroundColor(Color.parseColor("#FFEB3B"));
                }
            }
            tvInfo.setText("Wrong answer\nYOU LOSE\n\nGame over!");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                gameOver();
            }, 3000);
        }
    }

    private void gameOver() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startNewLevel() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(KEY_MODEL, model);
        startActivity(intent);
        finish();
    }


    private void usePrompt50(View view) {

        int counter = 0;
        while (counter < 2) {
            int random = (int) (Math.random() * 4);
            if (!buttons.get(random).getText().toString().endsWith(rightAnswer) && !buttons.get(random).getText().equals("")) {
                buttons.get(random).setText("");
                buttons.get(random).setClickable(false);
                counter++;
            }

        }
        b50.setClickable(false);
        tvRed1.setVisibility(View.VISIBLE);
        tvRed2.setVisibility(View.VISIBLE);
        model.setUsedPrompt50(true);
    }

    private void start() {

        QuestionAndAnswers questionAndAnswers = model.getQuestionAndAnswersArrayList().get(model.getCounter() - 1);
        tvQuestion.setText(model.getCounter() + ". " + questionAndAnswers.getQuestion());
        ArrayList<String> answers = questionAndAnswers.getAnswers();
        rightAnswer = questionAndAnswers.getRightAnswer();
        Collections.shuffle(answers);
        bA.setText("A: " + answers.get(0));
        bB.setText("B: " + answers.get(1));
        bC.setText("C: " + answers.get(2));
        bD.setText("D: " + answers.get(3));

        tvInfo.setText(model.getName() + "\nselect right answer");
        timerTask.execute();

    }

    private void createLevelListView() {
        fillLevelsList();
        levelsAdapter = new LevelsAdapter(this,
                levels,
                R.layout.level_item,
                new String[]{KEY_LEVEL},
                new int[]{android.R.id.text1});
        lvLevels.setAdapter(levelsAdapter);
    }

    private void fillLevelsList() {

        levels = new ArrayList<>();
        Map<String, String> data;
        for (String level : model.getLevels()) {
            data = new HashMap<>();
            data.put(KEY_LEVEL, level);

            levels.add(data);
        }
    }

    private void initViews() {
        lvLevels = findViewById(R.id.lvLevels);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvInfo = findViewById(R.id.tvInfo);
        tvMoney = findViewById(R.id.tvMoney);
        tvRed1 = findViewById(R.id.tvRed1);
        tvRed2 = findViewById(R.id.tvRed2);
        pbTimer = findViewById(R.id.pbTimer);
        b50 = findViewById(R.id.b50);
        bA = findViewById(R.id.bA);
        bB = findViewById(R.id.bB);
        bC = findViewById(R.id.bC);
        bD = findViewById(R.id.bD);
    }

    class TimerTask extends AsyncTask<Void, Integer, Void>{
        private boolean isTimeOut = true;
        @Override
        protected Void doInBackground(Void... voids) {

            for (int i = 0; i < 101; i++) {
                if (isCancelled()){
                    isTimeOut = false;
                    break;
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            if (isTimeOut) {
                runOnUiThread(() -> {
                    tvInfo.setText("TIME is OVER\nYOU LOSE\n\nGame over!");
                    b50.setClickable(false);
                    for (Button button : buttons) {
                        button.setClickable(false);
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        gameOver();
                    }, 3000);
                });
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pbTimer.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(Void unused) {
            isTimeOut = false;
        }
    }
}