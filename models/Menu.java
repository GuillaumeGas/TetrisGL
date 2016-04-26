package fr.univ_orleans.info.tetrisgl.models;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.univ_orleans.info.tetrisgl.views.MenuSurfaceView;

/**
 * Created by Guillaume on 26/04/2016.
 */
public class Menu {

    MenuSurfaceView view;
    private Timer timer;
    int[] coords;

    public Menu(MenuSurfaceView v) {
        view = v;
    }

    public void init() {
        coords = new int[2];
        Random r = new Random();
        coords[0] = r.nextInt(10);
        coords[1] = 0;

        start();
    }

    /* Lance le timer, appelle fait tomber la piece et actualise l'affichage toutes les 500ms */
    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                coords[1]++;
                view.requestRender();
            }
        }, 0, 500);
    }

    public int[] getCoords() {
        return coords;
    }
}
