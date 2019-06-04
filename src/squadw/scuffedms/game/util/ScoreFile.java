package squadw.scuffedms.game.util;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static squadw.scuffedms.game.util.Convert.*;

public class ScoreFile extends File {
    private final int INDEX = 0;

    public ScoreFile(String file) {
        super(file);
        fillEmptyFile();
        checkCorruptFile();
    }

    private void initFile() {
        writeFile(Arrays.asList("0"));
    }

    private void fillEmptyFile() {
        List<String> lines = readFile();
        if (lines.size() < 1) {
            initFile();
        }
    }

    private void checkCorruptFile() {
        List<String> lines = readFile();
        for (int i = 0; i < lines.size(); i++) {
            try { parseInt(lines.get(i)); }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e + "\nReset corrupt scores file");
                initFile();
            }
        }
    }

    public void setScore(int time) {
        addScore(time, INDEX);
    }

    public int getScore() {
        return getScore(INDEX);
    }

    private int getScore(int index) {
        List<String> lines = readFile();
        return parseInt(lines.get(index));
    }

    private void addScore(int input, int index) {
        List<String> lines = new ArrayList<>();
        lines.set(index, parseInt(input));
        writeFile(lines);
    }
}
