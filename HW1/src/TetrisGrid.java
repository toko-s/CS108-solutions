//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
    boolean[][] grid;
    int row;
    int col;

    /**
     * Constructs a new instance with the given grid.
     * Does not make a copy.
     *
     * @param grid
     */
    public TetrisGrid(boolean[][] grid) {
        this.grid = grid;
        row = grid.length;
        col = grid[0].length;
    }

    /**
     * Does row-clearing on the grid (see handout).
     */
    public void clearRows() {
        boolean[][] tmp = new boolean[row][col];
        int filling = 0;
        int traversing = 0;
        while (traversing < col) {
            if (!isFull(traversing)) {
                fillCol(filling, traversing, tmp);
                filling++;
            }
            traversing++;
        }
        for (int i = filling; i < col; i++)
            for (int j = 0; j < row; j++)
                tmp[j][i] = false;
        this.grid = tmp;
    }

    private boolean isFull(int col) {
        for (int i = 0; i < this.row; i++)
            if (!grid[i][col])
                return false;
        return true;
    }

    private void fillCol(int col, int traversing, boolean[][] tmp) {
        for (int i = 0; i < row; i++)
            tmp[i][col] = this.grid[i][traversing];
    }

    /**
     * Returns the internal 2d grid array.
     *
     * @return 2d grid array
     */
    boolean[][] getGrid() {
        return grid;
    }
}
