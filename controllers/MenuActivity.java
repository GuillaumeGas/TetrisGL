package fr.univ_orleans.info.tetrisgl.controllers;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import fr.univ_orleans.info.tetrisgl.R;
import fr.univ_orleans.info.tetrisgl.models.Scores;

public class MenuActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Pour le plein écran */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_layout);

        Scores.getInstance().init(this);

        Button bStart = (Button) findViewById(R.id.button);
        Button bScore = (Button) findViewById(R.id.button2);
        bStart.setOnClickListener(this);
        bScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, GameActivity.class);
                this.startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent(this, ScoreActivity.class);
                this.startActivity(intent);
                break;
        }

    }

}
