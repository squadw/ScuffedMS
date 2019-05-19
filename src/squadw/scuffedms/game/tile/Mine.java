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
        //setImage(); - set image before ending the game so the bomb img still gets rendered and put on the board
        //do something here to end the game probably just run method that ends game or switches to a new jframe with a "You lost! Play again?" screen
    }

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
