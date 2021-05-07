/* Board.java */


import java.util.Arrays;

/**
 * CS108 Tetris Board.
 * Represents a Tetris board -- essentially a 2-d grid
 * of booleans. Supports tetris pieces and row clearing.
 * Has an "undo" feature that allows clients to add and remove pieces efficiently.
 * Does not do any drawing or have any idea of pixels. Instead,
 * just represents the abstract 2-d board.
 */
public class Board {
    // Some ivars are stubbed out for you:
    private final int width;
    private final int height;
    private boolean[][] grid;
    private final boolean DEBUG = true;
    private boolean committed;
    private int[] widths;
    private int[] heights;
    private int maxHeight;
    private boolean[][] xGrid;
    private int[] xWidths;
    private int[] xHeights;
    private int xMaxHeight;

    // Here a few trivial methods are provided:

    /**
     * Creates an empty board of the given width and height
     * measured in blocks.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[width][height];
        committed = true;
        widths = new int[height];
        heights = new int[width];
        xGrid = new boolean[width][height];
        xWidths = new int[height];
        xHeights = new int[width];
        maxHeight = 0;
        xMaxHeight = 0;
    }


    /**
     * Returns the width of the board in blocks.
     */
    public int getWidth() {
        return width;
    }


    /**
     * Returns the height of the board in blocks.
     */
    public int getHeight() {
        return height;
    }


    /**
     * Returns the max column height present in the board.
     * For an empty board this is 0.
     */
    public int getMaxHeight() {
        return maxHeight;
    }


    /**
     * Checks the board for internal consistency -- used
     * for debugging.
     */
    public void sanityCheck() {
        if (DEBUG) {
            int max = 0;
            for (int i = 0; i < width; i++) {
                int currHeight = 0;
                for (int j = 0; j < height; j++)
                    if (grid[i][j]) {
                        if(j + 1 > currHeight)
                            currHeight = j + 1;
                        if(j + 1 > max)
                            max = j + 1;
                    }

                if (currHeight != heights[i])
                    throw new RuntimeException("Heights does not match!\n Height is: " + currHeight + " of Row " + i + ". expected Height is: " + heights[i] + "\n" + this);
            }
            if (max != maxHeight)
                throw new RuntimeException("Max Height does not match!");

            for (int j = 0; j < height; j++) {
                int currWidth = 0;
                for (int i = 0; i < width; i++)
                    if (grid[i][j])
                        currWidth++;
                if (currWidth != widths[j])
                    throw new RuntimeException("Widths does not match!");
            }
        }
    }

    /**
     * Given a piece and an x, returns the y
     * value where the piece would come to rest
     * if it were dropped straight down at that x.
     *
     * <p>
     * Implementation: use the skirt and the col heights
     * to compute this fast -- O(skirt length).
     */
    public int dropHeight(Piece piece, int x) {
        int y = 0;
        int[] skirt = piece.getSkirt();
        for (int i = x; i < piece.getWidth() + x; i++) {
            int currY = heights[i] - skirt[i - x];
            if (y < currY)
                y = currY;
        }
        return y;
    }


    /**
     * Returns the height of the given column --
     * i.e. the y value of the highest block + 1.
     * The height is 0 if the column contains no blocks.
     */
    public int getColumnHeight(int x) {
        return heights[x];
    }


    /**
     * Returns the number of filled blocks in
     * the given row.
     */
    public int getRowWidth(int y) {
        return widths[y];
    }


    /**
     * Returns true if the given block is filled in the board.
     * Blocks outside of the valid width/height area
     * always return true.
     */
    public boolean getGrid(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return true;
        return grid[x][y];
    }


    public static final int PLACE_OK = 0;
    public static final int PLACE_ROW_FILLED = 1;
    public static final int PLACE_OUT_BOUNDS = 2;
    public static final int PLACE_BAD = 3;

