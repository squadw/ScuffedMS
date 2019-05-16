package squadw.scuffedms.game;

import squadw.scuffedms.game.tile.Tile;

import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame {

    private Board board;
    private int wh;

    public Minesweeper() {
        board = new Board(9);
        wh = board.getSize() * 50;
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));
        initFrame();
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

    private void initButtons() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.getBoard()[i][j].getButton().addButtonToFrame(this);
            }
        }
    }

    private void initFrame() {
        setSize(wh, wh);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initButtons();
    }

    /*public void paint(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(1,1,wh,wh);
    }*/
}
