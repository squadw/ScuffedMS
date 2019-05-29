package squadw.scuffedms;

import javax.swing.*;
import java.lang.Object;
import java.time.Duration;
import java.time.Instant;

public class Main {

    private static Game game;
    private static Instant start;
    private static Instant end;
    private static Duration interval;

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                int s = Integer.parseInt(args[0]);
                int d = Integer.parseInt(args[1]);
                game = new Game(s, d);
                start = Instant.now();
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer for size and diffculty or just size.");
            }
        }
        else if (args.length == 1) {
            try {
                int s = Integer.parseInt(args[0]);
                game = new Game(s);
                start = Instant.now();
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer for size and difficulty or just size.");
            }
        }
        else
            start = Instant.now();
            game = new Game();
        }


    public static void endGame(boolean win) {
        if (win) {
            end = Instant.now();
            interval = Duration.between(start, end);
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(game, "You marked all the bombs!\nTime in seconds: " + interval.getSeconds() + "\nPress OK to quit.",
                    "You Win", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            System.exit(9);
        }
        else {
            end = Instant.now();
            interval = Duration.between(start, end);
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(game, "You exploded a bomb!\nTime in seconds: " + interval.getSeconds() + "\nPress OK to quit.",
                    "You Lose", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            System.exit(8);
        }
    }
}
