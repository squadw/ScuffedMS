package squadw.scuffedms.game;

import squadw.scuffedms.Main;
import squadw.scuffedms.game.board.Board;
import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Minesweeper extends JFrame {

    private JPanel content;
    private JPanel boardPanel;
    private JPanel textPanel;
    private JSplitPane splitPane;

    private JLabel bombsLeft;

    private Board board;
    private int w;
    private int h;

    private static Instant start;
    private static Instant end;
    private static Duration interval;
    private static long minutes;
    private static long seconds;

    public Minesweeper(int s, int d) {
        board = new Board(s,d);
        w = board.getSize() * 40 + 20;
        h = board.getSize() * 40 + 80;
        tileMouseListener();
        initUI();
        setVisible(true);
        startTimer();
        printBoard();
    }

    public void startTimer() {
        start = Instant.now();
    }

    public void endTimer() {
        end = Instant.now();
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
                boardPanel.add(board.getBoard()[i][j].getButton());
            }
        }
    }

    private void initUI() {
       splitPane = new JSplitPane();
       boardPanel = new JPanel();
       textPanel = new JPanel();

       bombsLeft = new JLabel("Bombs Left: " + board.numBombsLeft());

       initFrame();
       getContentPane().setLayout(new GridLayout());
       getContentPane().add(splitPane);

       splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
       splitPane.setDividerSize(0);
       splitPane.setDividerLocation(h - 80);
       splitPane.setTopComponent(boardPanel);
       splitPane.setBottomComponent(textPanel);

       textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
       boardPanel.setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));

       textPanel.add(bombsLeft);
       initButtons();
    }

    private void updateMineLabel() {
        bombsLeft.setText("Bombs Left: " + board.numBombsLeft());
    }

    private void initFrame() {
        setSize(w, h);
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

    private void tileMouseListener() {
        for (Tile[] b: board.getBoard())
            for (Tile t : b) {
                t.getButton().addMouseListener(new MouseAdapter() {
                    boolean pressed;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = true;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (pressed) {
                            if (!SwingUtilities.isRightMouseButton(e))
                                board.revealBoard(t.getCoords()[0], t.getCoords()[1]);
                            if (t.getTileState() == Tile.MARKED && SwingUtilities.isRightMouseButton(e)) {
                                t.setClosed();
                                updateMineLabel();
                            }
                            else if (SwingUtilities.isRightMouseButton(e) && t.getTileState() != Tile.OPENED) {
                                t.setMarked();
                                updateMineLabel();
                            }
                            else if (t.getTileState() != Tile.MARKED) t.setOpened();
                            if (t.getNumBombs() == 0)
                                board.openAround(t.getCoords()[0], t.getCoords()[1]);
                            tryToEnd(board.checkForGameEnd());

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
    }

    public void tryToEnd(Boolean status) {
        if (status != null) {;
            Main.endGame(status);
        }
    }

}
