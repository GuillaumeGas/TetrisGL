package fr.univ_orleans.info.tetrisgl.controllers;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.univ_orleans.info.tetrisgl.models.Scores;
import fr.univ_orleans.info.tetrisgl.views.GameSurfaceView;

public class GameActivity extends Activity {

    // le conteneur View pour faire du rendu OpenGL
    private LinearLayout mLayout;
    private GLSurfaceView mGLView;
    private TextView mLabelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Pour le plein écran */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        mLabelView = new TextView(this);
        mLabelView.setText("Score : 0");
        mGLView = new GameSurfaceView(this, mLabelView);
        mLayout.addView(mLabelView);
        mLayout.addView(mGLView);
        setContentView(mLayout);
    }

    public void setScore(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String s = "Score : " + Integer.toString(score);
                mLabelView.setText(s);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Scores.getInstance().save(this);
    }

}
