package fr.univ_orleans.info.tetrisgl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Guillaume on 19/04/2016.
 *
 * Comporte une grille (voir Grid.java) et la piece courante (voir Piece.java)
 * Ses méthodes, appelées depuis la vue, permettent de gérer la piece courante et d'effectuer des actions sur la grille
 * On y trouve aussi un timer permettant d'effectuer une action toutes les 500ms pour faire tomber la piece petit à petit
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

    /* Lance le timer, appelle fait tomber la piece et actualise l'affichage toutes les 500ms */
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

    /*
       Fait tomber la piece d'une case, si la piece courante est nulle dans la grille,
       c'est qu'elle est arrivée en bas alors on en génère une nouvelle.
       En cas de game over, on ré initialise tout
    */
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

    /* Meme chose mais on fait tomber la piece tout en bas */
    public void moveDownDown() {
        while(grid.getCurrentPiece() != null) {
            grid.move_down();
        }
        if(grid.is_game_over()) {
            //view.showScore(grid.getScore());
            grid.clear();
            grid.init();
        } else {
            current_piece = new Piece();
            grid.add_current_piece(current_piece);
        }
    }

    /* Fonctions demandant à la grille de faire bouger la piece courante vers la droite, la gauche, ou d'effectuer une rotation dessus */
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
