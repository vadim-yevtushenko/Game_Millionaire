package com.example.millionaire;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GameFragment extends Fragment implements Keys{

    private ListView lvLevels;
    private TextView tvQuestion;
    private TextView tvInfo;
    private TextView tvMoney;
    private TextView tvRed1;
    private TextView tvRed2;
    private ProgressBar pbTimer;
    private Button b50;
    private Button bA;
    private Button bB;
    private Button bC;
    private Button bD;

    private Model model;
    private LevelsAdapter levelsAdapter;
    private ArrayList<Map<String, String>> levels;
    private ArrayList<Button> buttons;
    private GameFragment.TimerTask timerTask;

    private GameActivity activity;
    private String rightAnswer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        activity = (GameActivity) getActivity();
        model = activity.getModel();
        initViews(view);
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
            selectedButton.setBackgroundColor(Color.parseColor("#FF8BC34A"));
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
                    button.setBackgroundColor(Color.parseColor("#FF8BC34A"));
                }
            }
            tvInfo.setText("Wrong answer\nYOU LOSE\n\nGame over!");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                gameOver();
            }, 3000);
        }
    }

    private void startNewLevel() {
        activity.startNewLevel();
    }

    private void gameOver() {
        activity.gameOver();
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
        levelsAdapter = new LevelsAdapter(activity,
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

    private void initViews(View view) {
        lvLevels = view.findViewById(R.id.lvLevels);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        tvInfo = view.findViewById(R.id.tvInfo);
        tvMoney = view.findViewById(R.id.tvMoney);
        tvRed1 = view.findViewById(R.id.tvRed1);
        tvRed2 = view.findViewById(R.id.tvRed2);
        pbTimer = view.findViewById(R.id.pbTimer);
        b50 = view.findViewById(R.id.b50);
        bA = view.findViewById(R.id.bA);
        bB = view.findViewById(R.id.bB);
        bC = view.findViewById(R.id.bC);
        bD = view.findViewById(R.id.bD);
    }

    class TimerTask extends AsyncTask<Void, Integer, Void> {
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
                activity.runOnUiThread(() -> {
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