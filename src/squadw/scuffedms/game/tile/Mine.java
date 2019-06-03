package squadw.scuffedms.game.tile;

import javax.swing.*;

public class Mine extends Tile {

    private boolean exploded;

    public Mine() {
        super();
        exploded = false;
    }

    public boolean isExploded() {
        return exploded;
    }

    @Override
    public void setOpened() {
        super.setOpened();
        exploded = true;
    }

    // Used to change what each mine displays depending on its state.
    @Override
    public void setImage() {
        getButton().setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/tile.png")));
        if (exploded)
            getButton().setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/bomb.png")));
        else if (getTileState() == MARKED) {
            getButton().setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/flag.png")));
        }
    }

    @Override
    public String toString() {
        return "X";
    }
}
