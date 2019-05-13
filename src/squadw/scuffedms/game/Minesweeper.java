package squadw.scuffedms.game;

import javax.swing.*;

public class Minesweeper extends JFrame {

    public Minesweeper() {
        initFrame();
    }

    private void initFrame() {

        JButton play = new JButton("Play");

        add(play);

        setSize(800, 800);
        setTitle("Scuffed Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

}
