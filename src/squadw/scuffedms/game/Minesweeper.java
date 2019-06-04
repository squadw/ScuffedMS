package squadw.scuffedms.game;

import squadw.scuffedms.Main;
import squadw.scuffedms.game.board.Board;
import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;
import squadw.scuffedms.game.util.ScoreFile;

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
    private int minutes;
    private int seconds;
    private int overallTime;

    // Constructor for the game
    public Minesweeper(int s, int d) {

        // Creates board and sets the width and height to be used later for window configuring
        board = new Board(s,d);
        w = board.getSize() * 40 + 20;
        h = board.getSize() * 40 + 70;

        // Creates the games User Interface
        initUI();
        // Starts/sets up a timer for the scoring
        setupTimer();
        // Sets the window to be visible to the user
        setVisible(true);
        // Prints the mines location for testing. Will be removed in releases.
        printBoard();
    }

    // Method to restart the game
    private void restartGame() {
        // Stops and resets timer and removes things from the window.
        getContentPane().removeAll();
        timer.cancel();
        overallTime = 0;

        // Resets sizes and settings for the board
        int s = board.getSize();
        int d = board.getDiff();
        numClicks = 0;
        // Creates a new board
        board = new Board(s,d);
        initUI();
        revalidate();
        setupTimer();
    }

    // Refreshes the board so no UI bugs occur
    private void refreshGame() {
        getContentPane().removeAll();
        initUI();
        revalidate();
    }

    // Method to start timer
    private void setupTimer() {
        // Timer task is created for the timer to run every second to count up
        task = new TimerTask() {
            @Override
            public void run() {
                overallTime++;
                minutes = overallTime / 60;
                seconds = overallTime % 60;
                updateTimeLabel();
            }
        };
        // Creates the actual timer
        timer = new Timer();
    }

    // Prints the mine locations for testing
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

    // Adds tiles to the board (Used in initUI)
    private void initButtons() {
        tileMouseListener();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                boardPanel.add(board.getBoard()[i][j].getButton());
            }
        }
    }

    // Main method for creating the game's User Interface
    private void initUI() {

        // Variables for the buttons and panels for where the tiles will be placed
        JButton playAgain = new JButton("Restart");
        splitPane = new JSplitPane();
        boardPanel = new JPanel();
        textPanel = new JPanel();

        // Labels used to display the amount of bombs left, and the amount of time currently elapsed throughout the game
        bombsLeft = new JLabel("Bombs Left: " + board.numBombsLeft());
        timePassed = new JLabel("  Elapsed Time: Waiting  ");

        // Creates frame for the game
        initFrame();
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(splitPane);

        // Configures settings for a panel used in the window
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(h - 70);
        splitPane.setTopComponent(boardPanel);
        splitPane.setBottomComponent(textPanel);

        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        boardPanel.setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));

        // Listener for the restart button, so that it restarts the game when clicked.
        playAgain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                restartGame();
            }
        });

        // Adds the labels and buttons to the window.
        textPanel.add(bombsLeft);
        textPanel.add(timePassed);
        textPanel.add(playAgain);
        // Adds the tiles to the window.
        initButtons();
    }

    // Update the count of bombs left
    private void updateMineLabel() {
        bombsLeft.setText("Bombs Left: " + board.numBombsLeft());
    }

    // Updates the timer on screen
    private void updateTimeLabel() {
        if (minutes > 0)
            timePassed.setText("  Elapsed Time: " + minutes + "m " + seconds + "s  ");
        else
            timePassed.setText("  Elapsed Time: " + seconds + "s  ");
    }

    // Creates frame for the game
    private void initFrame() {

        // Configures settings for the window to display properly
        setSize(w, h);
        setPreferredSize(new Dimension(w, h));
        setFocusable(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Sets the window's icon to the image of a flag
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }
    }

    // Listener for the tiles so that they have clickable functionality
    private void tileMouseListener() {
        for (Tile[] b: board.getBoard())
            for (Tile t : b) {
                MouseAdapter m = new MouseAdapter() {
                    boolean pressed;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        // Adds small tactile look to the tiles so when you hold down they appear differently than when clicked or left alone
                        pressed = true;
                        if (t.getTileState() == Tile.CLOSED && !SwingUtilities.isRightMouseButton(e))
                            t.setImage(Tile.OPENED);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (pressed) {
                            numClicks++;
                            if (numClicks == 1) {
                                // Starts timer on the first click of the game
                                timer.scheduleAtFixedRate(task, 0, 1000);
                                // If the first tile clicked is a mine this is ran to move the mine so the user doesn't lose on their first click
                                if (board.getBoard()[t.getX()][t.getY()] instanceof Mine) {
                                    board.firstBomb(board.getBoard()[t.getX()][t.getY()]);
                                    refreshGame();
                                    pressed = false;
                                    return;
                                }
                            }

                            // If left click runs revealBoard to try and clear an open space for the player
                            if (!SwingUtilities.isRightMouseButton(e))
                                board.revealBoard(t.getX(), t.getY());
                            // If tile is marked and you right click it unmarks it
                            if (t.getTileState() == Tile.MARKED && SwingUtilities.isRightMouseButton(e)) {
                                t.setClosed();
                                updateMineLabel();
                            }
                            // If tile isn't marked and you right click it, it is marked
                            else if (SwingUtilities.isRightMouseButton(e) && t.getTileState() != Tile.OPENED) {
                                t.setMarked();
                                updateMineLabel();
                            }
                            // If you click a tile that has the same amount of flags as mines surrounding it open the unmarked tiles. This can make the player lose.
                            else if (countFlags(t.getX(), t.getY()) == t.getNumBombs() && t.getNumBombs() > 0 && t.getTileState() != Tile.MARKED) {
                                board.openUnflagged(t.getX(), t.getY());
                            }
                            // If tile is clicked and isn't marked its set to open
                            else if (t.getTileState() != Tile.MARKED) {
                                t.setOpened();
                            }
                            // Tries to end the game
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
                };

                if (t.getButton().getMouseListeners().length == 1)
                    t.getButton().addMouseListener(m);
            }
    }

    // Counts the flags surrounding a tile. (Used in tileMouseListener)
    public int countFlags(int x, int y) {
        int size = board.getSize();
        int flags = 0;
        int xMax = x+1;
        int yMax = y+1;
        int xMin = x-1;
        int yMin = y-1;

        if (y == size-1) yMax = size-1;
        if (x == size-1) xMax = size-1;
        if (x == 0) xMin = 0;
        if (y == 0) yMin = 0;

        for (int k = xMin; k <= xMax; k++)
            for (int l = yMin; l <= yMax; l++)
                if (board.getBoard()[k][l].getTileState() == Tile.MARKED) flags++;

        return flags;
    }

    // Tries to end the game.
    private void tryToEnd(Boolean status) {
        // If their is a win or loss the timer is stopped and endGame is run with the status of the game
        if (status != null) {
            timer.cancel();
            endGame(status);
        }
    }

    // Method that stops the game
    private void endGame(boolean win) {
        Object[] options = {"Quit", "Play Again"};
        String timeString;
        int p;
        // Math for finding the length of the game
        if (minutes > 0)
            timeString = minutes + "m " + seconds + "s";
        else
            timeString = seconds + "s";

        // If the game is a win, dialog box is shown telling the player they won and displaying how long it took. Option to play again is allowed
        if (win) {
            /*if (overallTime > Main.getFile().getScore()) {
                Main.getFile().setScore(overallTime);
            }*/
            p = JOptionPane.showOptionDialog(this, "You marked all the bombs!\nTime: " + timeString,
                    "You Win", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
        }
        // If the game is a loss, dialog box is shown telling the player they lost and displays how long it took. Option to play again is allowed
        else {
            p = JOptionPane.showOptionDialog(this, "You exploded a bomb!\nTime: " + timeString,
                    "You Lose", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
        }
        // If the player wants to play again the game is restarted
        if (p == 1) {
            setVisible(false);
            Main.playAgain();
        }
        // If the player doesn't want to play again the program is closed
        else {
            System.exit(0);
        }
    }
}
