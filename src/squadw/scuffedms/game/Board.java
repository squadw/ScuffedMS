package squadw.scuffedms.game;

import squadw.scuffedms.game.tile.Tile;

public class Board {
    private int size;
    private Tile[][] board;

    public Board() {
        size = 9;
        board = new Tile[size][size];
        initBoard();
    }

    public Board(int size) {
        this.size = size;
        board = new Tile[this.size][this.size];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile();
            }
        }
    }
}
