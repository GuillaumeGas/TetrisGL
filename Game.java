package fr.univ_orleans.info.tetrisgl;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Guillaume on 19/04/2016.
 */
public class Game {

    private List<Square> mPieces;
    private Square current_piece;
    private int mWidth;
    private int mHeight;

    public void init(int w, int h) {
        mWidth = w;
        mHeight = h;
        mPieces = new LinkedList<>();
        mPieces.add(new Square(mWidth, mHeight));
        current_piece = mPieces.get(0);
    }

    public void moveDown() {
        current_piece.setPosY(current_piece.getPosY() - 0.1f);
    }

    public void moveRight() {
        current_piece.setPosX(current_piece.getPosX() - 0.1f);
    }

    public void moveLeft() {
        current_piece.setPosX(current_piece.getPosX() + 0.1f);
    }

    public List<Square> getPieces() { return mPieces; }
}
