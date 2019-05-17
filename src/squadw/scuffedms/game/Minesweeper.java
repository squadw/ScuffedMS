package squadw.scuffedms.game;

import squadw.scuffedms.game.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Minesweeper extends JFrame {

    private Board board;
    private int wh;

    public Minesweeper() {
        board = new Board(16);
        wh = board.getSize() * 40;
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
        setSize(wh, wh + 10);
        setFocusable(true);
        setResizable(false);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }

        initButtons();
    }
}
