package squadw.scuffedms.game.tile;

import squadw.scuffedms.game.button.GButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tile {

    //State of the tile 0 = closed, 1 = opened, 2 = marked
    protected final int CLOSED = 0;
    protected final int OPENED = 1;
    protected final int MARKED = 2;
    private int tileState;
    private int numBombs;
    private boolean isMine;
    private GButton button = new GButton();

    public Tile() {
        this.tileState = 0;
        this.isMine = false;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) setMarked();
                else if (e.getClickCount() == 1) setOpened();
            }
        });
    }



    public Tile(boolean isMine) {
        this();
        this.isMine = isMine;
    }

    public GButton getButton() {
        return button;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setClosed() {
        setTileState(CLOSED);
    }

    public void setOpened() {
        setTileState(OPENED);
    }

    public void setMarked() {
        setTileState(MARKED);
    }

    private void setTileState(int s) {
        this.tileState = s;
    }

    @Override
    public String toString() {
        return ".";
    }
}
