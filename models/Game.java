package fr.univ_orleans.info.tetrisgl.models;

import java.util.Timer;
import java.util.TimerTask;

import fr.univ_orleans.info.tetrisgl.views.GameSurfaceView;

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
    private Piece next_piece;
    private int screen_width;
    private int screen_height;
    private Timer timer;
    private GameSurfaceView view;

    public Game(GameSurfaceView v) {
        view = v;
    }

    public void init(int w, int h) {
        screen_width = w;
        screen_height = h;
        grid = new Grid();
        current_piece = new Piece();
        next_piece = new Piece();
        grid.add_current_piece(current_piece);

        System.out.println("[START]\nCP = " + Integer.toString(current_piece.getVal()));
        System.out.println("NP = " + Integer.toString(next_piece.getVal()));

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

    public Grid getGrid() { return grid; }

    /*
       Fait tomber la piece d'une case, si la piece courante est nulle dans la grille,
       c'est qu'elle est arrivée en bas alors on en génère une nouvelle.
       En cas de game over, on ré initialise tout
    */
    public void moveDown() {
        grid.move_down(true);
        if(grid.is_game_over()) {
            //view.showScore(grid.getScore());
            grid.clear();
            grid.init();
            view.updateScore(0);
        } else if(grid.getCurrentPiece() == null) {
            grid.add_current_piece(next_piece);
            current_piece = next_piece;

            System.out.println("CP = " + Integer.toString(current_piece.getVal()));
            //current_piece.show();

            next_piece = new Piece();

            System.out.println("NP = " + Integer.toString(next_piece.getVal()));
            //next_piece.show();

            view.updateScore(grid.getScore());
        }
    }

    /* Meme chose mais on fait tomber la piece tout en bas */
    public void moveDownDown() {
        while(grid.move_down(false)) {}
        if(grid.is_game_over()) {
            grid.clear();
            grid.init();
            view.updateScore(0);
        } else {
            grid.add_current_piece(next_piece);
            current_piece = next_piece;
            System.out.println("CP = " + Integer.toString(current_piece.getVal()));
            //current_piece.show();

            next_piece = new Piece();

            System.out.println("NP = " + Integer.toString(next_piece.getVal()));
            //next_piece.show();
            view.updateScore(grid.getScore());
        }
    }

    /* Fonctions demandant à la grille de faire bouger la piece courante vers la droite, la gauche, ou d'effectuer une rotation dessus */
    public void moveRight() {
        grid.move_right();
    }
    public void moveLeft() {
        grid.move_left();
    }
    public void rotateCurrentPiece() { grid.rotate();
    }

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

    public Piece getNextPiece() { return next_piece; }

    public Piece getCurrentPiece() {
        return current_piece;
    }
}
