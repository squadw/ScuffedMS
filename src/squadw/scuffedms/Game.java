package squadw.scuffedms;

import squadw.scuffedms.game.Minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

    Minesweeper game = new Minesweeper();
    JPanel panel = new JPanel();

    public Game() {
        initFrame();
        initPanel();
        initScreen();
        setContentPane(panel);
        setVisible(true);
    }

    private void initFrame() {
        setSize(400, 500);
        setFocusable(true);
        setResizable(false);
        setFocusTraversalKeysEnabled(false);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try { setIconImage(ImageIO.read(getClass().getResource("/squadw/scuffedms/resources/images/flag.png"))); }
        catch(IOException e) { System.out.println(e); }
    }

    private void initPanel() {
        panel.setLayout(new GridBagLayout());
    }

    private void initScreen() {
        JButton p = new JButton("Play");

        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        p.setPreferredSize(new Dimension(150,65));
        panel.add(p);

        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                game.setVisible(true);
            }
        });
    }
}