    /**
     * Attempts to add the body of a piece to the board.
     * Copies the piece blocks into the board grid.
     * Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
     * for a regular placement that causes at least one row to be filled.
     *
     * <p>Error cases:
     * A placement may fail in two ways. First, if part of the piece may falls out
     * of bounds of the board, PLACE_OUT_BOUNDS is returned.
     * Or the placement may collide with existing blocks in the grid
     * in which case PLACE_BAD is returned.
     * In both error cases, the board may be left in an invalid
     * state. The client can use undo(), to recover the valid, pre-place state.
     */
    public int place(Piece piece, int x, int y) {
        // flag !committed problem
        if (!committed) throw new RuntimeException("place commit problem");
        committed = false;
        int result = PLACE_OK;
        for (TPoint p : piece.getBody()) {
            int currX = x + p.x;
            int currY = y + p.y;
            if (currX < 0 || currY < 0 || currX >= width || currY >= height)
                return PLACE_OUT_BOUNDS;
            else if (grid[currX][currY])
                return PLACE_BAD;
            else {
                grid[currX][currY] = true;
                widths[currY]++;
                if (currY + 1 > heights[currX])
                    heights[currX] = currY + 1;
                if (currY + 1 > maxHeight)
                    maxHeight = currY + 1;
                if (widths[currY] == width && result == PLACE_OK)
                    result = PLACE_ROW_FILLED;
            }
        }

        sanityCheck();
        return result;
    }


    /**
     * Deletes rows that are filled all the way across, moving
     * things above down. Returns the number of rows cleared.
     */
    public int clearRows() {
        committed = false;
        int rowsCleared = 0;
        int To = 0;
        int From = 0;
        while (From < height) {
            if (widths[From] != width) {
                moveRow(From, To);
                To++;
            } else
                rowsCleared++;
            From++;
        }

        maxHeight -= rowsCleared;

        for (int i = To; i < height; i++) {
            widths[i] = 0;
            for (int j = 0; j < width; j++)
                grid[j][i] = false;
        }
        renewHeights();
        sanityCheck();
        return rowsCleared;
    }

    private void renewHeights() {
        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= -1; j--) {
                heights[i] = j + 1;
                if (j == -1 || grid[i][j])
                    break;
            }
            if (heights[i] > maxHeight)
                maxHeight = heights[i];
        }
    }

    private void moveRow(int from, int to) {
        if (from == to)
            return;
        widths[to] = widths[from];
        for (int i = 0; i < width; i++)
            grid[i][to] = grid[i][from];
    }


    /**
     * Reverts the board to its state before up to one place
     * and one clearRows();
     * If the conditions for undo() are not met, such as
     * calling undo() twice in a row, then the second undo() does nothing.
     * See the overview docs.
     */
    public void undo() {
        if (!committed)
            committed = true;
        int[] tmpWidth = widths;
        widths = xWidths;
        xWidths = tmpWidth;
        int[] tmpHeight = heights;
        heights = xHeights;
        xHeights = tmpHeight;
        boolean[][] temp = grid;
        grid = xGrid;
        xGrid = temp;
        int tmp = maxHeight;
        maxHeight = xMaxHeight;
        xMaxHeight = tmp;
        backup();
        sanityCheck();
    }

    private void backup() {
        System.arraycopy(widths, 0, xWidths, 0, height);
        System.arraycopy(heights, 0, xHeights, 0, width);
        for (int i = 0; i < width; i++) {
            System.arraycopy(grid[i], 0, xGrid[i], 0, height);
        }
        xMaxHeight = maxHeight;
    }

    /**
     * Puts the board in the committed state.
     */
    public void commit() {
        committed = true;
        backup();
    }


    /*
     Renders the board state as a big String, suitable for printing.
     This is the sort of print-obj-state utility that can help see complex
     state change over time.
     (provided debugging utility)
     */
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int y = height - 1; y >= 0; y--) {
            buff.append('|');
            for (int x = 0; x < width; x++) {
                if (getGrid(x, y)) buff.append('+');
                else buff.append(' ');
            }
            buff.append("|\n");
        }
        buff.append("-".repeat(width + 2));
        return (buff.toString());
    }
}


