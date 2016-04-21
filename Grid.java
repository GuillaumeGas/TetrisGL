package fr.univ_orleans.info.tetrisgl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 20/04/2016.
 */
public class Grid {
    private final int width = 10;
    private final int height = 20;
    private Piece current_piece = null;
    private int[][] grid;
    private int[][] tmp_grid;
    private int score = 0;
    private boolean game_over = false;

    private final int SIZE_PIECE = 4;

    public Grid() {
        grid = new int[height][width];
        tmp_grid = new int[height][width];
    }

    public void init() {
        game_over = false;
        score = 0;
        current_piece = null;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                grid[i][j] = 0;
                tmp_grid[i][j] = 0;
            }
        }
    }

    public void clear() {
        init();
    }

    public void add_current_piece(Piece p) {
        current_piece = p;
        int[] coord_start_flood = current_piece.get_start_flood_coord();

        check_grid();

        if(is_movable(current_piece.getPosX(),
                    current_piece.getPosY(),
                    coord_start_flood[0],
                    coord_start_flood[1],
                    current_piece.getVal())) {
            draw_piece(current_piece.getPosX(),
                    current_piece.getPosY(),
                    coord_start_flood[0],
                    coord_start_flood[1],
                    current_piece.getVal());
        } else {
            game_over = true;
            current_piece = null;
        }
    }

    public void block_current_piece() {
        boolean[][] visited = new boolean[SIZE_PIECE][SIZE_PIECE];
        for(int i = 0; i < SIZE_PIECE; i++) {
            for(int j = 0; j < SIZE_PIECE; j++) {
                visited[i][j] = false;
            }
        }

        int[] coord_start_flood = current_piece.get_start_flood_coord();
        block_current_piece_using_flood(current_piece.getPosX(), current_piece.getPosY(), coord_start_flood[0], coord_start_flood[1], current_piece.getVal(), visited);

        current_piece = null;
    }

    public void block_current_piece_using_flood(int grid_x, int grid_y, int piece_x, int piece_y, int val, boolean visited[][]) {
        if(grid_x < 0 || grid_x >= width || grid_y < 0 || grid_y >= height || grid[grid_y][grid_x] != val) {
            return;
        }

        int[][] piece_grid = current_piece.get_grid();
        if(piece_x < 0 || piece_x >= SIZE_PIECE || piece_y < 0 || piece_y >= SIZE_PIECE || piece_grid[piece_y][piece_x] == 0 || visited[piece_y][piece_x]) {
            return;
        }

        grid[grid_y][grid_x] *= -1;
        tmp_grid[grid_y][grid_x] = grid[grid_y][grid_x];
        visited[piece_y][piece_x] = true;

        block_current_piece_using_flood(grid_x+1, grid_y, piece_x+1, piece_y, val, visited);
        block_current_piece_using_flood(grid_x-1, grid_y, piece_x-1, piece_y, val, visited);
        block_current_piece_using_flood(grid_x, grid_y+1, piece_x, piece_y+1, val, visited);
        block_current_piece_using_flood(grid_x, grid_y-1, piece_x, piece_y-1, val, visited);
    }

    public boolean move_down() {
        if(current_piece != null) {
            int[] coord_start_flood = current_piece.get_start_flood_coord();

            if(is_movable(current_piece.getPosX(),
                    current_piece.getPosY()+1,
                    coord_start_flood[0],
                    coord_start_flood[1],
                    current_piece.getVal())) {

                clear_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());

                current_piece.setPosY(current_piece.getPosY() + 1);

                draw_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());




                return true;

            }
            block_current_piece();
            return false;
        }
        return false;
    }

    public boolean move_right() {
        if(current_piece != null) {
            int[] coord_start_flood = current_piece.get_start_flood_coord();

            if(is_movable(current_piece.getPosX()+1,
                    current_piece.getPosY(),
                    coord_start_flood[0],
                    coord_start_flood[1],
                    current_piece.getVal())) {

                clear_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());

                current_piece.setPosX(current_piece.getPosX() + 1);

                draw_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());



                return true;

            }
            return false;
        }
        return false;
    }

    public boolean move_left() {
        if(current_piece != null) {
            int[] coord_start_flood = current_piece.get_start_flood_coord();

            if(is_movable(current_piece.getPosX()-1,
                    current_piece.getPosY(),
                    coord_start_flood[0],
                    coord_start_flood[1],
                    current_piece.getVal())) {

                clear_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());

                current_piece.setPosX(current_piece.getPosX()-1);

                draw_piece(current_piece.getPosX(),
                        current_piece.getPosY(),
                        coord_start_flood[0],
                        coord_start_flood[1],
                        current_piece.getVal());



                return true;

            }
            return false;
        }
        return false;
    }

    /* 
        
    */
    public boolean is_movable(int grid_x, int grid_y, int piece_x, int piece_y, int val) {
        //boolean res = true;
        boolean[][] visited = new boolean[SIZE_PIECE][SIZE_PIECE];
        for(int x = 0; x < SIZE_PIECE; x++) {
            for(int y = 0; y < SIZE_PIECE; y++) {
                visited[y][x] = false;
            }
        }
        return is_movable_using_flood(grid_x, grid_y, piece_x, piece_y, val, visited);
        //return res;
    }

    public boolean is_movable_using_flood(int grid_x, int grid_y, int piece_x, int piece_y, int val, boolean visited[][]) {
        int[][] matrix_piece = current_piece.get_grid();
        if( piece_x < 0 || piece_x >= SIZE_PIECE ||
                piece_y < 0 || piece_y >= SIZE_PIECE ||
                matrix_piece[piece_y][piece_x] != val || visited[piece_y][piece_x]) {
            return true;
        }

        if( grid_x < 0 || grid_x >= width ||
                grid_y < 0 || grid_y >= height ||
                tmp_grid[grid_y][grid_x] != 0) {
            //res = false;
            return false;
        }

        visited[piece_y][piece_x] = true;

        return is_movable_using_flood(grid_x + 1, grid_y, piece_x + 1, piece_y, val, visited) &&
        is_movable_using_flood(grid_x - 1, grid_y, piece_x - 1, piece_y, val, visited) &&
        is_movable_using_flood(grid_x, grid_y + 1, piece_x, piece_y + 1, val, visited) &&
        is_movable_using_flood(grid_x, grid_y - 1, piece_x, piece_y - 1, val, visited);
    }

    public void draw_piece(int grid_x, int grid_y, int piece_x, int piece_y, int val) {
        boolean[][] visited = new boolean[SIZE_PIECE][SIZE_PIECE];
        for(int x = 0; x < SIZE_PIECE; x++) {
            for(int y = 0; y < SIZE_PIECE; y++) {
                visited[y][x] = false;
            }
        }
        draw_piece_using_flood(grid_x, grid_y, piece_x, piece_y, val, visited);
        show();
    }

    public void draw_piece_using_flood(int grid_x, int grid_y, int piece_x, int piece_y, int val, boolean visited[][]) {
        int[][] matrix_piece = current_piece.get_grid();
        if( piece_x < 0 || piece_x >= SIZE_PIECE ||
                piece_y < 0 || piece_y >= SIZE_PIECE ||
                matrix_piece[piece_y][piece_x] != val ||
                visited[piece_y][piece_x]) {
        } else {
            grid[grid_y][grid_x] = val;
            visited[piece_y][piece_x] = true;

            draw_piece_using_flood(grid_x + 1, grid_y, piece_x + 1, piece_y, val, visited);
            draw_piece_using_flood(grid_x - 1, grid_y, piece_x - 1, piece_y, val, visited);
            draw_piece_using_flood(grid_x, grid_y + 1, piece_x, piece_y + 1, val, visited);
            draw_piece_using_flood(grid_x, grid_y - 1, piece_x, piece_y - 1, val, visited);
        }
    }

    public void clear_piece(int grid_x, int grid_y, int piece_x, int piece_y, int val) {
        boolean[][] visited = new boolean[SIZE_PIECE][SIZE_PIECE];
        for(int x = 0; x < SIZE_PIECE; x++) {
            for(int y = 0; y < SIZE_PIECE; y++) {
                visited[y][x] = false;
            }
        }
        clear_piece_using_flood(grid_x, grid_y, piece_x, piece_y, val, visited);
    }

    public void clear_piece_using_flood(int grid_x, int grid_y, int piece_x, int piece_y, int val, boolean visited[][]) {
        int[][] matrix_piece = current_piece.get_grid();
        if( piece_x < 0 || piece_x >= SIZE_PIECE ||
                piece_y < 0 || piece_y >= SIZE_PIECE ||
                matrix_piece[piece_y][piece_x] != val ||
                visited[piece_y][piece_x]) {
            return;
        }

        grid[grid_y][grid_x] = 0;
        visited[piece_y][piece_x] = true;

        clear_piece_using_flood(grid_x + 1, grid_y, piece_x + 1, piece_y, val, visited);
        clear_piece_using_flood(grid_x - 1, grid_y, piece_x - 1, piece_y, val, visited);
        clear_piece_using_flood(grid_x, grid_y + 1, piece_x, piece_y + 1, val, visited);
        clear_piece_using_flood(grid_x, grid_y - 1, piece_x, piece_y - 1, val, visited);
    }


    public void rotate() {
        clear_piece(current_piece.getPosX(), current_piece.getPosY(), current_piece.get_start_flood_coord()[0], current_piece.get_start_flood_coord()[1], current_piece.getVal());
        current_piece.rotate_right();

        int[] coord_start_flood = current_piece.get_start_flood_coord();
        if(!is_movable(current_piece.getPosX(),
                current_piece.getPosY(),
                coord_start_flood[0],
                coord_start_flood[1],
                current_piece.getVal())) {
            current_piece.rotate_left();
        }

        draw_piece(current_piece.getPosX(), current_piece.getPosY(), current_piece.get_start_flood_coord()[0], current_piece.get_start_flood_coord()[1], current_piece.getVal());
    }

    public boolean is_game_over()  {
        return game_over;
    }

    public void check_grid() {
        check_grid_recur();
    }


    public void check_grid_recur() {
        for(int y = height-1; y >=0 ; y--) {
            int x = 0;
            boolean full_line = true;
            while(full_line && x < width) {
                //System.out.println("[X][Y] = [" + Integer.toString(x) + "][" + Integer.toString(y) +"]");
                if(tmp_grid[y][x] == 0) {
                    full_line = false;
                }
                x++;
            }
            if(full_line) {
                lines_move_down_until(y);
            }
        }
    }

    public void lines_move_down_until(int _y) {
        for(int y = _y; y > 0; y--) {
            for(int x = 0; x < width; x++) {
                grid[y][x] = grid[y-1][x];
                tmp_grid[y][x] = tmp_grid[y-1][x];
            }
        }
    }

    public void show() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(Integer.toString(grid[i][j]));
            }
            System.out.println("");
        }
    }



    public int[][] get_grid() {
        return grid;
    }

    public void test() {
        for(int x = 0; x < width; x++) {
            grid[15][x] = -1;
            tmp_grid[15][x] = -1;
        }
    }


    public Piece getCurrentPiece(){
        return current_piece;
    }

    public int getWidth() {
        return width;
    }


    public int getHeightt() {
        return height;
    }

}
