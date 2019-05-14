package squadw.scuffedms.game.tile;

public class Tile {

    //State of the tile 0 = closed, 1 = opened, 2 = marked
    protected final int CLOSED = 0;
    protected final int OPENED = 1;
    protected final int MARKED = 2;
    private int tileState;
    private int numBombs;

    public Tile() {
        this.tileState = 0;
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
}
