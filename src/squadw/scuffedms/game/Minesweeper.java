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
    private int numClicks = 0;

    private Timer timer;
    private TimerTask task;
    private long minutes;
    private long seconds;
    private long overallTime;

    public Minesweeper(int s, int d) {
        board = new Board(s,d);
        w = board.getSize() * 40 + 20;
        h = board.getSize() * 40 + 70;
        initUI();
        setupTimer();
        setVisible(true);
        printBoard();
    }

    private void setupTimer() {
        task = new TimerTask() {
            @Override
            public void run() {
                overallTime++;
                minutes = overallTime / 60;
                seconds = overallTime % 60;
                updateTimeLabel();
            }
        };
        timer = new Timer();
    }

    private void printBoard() {
        Tile[][] temp = board.getBoard();

        for (int i = 0; i < board.getSize(); i++) {
            System.out.println();
            for (int j = 0; j < board.getSize(); j++) {
                System.out.print(temp[i][j] + " ");
            }
        }
        System.out.println();
    }

    private void initButtons() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                tileMouseListener(i, j);
                boardPanel.add(board.getBoard()[i][j].getButton());
            }
        }
    }

    private void initUI() {
       splitPane = new JSplitPane();
       boardPanel = new JPanel();
       textPanel = new JPanel();

       bombsLeft = new JLabel("Bombs Left: " + board.numBombsLeft());
       timePassed = new JLabel("    Elapsed Time: Waiting for first move");

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

    private void tileMouseListener(int x, int y) {
        Tile t = board.getBoard()[x][y];
        t.getButton().addMouseListener(new MouseAdapter() {
            boolean pressed;
            boolean moved;

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                if (t.getTileState() == Tile.CLOSED && !SwingUtilities.isRightMouseButton(e))
                    t.setImage(Tile.OPENED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed) {
                    numClicks++;
                    if (numClicks == 1) {
                        timer.scheduleAtFixedRate(task, 0, 1000);
                        if (t instanceof Mine) {
                            int xy[] = board.moveBomb(t);
                            moved = true;
                            initButtons();
                            board.setNumBombs(xy[0], xy[1]);
                            board.getBoard()[xy[0]][xy[1]].setOpened();
                            printBoard();
                            return;
                        }
                    }

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
                    else if (t.getTileState() != Tile.MARKED && !moved) t.setOpened();
                    if (t.getNumBombs() == 0)
                        board.openAround(t.getCoords()[0], t.getCoords()[1]);
                    tryToEnd(board.checkForGameEnd());

                    pressed = false;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pressed = false;
                if (t.getTileState() == Tile.CLOSED)
                    t.setImage(Tile.CLOSED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                        pressed = true;
                    }
        });
    }

    private void tryToEnd(Boolean status) {
        if (status != null) {
            timer.cancel();
            endGame(status);
        }
    }

    private void endGame(boolean win) {
        Object[] options = {"Quit", "Play Again"};
        String timeString;
        int p;
        if (minutes > 0)
            timeString = minutes + "m " + seconds + "s";
        else
            timeString = seconds + "s";

        if (win) {
            p = JOptionPane.showOptionDialog(this, "You marked all the bombs!\nTime: " + timeString,
                    "You Win", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
        }
        else {
            p = JOptionPane.showOptionDialog(this, "You exploded a bomb!\nTime: " + timeString,
                    "You Lose", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
        }
        if (p == 1) {
            setVisible(false);
            Main.playAgain();
        }
        else {
            System.exit(0);
        }
    }
}
