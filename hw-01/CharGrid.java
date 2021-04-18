// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
    private char[][] grid;

    /**
     * Constructs a new CharGrid with the given grid.
     * Does not make a copy.
     *
     * @param grid
     */
    public CharGrid(char[][] grid) {
        this.grid = grid;
    }

    /**
     * Returns the area for the given char in the grid. (see handout).
     *
     * @param ch char to look for
     * @return area for given char
     */
    public int charArea(char ch) {
        int i_prev = 0;
        int j_prev = 0;
        boolean prev = false;
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ch) {
                    if (prev)
                        res = (i + 1 - i_prev) * (j + 1 - j_prev);
                    else {
                        res = 1;
                        prev = true;
                    }
                    i_prev = i;
                    j_prev = j;
                }
            }
        }
        return res;
    }

    /**
     * Returns the count of '+' figures in the grid (see handout).
     *
     * @return number of + in grid
     */
    public int countPlus() {
        int res = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                int a = getRow(i, j, grid[i][j]);
                int b = getCol(i, j, grid[i][j]);
                if (a == b)
                    res++;
            }
        }
        return res;
    }

    private int getCol(int x, int y, char ch) {
        int up = 0;
        int down = 0;
        int i = x - 1;
        while (i >= 0) {
            if (grid[i][y] != ch)
                break;
            up++;
            i--;
        }
        i = x + 1;
        while (i < grid.length) {
            if (grid[i][y] != ch)
                break;
            down++;
            i++;
        }
        if (up == 0 || down - up != 0)
            return -1;
        return up;
    }

    private int getRow(int x, int y, char ch) {
        int left = 0;
        int right = 0;
        int j = y - 1;
        while (j >= 0) {
            if (grid[x][j] != ch)
                break;
            left++;
            j--;
        }
        j = y + 1;
        while (j < grid[0].length) {
            if (grid[x][j] != ch)
                break;
            right++;
            j++;
        }

        if (left == 0 || left - right != 0)
            return -2;
        return left;
    }

}
