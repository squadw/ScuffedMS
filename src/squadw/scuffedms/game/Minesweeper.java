package squadw.scuffedms.game;

import squadw.scuffedms.game.board.Board;
import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Minesweeper extends JFrame {

    private Board board;
    private int w;
    private int h;

    public Minesweeper(int s, int d) {
        board = new Board(s,d);
        w = board.getSize() * 40;
        h = board.getSize() * 40 + 20;
        initFrame();
        initButtons();
        setVisible(true);
        printBoard();
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
                add(board.getBoard()[i][j].getButton());
            }
        }
    }

    private void initFrame() {
        setSize(w, h);
        setFocusable(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }
    }
}
