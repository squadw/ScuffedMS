package squadw.scuffedms.game.board;

import squadw.scuffedms.game.tile.Mine;
import squadw.scuffedms.game.tile.Tile;

import java.util.*;

public class Board {
    private int size;
    private int diff;
    private int numBombs;
    private int numBombsLeft;
    private Tile[][] board;

    // Constructor for board.
    public Board(int size, int diff) {
        this.size = size;
        this.diff = diff;
        board = new Tile[this.size][this.size];
        initBoard();
    }

    public int getSize() {
        return size;
    }

    public int getDiff() {
        return diff;
    }

    public Tile[][] getBoard() {
        return board;
    }

    // Sets each tile's number to the number of bombs surrounding it
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

//     Counts number of bombs left.
//     (Actually counts amount of flags placed which should equal the number of bombs if the game is solved correctly.,
//     Can be negative if player plays incorrectly and flags tiles that aren't bombs)
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

    // Method to clear an open space when a tile that is blank is opened.
    public void revealBoard(int x, int y) {
        // Makes sure the tile's position will work and won't break the program.
        if (x < 0 || x >= size || y < 0 || y >= size) return;
        if (board[x][y].getNumBombs() != 0 || board[x][y].getTileState() == Tile.OPENED || board[x][y] instanceof Mine) return;

        // Opens all blank tiles within a space surrounded by bombs.
        Queue queue = new ArrayDeque();
        queue.add(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] q = (int[])queue.poll();
            x = q[0];
            y = q[1];
            if (x-1 >= 0)
                if (board[x-1][y].getNumBombs() == 0 && board[x-1][y].getTileState() != Tile.OPENED && !(board[x-1][y] instanceof Mine)) {
                    board[x-1][y].setOpened();
                    queue.add(new int[]{x-1, y});
                }
            if (x+1 < size)
                if (board[x+1][y].getNumBombs() == 0 && board[x+1][y].getTileState() != Tile.OPENED && !(board[x+1][y] instanceof Mine)) {
                    board[x+1][y].setOpened();
                    queue.add(new int[]{x+1, y});
                }
            if (y+1 < size)
                if (board[x][y+1].getNumBombs() == 0 && board[x][y+1].getTileState() != Tile.OPENED && !(board[x][y+1] instanceof Mine)) {
                    board[x][y+1].setOpened();
                    queue.add(new int[]{x, y+1});
                }
            if (y-1 >= 0)
                if (board[x][y-1].getNumBombs() == 0 && board[x][y-1].getTileState() != Tile.OPENED && !(board[x][y-1] instanceof Mine)) {
                    board[x][y-1].setOpened();
                    queue.add(new int[]{x, y-1});
                }
        }

        // Opens tiles on the perimeter of the opened space.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                openAround(i, j);
            }
        }
    }

    // Opens tiles surrounding a tile when clicked if the number of surrounding bombs is 0.
    public void openAround(int x, int y) {
        int xMin = (x-1 < 0) ? x : x-1;
        int yMin = (y-1 < 0) ? y : y-1;
        int xMax = (x+1 >= size) ? x : x+1;
        int yMax = (y+1 >= size) ? y : y+1;

        // Makes sure that the tile is opened and has no surrounding bombs
        if (board[x][y].getTileState() == Tile.OPENED && board[x][y].getNumBombs() == 0) {
            for (int i = xMin; i <= xMax; i++)
                for (int j = yMin; j <= yMax; j++) {
                    board[i][j].setOpened();
                    if (board[i][j].getNumBombs() == 0)
                        revealBoard(i, j);
                }
        }
    }

    // Method used to open all tiles around a tile that aren't flagged
    public void openUnflagged(int x, int y) {
        int xMin = (x-1 < 0) ? x : x-1;
        int yMin = (y-1 < 0) ? y : y-1;
        int xMax = (x+1 >= size) ? x : x+1;
        int yMax = (y+1 >= size) ? y : y+1;

        for (int k = xMin; k <= xMax; k++) {
            for (int l = yMin; l <= yMax; l++) {
                if (board[k][l].getNumBombs() == 0) revealBoard(k, l);
                if (board[k][l].getTileState() != Tile.MARKED) board[k][l].setOpened();
            }
        }

    }

    // Adds a bomb to a random spot on the board. Runs until a bomb is placed on a spot that a bomb isn't already on
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

    // Runs if the first click of a game is a bomb. Replaces the bomb with a tile and moves the bomb to the farthest left position in the top row as possible.
    public void firstBomb(Tile t) {
        // Gets the first tile clicked position
        final int tileX = t.getX();
        final int tileY = t.getY();

        // Finds the farthest left open space in the top row
        int pos = 0;
        while(board[0][pos] instanceof Mine) { pos++; }

        // Creates a temporary tile to replace the first clicked bomb with a tile and the position found earlier with a bomb.
        Tile temp = t;
        board[tileX][tileY] = board[0][pos];
        board[0][pos] = temp;

        // Resets tiles' surrounding bomb count and their coords so that the tiles still work correctly
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setNumBombs(i, j);
                board[i][j].setCoords(i, j);
            }
        }

        // Tries to run revealBoard and opens the tile that was first clicked
        revealBoard(board[tileX][tileY].getX(), board[tileX][tileY].getY());
        board[tileX][tileY].setOpened();
    }

    // Find the status of the game
    public Boolean checkForGameEnd() {
        int bombsFlagged = 0;
        int markedTiles = 0;
        boolean gameOver = false;

        // Runs through the tiles of the board
        for (Tile[] b : board) {
            for (Tile t : b) {
                if (t instanceof Mine && ((Mine) t).isExploded()) {
                    // Checks for a mine that is exploded to end the game
                    gameOver = true;
                    revealAllMines();
                }
                // Checks for Mines that are correctly marked and counts them
                if (t instanceof Mine && t.getTileState() == Tile.MARKED) bombsFlagged++;
                // Checks for Tiles that are incorrectly marked and counts them
                if (!(t instanceof Mine) && t.getTileState() == Tile.MARKED) markedTiles++;
                t.setImage();
            }
        }

        // If else sequence, returns true for win, false for loss, null for neither
        // If all Mines are marked and there are no extra flags on safe tiles, all the unmarked mines are opened.
        if (bombsFlagged == numBombs && markedTiles == 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j].getTileState() != Tile.MARKED) board[i][j].setOpened();
                }
            }
            return true;
        }
        else if (gameOver) {
            return false;
        }
        return null;
    }

    // Opens all the tiles on the board.
    private void revealAllMines() {
        for (Tile[] b: board)
            for (Tile t : b)
                if (t instanceof Mine) t.setOpened();
    }

    // Adds an amount of bombs based on the difficulty selected.
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
