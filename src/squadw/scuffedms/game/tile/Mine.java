package squadw.scuffedms.game.tile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mine extends Tile {

    boolean exploded;

    public Mine() {
        super();
        exploded = false;
    }

    @Override
    protected void mouseListener() {
        getButton().addMouseListener(new MouseAdapter() {
            boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed) {
                    if (getTileState() == MARKED && SwingUtilities.isRightMouseButton(e)) {
                        setClosed();
                    } else {
                        if (SwingUtilities.isRightMouseButton(e) && getTileState() != OPENED) setMarked();
                        else explode();
                    }
                    setImage();
                    pressed = false;
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                pressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pressed = true;
            }
        });
    }

    public void explode() {
        exploded = true;
//        setImage(); - set image before ending the game so the bomb img still gets rendered and put on the board
//        do something here to end the game probably just run method that ends game or switches to a new jframe with a "You lost! Play again?" screen
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
