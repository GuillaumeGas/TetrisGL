package fr.univ_orleans.info.tetrisgl;

/**
 * Created by Guillaume on 19/04/2016.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

/*
    Class MyGLSurfaceView : gère les évènements, créé le renderer
*/
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private Game mGame;
    private Context context;

    public MyGLSurfaceView(Context context) {
        super(context);

        this.context = context;
        // Création d'un context OpenGLES 2.0
        setEGLContextClientVersion(2);

        mGame = new Game(this);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MyGLRenderer(mGame, this);
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /* Comment interpréter les événements sur l'écran tactile */

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getActionMasked() == MotionEvent.ACTION_UP) {
            // Les coordonnées du point touché sur l'écran
            float x = e.getX();
            float y = e.getY();

            // la taille de l'écran en pixels
            float width = getWidth();
            float height = getHeight();

            if(y > (2*height/3)) {
                mGame.moveDown();
            } else if(y < (height/3)) {
                mGame.rotateCurrentPiece();
            } else {
                if(x < width/2) {
                    mGame.moveLeft();
                } else {
                    mGame.moveRight();
                }
            }
            requestRender();
        }

        return true;
    }

    public void showScore(int score) {
        int duration = Toast.LENGTH_SHORT;
        String text_score = "Votre score est de " + Integer.toString(score) + " ! ";

        //Toast toast = Toast.makeText(context, text_score, duration);
        //toast.show();
        System.out.println(text_score);
    }
}
