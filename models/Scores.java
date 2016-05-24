package fr.univ_orleans.info.tetrisgl.models;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by Guillaume on 24/05/2016.
 */
public class Scores {
    private static Scores Instance = getInstance();

    private Vector<Integer> mScores;

    private FileInputStream mIn = null;
    private FileOutputStream mOut = null;

    public static Scores getInstance()  {
        if (Instance == null)
            try {
                Instance = new Scores();
            } catch(IOException e) {
                e.printStackTrace();
            }
        return Instance;
    }

    private Scores() throws IOException {
        mScores = new Vector<>();
    }

    public void init(Context context) {
        try {
            mIn = new FileInputStream(context.getFilesDir() + "/scores_tetris.txt");

            int score;
            while ((score = mIn.read()) != -1) {
                mScores.add(score);
            }

            if (mIn != null)
                mIn.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<Integer> getScores() {
        return mScores;
    }

    public void addScore(int s) {
        mScores.add(s);
    }

    public void save(Context context) {
        try {
            mOut = context.openFileOutput("scores_tetris.txt", Context.MODE_PRIVATE);
            for (Integer i : mScores) {
                mOut.write(i);
            }

            if (mOut != null)
                mOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
