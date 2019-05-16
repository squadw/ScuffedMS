package squadw.scuffedms.game;

import squadw.scuffedms.game.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minesweeper extends JFrame {

    private Board board;

    public Minesweeper() {
        board = new Board(16);
        setLayout(new GridBagLayout());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
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
        setSize(800, 800);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initButtons();
    }

    public void paint(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(1,1,800,800);
    }
}
