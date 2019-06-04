package squadw.scuffedms;

import squadw.scuffedms.game.Minesweeper;
import squadw.scuffedms.game.util.Convert;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

    private Minesweeper game;

    public Game() {
        initFrame(); // Creates game board
        startScreen(); // Creates the starting screen
        setVisible(true); // Sets the frame visible so the player can see it
    }

    // Constructors for custom sizing of a board
    public Game(int s, int d) {
        game = new Minesweeper(s, d);
        game.setVisible(true);
    }

    public Game(int s) {
        this(s, 2);
    }

    // Creates window where the game will be shown
    private void initFrame() {

        // Configures the game's window properly
        setSize(400, 500);
        setFocusable(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Changes UI to Windows Default UI
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { System.out.println(e); }
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }
    }

    // Creates the starting screen window
    private void startScreen() {
        // Creates play button
        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();

//        int sc = Main.getFile().getScore();
//        int m = sc / 60;
//        int s = sc % 60;
//        String score;

        JButton p = new JButton("Play");
//        JLabel l = new JLabel();
//
//        score = "High Score: ";
//        if (m > 0)
//            score += m + "m " + s + "s";
//        else
//            score += s + "s";
//
//        l.setText(score);

        // Configuring panel
        panel.setLayout(new GridBagLayout());
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(5,0,5,0);

        setContentPane(panel);

        // Configuring and adding play button to panel
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        p.setPreferredSize(new Dimension(150,65));
        //panel.add(l, c);
        panel.add(p, c);

        // Adding listener to play button so it knows when to start the game
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.setVisible(false);
                sizeScreen();
            }
        });
    }

    // Creates size choice screen
    private void sizeScreen() {

        // Creates Panel for size choice screen
        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();

        // Buttons for each size
        JButton s = new JButton("Small");
        JButton m = new JButton("Medium");
        JButton l = new JButton("Large");

        panel.setLayout(new GridBagLayout());

        // Configuring settings for the buttons
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(5,0,5,0);

        // Sets size of the buttons
        s.setPreferredSize(new Dimension(150,50));
        m.setPreferredSize(new Dimension(150,50));
        l.setPreferredSize(new Dimension(150,50));

        // Adds buttons to the panel
        panel.add(s, c);
        panel.add(m, c);
        panel.add(l, c);

        setContentPane(panel);

        // Adds listeners to the buttons
        s.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.setVisible(false);
                difficultyScreen(9);
            }
        });
        m.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.setVisible(false);
                difficultyScreen(16);
            }
        });
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.setVisible(false);
                difficultyScreen(25);
            }
        });
    }

    // Creates difficulty selection screen
    private void difficultyScreen(int size) {

        // Creates panel and buttons for each difficulty
        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        JButton e = new JButton("Easy");
        JButton n = new JButton("Normal");
        JButton h = new JButton("Hard");

        panel.setLayout(new GridBagLayout());

        // Configures settings for buttons
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(5,0,5,0);

        // Sets sizes of buttons
        e.setPreferredSize(new Dimension(150,50));
        n.setPreferredSize(new Dimension(150,50));
        h.setPreferredSize(new Dimension(150,50));

        // Add Buttons to the panel
        panel.add(e, c);
        panel.add(n, c);
        panel.add(h, c);

        setContentPane(panel);

        // Adds listeners to the buttons
        e.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                game = new Minesweeper(size, 2);
            }
        });
        n.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                game = new Minesweeper(size, 3);
            }
        });
        h.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                game = new Minesweeper(size, 4);
            }
        });
    }
}
