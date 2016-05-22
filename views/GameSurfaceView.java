package fr.univ_orleans.info.tetrisgl.views;

/**
 * Created by Guillaume on 19/04/2016.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import fr.univ_orleans.info.tetrisgl.models.Game;

/**
    Class MyGLSurfaceView : gère les évènements, créé le renderer
*/
public class GameSurfaceView extends GLSurfaceView {

    private final GameRenderer mRenderer;
    private Game mGame;
    private Context context;
    private TextView mLabelView;

    private float lastX = 0.0f;
    private float lastY = 0.0f;
    private boolean moved = false;

    public GameSurfaceView(Context context, TextView labelView) {
        super(context);

        this.context = context;
        // Création d'un context OpenGLES 2.0
        setEGLContextClientVersion(2);

        mLabelView = labelView;
        mGame = new Game(this);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new GameRenderer(mGame, this);
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /* Comment interpréter les événements sur l'écran tactile */
    /* Toucher l'ecran simplement : effectuer une rotation
     * Toucher vers le bas : faire tomber la piece
     * Toucher vers la droite ou la gauche : faire bouger la piece vers la droite gauche*/
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float x = e.getX();
            float y = e.getY();

            lastX = x;
            lastY = y;
        } else if(e.getActionMasked() == MotionEvent.ACTION_MOVE) {
            float x = e.getX();
            float y = e.getY();

            float deltaX = Math.abs(x - lastX);
            float deltaY = Math.abs(y - lastY);

            if(!moved) {
                if (deltaX > 50) {
                    if (x < lastX) {
                        mGame.moveLeft();
                    } else {
                        mGame.moveRight();
                    }
                    moved = true;
                }
                if (deltaY > 50) {
                    if (y > lastY) {
                        mGame.moveDownDown();
                    }
                    moved = true;
                }
            }
        } else if(e.getActionMasked() == MotionEvent.ACTION_UP) {
            if(!moved) {
                mGame.rotateCurrentPiece();
            } else {
                moved = false;
            }
        }



        return true;
    }

    public void updateScore(int score) {
        String s = "Score : " + Integer.toString(score);
        mLabelView.setText(s);
    }
}
