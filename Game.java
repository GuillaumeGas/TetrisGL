package fr.univ_orleans.info.tetrisgl;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Guillaume on 19/04/2016.
 */
public class Game {

    private Grid grid;
    private Piece current_piece;
    private int screen_width;
    private int screen_height;
    private Timer timer;
    private MyGLSurfaceView view;

    public Game(MyGLSurfaceView v) {
        view = v;
    }

    public void init(int w, int h) {
        screen_width = w;
        screen_height = h;
        grid = new Grid();
        current_piece = new Piece();
        grid.add_current_piece(current_piece);
        start();
    }

    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveDown();
                view.requestRender();
            }
        }, 0, 500);
    }

    public int[][] getGrid() { return grid.get_grid(); }

    public void moveDown() {
        grid.move_down();
        if(grid.is_game_over()) {
            //view.showScore(grid.getScore());
            grid.clear();
            grid.init();
        } else if(grid.getCurrentPiece() == null) {
            current_piece = new Piece();
            grid.add_current_piece(current_piece);
        }
    }
    public void moveRight() {
        grid.move_right();
    }
    public void moveLeft() {
        grid.move_left();
    }
    public void rotateCurrentPiece() { grid.rotate(); }

    public int getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(int screen_width) {
        this.screen_width = screen_width;
    }

    public int getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(int screen_height) {
        this.screen_height = screen_height;
    }
}
