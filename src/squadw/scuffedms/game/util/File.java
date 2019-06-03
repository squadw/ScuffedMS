package squadw.scuffedms.game.util;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class File {
    private Path file;
    private String fileName;
    private final String dir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ScuffedMS\\";

    public File(String fileName) {
        this.fileName = fileName;
        this.file = Paths.get(fileName);
    }

    public Path getFile() {
        return file;
    }

    public List<String> readFile() {
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readAllLines(file);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return lines;
    }

    public void writeFile(List<String> lines) {
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
