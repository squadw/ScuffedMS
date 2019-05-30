package squadw.scuffedms;

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
                System.out.println("Invalid input. Please enter an integer for size and difficulty or just size.");
            }
        }
        else
            game = new Game();
    }

    public static void playAgain() {
        main(new String[]{});
    }
}
