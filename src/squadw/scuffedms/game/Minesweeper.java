package squadw.scuffedms.game;

import squadw.scuffedms.game.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minesweeper extends JFrame implements MouseListener, ActionListener {

    private Board board = new Board(16);

    public Minesweeper() {
        initFrame();
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void printBoard() {
        Tile[][] temp = board.getBoard();

        for (int i = 0; i < board.getSize(); i++) {
            System.out.println();
            for (int j = 0; j < board.getSize(); j++) {
                System.out.print(temp[i][j] + " ");
            }
        }
    }

    private void initFrame() {
        setSize(800, 800);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void paint(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(1,1,800,800);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
