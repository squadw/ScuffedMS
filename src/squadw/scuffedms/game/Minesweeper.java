package squadw.scuffedms.game;

import squadw.scuffedms.game.board.Board;
import squadw.scuffedms.game.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends JFrame {

    private JPanel boardPanel;
    private JPanel textPanel;
    private JSplitPane splitPane;

    private JLabel bombsLeft;
    private JLabel timePassed;

    private Board board;
    private int w;
    private int h;

    Timer timer;
    private long minutes;
    private long seconds;
    private long overallTime;

    public Minesweeper(int s, int d) {
        board = new Board(s,d);
        w = board.getSize() * 40 + 20;
        h = board.getSize() * 40 + 70;
        tileMouseListener();
        initUI();
        setVisible(true);
        startTimer();
        printBoard();
    }

    public void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                overallTime++;
                minutes = overallTime / 60;
                seconds = overallTime % 60;
                updateTimeLabel();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
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
       timePassed = new JLabel("Time: 0s");

       initFrame();
       getContentPane().setLayout(new GridLayout());
       getContentPane().add(splitPane);

       splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
       splitPane.setDividerSize(0);
       splitPane.setDividerLocation(h - 70);
       splitPane.setTopComponent(boardPanel);
       splitPane.setBottomComponent(textPanel);

       textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
       boardPanel.setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));

       textPanel.add(bombsLeft);
       textPanel.add(timePassed);
       initButtons();
    }

    private void updateMineLabel() {
        bombsLeft.setText("Bombs Left: " + board.numBombsLeft());
    }

    private void updateTimeLabel() {
        if (minutes > 0)
            timePassed.setText("    Elapsed Time: " + minutes + "m " + seconds + "s");
        else
            timePassed.setText("    Elapsed Time: " + seconds + "s");
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
        if (status != null) {
            timer.cancel();
            endGame(status);
        }
    }

    public void endGame(boolean win) {
        Object[] options = {"OK"};
        String timeString;
        if (minutes > 0)
            timeString = minutes + "m " + seconds + "s";
        else
            timeString = seconds + "s";

        if (win) {
            JOptionPane.showOptionDialog(this, "You marked all the bombs!\nTime: " + timeString + "\nPress OK to quit.",
                    "You Win", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            System.exit(9);
        }
        else {
            JOptionPane.showOptionDialog(this, "You exploded a bomb!\nTime: " + timeString + "\nPress OK to quit.",
                    "You Lose", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            System.exit(8);
        }
    }
}
