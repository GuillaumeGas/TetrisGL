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
        current_piece.setPosY(current_piece.getPosY()-0.5f);
    }

    public void moveRight() {

    }

    public void moveLeft() {

    }

    public List<Square> getPieces() { return mPieces; }
}
