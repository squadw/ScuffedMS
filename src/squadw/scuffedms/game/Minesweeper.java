package squadw.scuffedms.game;

import squadw.scuffedms.game.board.Board;
import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Minesweeper extends JFrame {

    private JPanel content;
    private JPanel boardPanel;
    private JPanel textPanel;
    private JSplitPane splitPane;

    private Board board;
    private int w;
    private int h;

    public Minesweeper(int s, int d) {
        board = new Board(s,d);
        w = board.getSize() * 40;
        h = board.getSize() * 40;
        initUI();
        setVisible(true);
        //printBoard();
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
                board.getBoard()[i][j].getButton().addButtonToFrame(boardPanel);
            }
        }
    }

    private void initUI() {
       splitPane = new JSplitPane();

       boardPanel = new JPanel();
       textPanel = new JPanel();

       initFrame();
       getContentPane().setLayout(new GridLayout());
       getContentPane().add(splitPane);

       splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
       splitPane.setDividerLocation(h/2);
       splitPane.setTopComponent(boardPanel);
       splitPane.setBottomComponent(textPanel);

       boardPanel.setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));
       initButtons();
    }

    private void initLayout(){

    }

    private void initFrame() {
        //setSize(w, h);
        setPreferredSize(new Dimension(w, h));
        setFocusable(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }
    }
}
