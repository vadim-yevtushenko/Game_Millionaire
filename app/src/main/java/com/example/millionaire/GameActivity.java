package com.example.millionaire;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements Keys {

    private Model model;
    private GameFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        model = (Model) intent.getSerializableExtra(KEY_MODEL);

        startNewLevel();

    }

    public Model getModel() {
        return model;
    }

    public void gameOver() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void startNewLevel() {
        getFragmentManager().popBackStack();

        gameFragment = new GameFragment();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout, gameFragment).
                commit();

    }
}