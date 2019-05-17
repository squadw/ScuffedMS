package squadw.scuffedms.game.tile;

import squadw.scuffedms.game.button.GButton;

import javax.swing.*;
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
        this.isMine = false;
        setClosed();
        setImage();
        button.addMouseListener(new MouseAdapter() {
            boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                button.getModel().setArmed(true);
                button.getModel().setPressed(true);
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.getModel().setArmed(false);
                button.getModel().setPressed(false);
                if (pressed) {
                    if (SwingUtilities.isRightMouseButton(e)) setMarked();
                    else setOpened();
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



    public Tile(boolean isMine) {
        this();
        this.isMine = isMine;
        setImage();
    }

    public void setImage() {
        button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/tile.png")));
        if (isMine)
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/bomb.png")));
        else if (tileState == OPENED)
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/flat.png")));
        else if (tileState == MARKED) {
            button.setIcon(new ImageIcon(getClass().getResource("/squadw/scuffedms/resources/images/flag.png")));
        }
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
