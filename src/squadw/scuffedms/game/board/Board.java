package squadw.scuffedms.game.board;

import squadw.scuffedms.Main;
import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board {
    private int size;
    private int diff;
    private int numBombs;
    private int firstClick;
    private Tile[][] board;

    public Board(int size, int diff) {
        this.size = size;
        this.diff = diff;
        board = new Tile[this.size][this.size];
        initBoard();
        tileMouseListener();
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getBoard() {
        return board;
    }

    private void checkForBombs(int x, int y) {
        int mines = 0;
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
                if (board[k][l] instanceof Mine) mines++;

        board[x][y].setNumBombs(mines);
    }

    private void tileMouseListener() {
        for (Tile[] b: board)
            for (Tile t : b) {
                t.getButton().addMouseListener(new MouseAdapter() {
                    boolean pressed;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = true;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (pressed) {
                            revealBoard(t.getCoords()[1], t.getCoords()[0]);
                            if (t.getTileState() == Tile.MARKED && SwingUtilities.isRightMouseButton(e)) t.setClosed();
                            else if (SwingUtilities.isRightMouseButton(e) && t.getTileState() != Tile.OPENED) t.setMarked();
                            else t.setOpened();
                            checkForGameEnd();
                            firstClick++;
                            System.out.println(t.getCoords()[1] + " " + t.getCoords()[0]);
                            //if (firstClick == 1) {

                            //}
                            pressed = false;
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        pressed = false;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        pressed = true;
                    }
                });
            }
    }

    private void revealBoard(int x, int y) {
        if (x < 0 || x > size-1 || y < 0 || y > size-1) return;

        if (board[x][y].getNumBombs() <= 0 && board[x][y].getTileState() != Tile.OPENED && !(board[x][y] instanceof Mine)) {
            board[x][y].setOpened();
            revealBoard(x+1, y);
            revealBoard(x-1, y);
            revealBoard(x, y-1);
            revealBoard(x, y+1);
        }
        else return;
    }

    private void checkForGameEnd() {
        int bombsFlagged = 0;
        int markedTiles = 0;
        boolean gameOver = false;

        for (Tile[] b : board) {
            for (Tile t : b) {
                if (t instanceof Mine && ((Mine) t).isExploded()) gameOver = true;
                if (t instanceof Mine && t.getTileState() == Tile.MARKED) bombsFlagged++;
                if (!(t instanceof Mine) && t.getTileState() == Tile.MARKED) markedTiles++;
                t.setImage();
            }
        }

        if (bombsFlagged == numBombs && markedTiles == 0) Main.endGame(true);
        else if (gameOver) Main.endGame(false);
    }

    private void initBoard() {
        Random r = new Random();
        int n = (size * size) * (diff * 2 - 1) / 8;
        int x;
        int y;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile();
            }
        }

        for (int i = 0; i < n; i++) {
            do {
                x = r.nextInt(size);
                y = r.nextInt(size);
                board[x][y] = new Mine();
            } while (!(board[x][y] instanceof Mine));
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                checkForBombs(i, j);
                board[i][j].setCoords(i, j);
                if (board[i][j] instanceof Mine) {
                    numBombs++;
                }
            }
        }
    }
}
