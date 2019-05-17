package squadw.scuffedms.game.tile;

import squadw.scuffedms.game.button.GButton;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tile {

    //State of the tile 0 = closed, 1 = opened, 2 = marked
    public final int CLOSED = 0;
    public final int OPENED = 1;
    public final int MARKED = 2;
    private int tileState;
    private GButton button = new GButton();

    public Tile() {
        setClosed();
        setImage();
        mouseListener();
    }

    protected void mouseListener() {
        button.addMouseListener(new MouseAdapter() {
            boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed) {
                    if (tileState == MARKED && SwingUtilities.isRightMouseButton(e)) setClosed();
                    else if (SwingUtilities.isRightMouseButton(e) && getTileState() != OPENED) setMarked();
                    else setOpened();
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

    public void setImage() {
        button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/tile.png")));
        if (tileState == OPENED)
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/flat.png")));
        else if (tileState == MARKED) {
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/flag.png")));
        } else if (tileState == CLOSED) {
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/tile.png")));
        }
    }

    public GButton getButton() {
        return button;
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

    public int getTileState() {
        return tileState;
    }

    @Override
    public String toString() {
        return ".";
    }
}
