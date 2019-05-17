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
                    if (SwingUtilities.isRightMouseButton(e)) setMarked();
                    else explode();
                    setImage();
                }
                pressed = false;
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
