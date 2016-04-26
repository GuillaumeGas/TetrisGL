package fr.univ_orleans.info.tetrisgl;

import java.util.Random;

/**
 * Created by Guillaume on 20/04/2016.
 *
 * Une piece symbolisée par les valeurs positives dans une grille 4*4
 */
public class Piece {

    private int val;
    private int posX;
    private int posY;
    private int[][] grid;
    private int[] start_flood_coord; //1ere case de la grille à etre != 0

    public static final int SIZE_PIECE = 4;

    public Piece() {
        grid = new int[4][4];
        val = 1;
        start_flood_coord = new int[2];
        start_flood_coord[0] = 0;
        start_flood_coord[1] = 0;
        generate();
        posX = 4;
        posY = 0;
    }

    public Piece(Piece p) {
        val = p.val;
        posX = p.posX;
        posY = p.posY;
        grid = new int[4][4];
        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                grid[i][j] = p.grid[i][j];
            }
        }
        start_flood_coord = new int[2];
        start_flood_coord[0] = p.start_flood_coord[0];
        start_flood_coord[1] = p.start_flood_coord[1];
    }

    /* Genere une piece aleatoire a partie du tableau de formes (voir plus bas), on effectue également un nombre aléatoire de rotation */
    public void generate() {
        Random rand = new Random();
        int random = rand.nextInt(7);
        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                grid[i][j] = shapes[random][i][j];
            }
        }
        val = random+1;

        random = rand.nextInt(4);
        for(int i = 0; i < random; i++) {
            rotate_right();
        }

        determine_start_flood_coord();
    }

    public void rotate_right() {
        int[][] tmp = new int[SIZE_PIECE][SIZE_PIECE];

        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                tmp[i][j] = grid[SIZE_PIECE-1-j][i];
            }
        }

        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                grid[i][j] = tmp[i][j];
            }
        }

        determine_start_flood_coord();
    }

    public void rotate_left() {
        int[][] tmp = new int[SIZE_PIECE][SIZE_PIECE];

        for(int i = SIZE_PIECE-1; i >= 0; i--) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                tmp[SIZE_PIECE-i-1][j] = grid[j][i];
            }
        }

        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                grid[i][j] = tmp[i][j];
            }
        }

        determine_start_flood_coord();
    }

    public void determine_start_flood_coord() {
        for(int y = 0; y < SIZE_PIECE; y++) {
            for(int x = 0; x < SIZE_PIECE; x++) {
                if(grid[y][x] != 0) {
                    start_flood_coord[0] = x;
                    start_flood_coord[1] = y;
                    return;
                }
            }
        }
    }

    public int[] get_start_flood_coord() {
        return start_flood_coord;
    }

    public int[][] get_grid() {
        return grid;
    }

    public void show() {
        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                if(grid[i][j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(Integer.toString(grid[i][j]));
                }
            }
            System.out.println("");
        }
    }

    public int getVal() { return val;}
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public void setPosX(int x) { posX = x; }
    public void setPosY(int y) { posY = y; }
    public void setVal(int v) { val = v; }

    public static final int[][][] shapes = {
            {
                    {0, 0, 0, 0},
                    {1, 1, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {2, 2, 2, 2},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {0, 3, 3, 0},
                    {3, 3, 0, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {4, 4, 0, 0},
                    {0, 4, 4, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {5, 5, 5, 0},
                    {5, 0, 0, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {6, 6, 6, 0},
                    {0, 0, 6, 0},
                    {0, 0, 0, 0},
            },
            {
                    {0, 0, 0, 0},
                    {7, 7, 7, 0},
                    {0, 7, 0, 0},
                    {0, 0, 0, 0},
            }
    };
}
