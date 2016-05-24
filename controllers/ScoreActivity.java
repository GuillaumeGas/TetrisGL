package fr.univ_orleans.info.tetrisgl.controllers;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Vector;

import fr.univ_orleans.info.tetrisgl.R;
import fr.univ_orleans.info.tetrisgl.models.Scores;

public class ScoreActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Pour le plein écran */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.score_layout);

        Vector<Integer> scores_vec = Scores.getInstance().getScores();
        if (scores_vec.size() > 0) {
            ListView listView = (ListView) findViewById(R.id.listView);

            ArrayAdapter<Integer> adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, scores_vec.subList(0, scores_vec.size()));
            listView.setAdapter(adapter);
        }

    }
}
