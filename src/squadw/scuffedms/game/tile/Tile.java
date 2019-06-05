package squadw.scuffedms.game.tile;

import squadw.scuffedms.game.button.GButton;

import javax.swing.*;

public class Tile {

    //State of the tile 0 = closed, 1 = opened, 2 = marked
    public static final int CLOSED = 0;
    public static final int OPENED = 1;
    public static final int MARKED = 2;
    public static final int GUESSED = 3;
    private int tileState;
    private int numBombs;
    private int numFlags;
    private int x;
    private int y;
    private GButton button = new GButton();
    private String resourcePath = "/squadw/scuffedms/resources/images/new/";

    public Tile() {
        setClosed();
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    public void setNumFlags(int numFlags) { this.numFlags = numFlags; }

    public int getNumBombs() {
        return numBombs;
    }

    public int getNumFlags() { return numFlags; }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getCoords() {
        return new int[] {x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Used to change what each tile displays depending on its state.
    public void setImage() {
        button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "tile.png")));
        if (tileState == OPENED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "flat.png")));
        else if (tileState == MARKED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "flag.png")));
        else if (tileState == GUESSED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "guess.png")));
        if (tileState == OPENED && numBombs != 0) {
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + numBombs + ".png")));
        }
    }

    // Used to change what each tile displays depending on its state.
    public void setImage(int i) {
        if (i == CLOSED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "tile.png")));
        else if (i == OPENED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "flat.png")));
        else if (i == MARKED)
            button.setIcon(new ImageIcon(getClass().getResource(resourcePath + "flag.png")));
    }

    public GButton getButton() {
        return button;
    }

    public void setClosed() {
        setTileState(CLOSED);
        setImage();
    }

    public void setOpened() {
        setTileState(OPENED);
        setImage();
    }

    public void setMarked() {
        setTileState(MARKED);
        setImage();
    }

    public void setGuessed() {
        setTileState(GUESSED);
        setImage();
    }

    private void setTileState(int s) {
        this.tileState = s;
    }

    public int getTileState() {
        return tileState;
    }

    @Override
    public String toString() {
        return ".";
    }
}
