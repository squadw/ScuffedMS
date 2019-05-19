package squadw.scuffedms;

import javax.swing.*;

public class Main {

    private static Game game;

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                int s = Integer.parseInt(args[0]);
                int d = Integer.parseInt(args[1]);
                game = new Game(s, d);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer for size and diffculty or just size.");
            }
        }
        else if (args.length == 1) {
            try {
                int s = Integer.parseInt(args[0]);
                game = new Game(s);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer for size and diffculty or just size.");
            }
        }
        else {
            game = new Game();
        }
    }

    public static void endGame(boolean win) {
        if (win) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(game, "You marked all the bombs!\nPress OK to quit.",
                    "You Win", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            System.exit(9);
        }
        else {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(game, "You exploded a bomb!\nPress OK to quit.",
                    "You Lose", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            System.exit(8);
        }
    }
}
