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
    private int numBombsLeft;
    private Tile[][] board;

    public Board(int size, int diff) {
        this.size = size;
        this.diff = diff;
        board = new Tile[this.size][this.size];
        initBoard();
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public int getTotalBombs() {
        return numBombs;
    }

    private void setNumBombs(int x, int y) {
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

    public int numBombsLeft() {
        int numMarked = 0;

        for (Tile[] b: board) {
            for (Tile t : b) {
                if (t.getTileState() == Tile.MARKED) {
                    numMarked++;
                }
            }
        }
        numBombsLeft = numBombs - numMarked;
        return numBombsLeft;
    }

    public void revealBoard(int x, int y) {
        if (x < 0 || x > size-1 || y < 0 || y > size-1) return;

        if (board[x][y].getNumBombs() == 0 && board[x][y].getTileState() != Tile.OPENED && !(board[x][y] instanceof Mine)) {
            board[x][y].setOpened();
            revealBoard(x+1, y);
            revealBoard(x-1, y);
            revealBoard(x, y-1);
            revealBoard(x, y+1);
            //openAround(x, y);
        }
        else return;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                openAround(i, j);
            }
        }
    }

    public void openAround(int x, int y) {
        if (board[x][y].getTileState() == Tile.OPENED && board[x][y].getNumBombs() == 0) {
            if (x > 0 && x < size-1 && y > 0 && y < size-1) {
                board[x - 1][y].setOpened();
                board[x + 1][y].setOpened();
                board[x][y - 1].setOpened();
                board[x][y + 1].setOpened();
                board[x - 1][y - 1].setOpened();
                board[x + 1][y + 1].setOpened();
                board[x - 1][y + 1].setOpened();
                board[x + 1][y - 1].setOpened();
            }
            if (x == 0 && y > 0 && y < size-1) {
                board[x + 1][y].setOpened();
                board[x][y - 1].setOpened();
                board[x][y + 1].setOpened();
                board[x + 1][y + 1].setOpened();
                board[x + 1][y - 1].setOpened();
            }
            if (x == size - 1 && y > 0 && y < size-1) {
                board[x - 1][y].setOpened();
                board[x][y - 1].setOpened();
                board[x][y + 1].setOpened();
                board[x - 1][y - 1].setOpened();
                board[x - 1][y + 1].setOpened();
            }
            if (y == 0 && x > 0 && x < size-1) {
                board[x - 1][y].setOpened();
                board[x + 1][y].setOpened();
                board[x][y + 1].setOpened();
                board[x + 1][y + 1].setOpened();
                board[x - 1][y + 1].setOpened();
            }
            if (y == size - 1 && x > 0 && x < size - 1) {
                board[x - 1][y].setOpened();
                board[x + 1][y].setOpened();
                board[x][y - 1].setOpened();
                board[x - 1][y - 1].setOpened();
                board[x + 1][y - 1].setOpened();
            }
        }
    }

    private void addNewBomb() {
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        if (board[x][y] instanceof Mine) {
            do {
                x = r.nextInt(size);
                y = r.nextInt(size);
            } while(board[x][y] instanceof Mine);
            board[x][y] = new Mine();
        }
        else {
            board[x][y] = new Mine();
        }
    }

    public Boolean checkForGameEnd() {
        int bombsFlagged = 0;
        int markedTiles = 0;
        boolean gameOver = false;

        for (Tile[] b : board) {
            for (Tile t : b) {
                if (t instanceof Mine && ((Mine) t).isExploded()) {
                    gameOver = true;
                    revealAllMines();
                }
                if (t instanceof Mine && t.getTileState() == Tile.MARKED) bombsFlagged++;
                if (!(t instanceof Mine) && t.getTileState() == Tile.MARKED) markedTiles++;
                t.setImage();
            }
        }

        if (bombsFlagged == numBombs && markedTiles == 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j].getTileState() != Tile.MARKED) board[i][j].setOpened();
                }
            }
            //Main.endGame(true);
            return true;
        }
        else if (gameOver) {
            //Main.endGame(false);
            return false;
        }
        return null;
    }
  
    private void revealAllMines() {
        for (Tile[] b: board)
            for (Tile t : b)
                if (t instanceof Mine) t.setOpened();
    }
  
    private void initBoard() {
        int n = (size * size) * diff / 20;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile();
            }
        }

        for (int i = 0; i < n; i++)
            addNewBomb();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setNumBombs(i, j);
                board[i][j].setCoords(i, j);
                if (board[i][j] instanceof Mine) {
                    numBombs++;
                }
            }
        }
    }
}
