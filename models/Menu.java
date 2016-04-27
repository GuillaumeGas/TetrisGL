package fr.univ_orleans.info.tetrisgl.models;

import android.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import fr.univ_orleans.info.tetrisgl.views.MenuSurfaceView;

/**
 * Created by Guillaume on 26/04/2016.
 */
public class Menu {

    MenuSurfaceView view;
    private Timer timer;
    List<Coord> coords;
    int count = 0;

    public Menu(MenuSurfaceView v) {
        view = v;
    }

    public void init() {
        coords = new LinkedList<>();
        start();
    }

    /* Lance le timer, appelle fait tomber la piece et actualise l'affichage toutes les 500ms */
    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (count % 10 == 0) {
                    createPiece();
                }
                moveDown();
                view.requestRender();
                count++;
            }
        }, 0, 50);
    }

    public List<Coord> getCoords() {
        return coords;
    }

    private void createPiece() {
        Random r = new Random();
        coords.add(new Coord(r.nextInt(15), 0));
    }

    private void moveDown() {
        Vector<Integer> to_delete = new Vector<>();
        int i = 0;
        for(Coord c : coords) {
            if(c.y <= 20) {
                c.y += 0.1;
            } else {
                to_delete.add(new Integer(i));
            }
            i++;
        }
        for(Integer index : to_delete) {
            coords.remove(index.intValue());
        }
    }
}
