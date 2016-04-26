package fr.univ_orleans.info.tetrisgl.views;

/**
 * Created by Guillaume on 19/04/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import fr.univ_orleans.info.tetrisgl.controllers.GameActivity;
import fr.univ_orleans.info.tetrisgl.models.Menu;

/**
 Class MenuView : gère les évènements, créé le renderer du menu
 */
public class MenuSurfaceView extends GLSurfaceView {

    private final MenuRenderer mRenderer;
    private Menu mMenu;
    private Context context;

    public MenuSurfaceView(Context context) {
        super(context);

        this.context = context;
        // Création d'un context OpenGLES 2.0
        setEGLContextClientVersion(2);

        mMenu = new Menu(this);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MenuRenderer(mMenu, this);
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /* Comment interpréter les événements sur l'écran tactile */
    /* Toucher l'ecran simplement : jouer
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getActionMasked() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(context, GameActivity.class);
            context.startActivity(intent);
        }
        return true;
    }
}
