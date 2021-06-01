import java.util.*;
import java.util.function.Consumer;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
    // Provided grid data for main/testing
    // The instance variable strategy is up to you.

    // Provided easy 1 6 grid
    // (can paste this text into the GUI too)
    public static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");


    // Provided medium 5 3 grid
    public static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");


    public static final int SIZE = 9;  // size of the whole 9x9 puzzle
    public static final int PART = 3;  // size of each 3x3 part
    public static final int MAX_SOLUTIONS = 100;
    private static int[][] grid;
    private static int foundSolutions;
    private static long timeDiff;
    private String firstSolution = "";


    // Provided various static utility methods to
    // convert data formats to int[][] grid.

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * The "..." is a Java 5 feature that essentially
     * makes "rows" a String[] array.
     * (provided utility)
     *
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String... rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            result[row] = stringToInts(rows[row]);
        }
        return result;
    }


    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     *
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != SIZE * SIZE) {
            throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
        }

        int[][] result = new int[SIZE][SIZE];
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }


    /**
     * Given a string containing digits, like "1 23 4",
     * returns an int[] of those digits {1 2 3 4}.
     * (provided utility)
     *
     * @param string string containing ints
     * @return array of ints
     */
    public static int[] stringToInts(String string) {
        int[] a = new int[string.length()];
        int found = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                a[found] = Integer.parseInt(string.substring(i, i + 1));
                found++;
            }
        }
        int[] result = new int[found];
        System.arraycopy(a, 0, result, 0, found);
        return result;
    }


    // Provided -- the deliverable main().
    // You can edit to do easier cases, but turn in
    // solving hardGrid.
    public static void main(String[] args) {
        Sudoku sudoku;
        sudoku = new Sudoku(hardGrid);

        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }


    private static class spot implements Comparable {
        private final int row;
        private final int col;
        private final int inversPriority;

        public spot(int row, int col) {
            this.row = row;
            this.col = col;
            inversPriority = calcPriority();
        }

        private int calcPriority() {
            int res = 0;
            for (int value = 1; value <= SIZE; value++)
                if (checkValue(value))
                    res++;
            return res;
        }

        public boolean checkValue(int value) {
            //check row and col
            for (int i = 0; i < SIZE; i++) {
                if (grid[row][i] == value)
                    return false;
                if (grid[i][col] == value)
                    return false;
            }
            //check sub grid
            int x = row - row % PART;
            int y = col - col % PART;
            for (int i = x; i < x + PART; i++)
                for (int j = y; j < y + PART; j++)
                    if (grid[i][j] == value)
                        return false;

            return true;
        }

        public static List<spot> getSpotArray() {
            List<spot> res = new ArrayList<>();
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++)
                    if (grid[i][j] == 0)
                        res.add(new spot(i, j));
            Collections.sort(res);
            return res;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public int getPriority() {
            return inversPriority;
        }

        public void set(int n) {
            grid[row][col] = n;
        }

        @Override
        public String toString() {
            return "(%d , %d)".formatted(row, col);
        }

        @Override
        public int compareTo(Object o) {
            return inversPriority - ((spot) o).getPriority();
        }

    }


    /**
     * Sets up based on the given ints.
     */
    public Sudoku(int[][] ints) {
        assert(ints.length != SIZE);
        assert(ints[0].length != SIZE);
        grid = ints;
        foundSolutions = 0;
        firstSolution = "";
    }

    public Sudoku(String text) {
        this(textToGrid(text));
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                res = res + grid[i][j] + " ";
            }
            res += "\n";
        }
        return res.substring(0,res.length() - 2);
    }


    /**
     * Solves the puzzle, invoking the underlying recursive search.
     */
    public int solve() {
        List<spot> spots = spot.getSpotArray();
        timeDiff = System.currentTimeMillis();
        recSolve(0, spots);
        timeDiff = System.currentTimeMillis() - timeDiff;
        return foundSolutions; // YOUR CODE HERE
    }

    private void recSolve(int curr, List<spot> spots) {
        if (curr == spots.size()) {
        	if(foundSolutions == 0)
        		firstSolution = this.toString();
            foundSolutions++;
            return;
        }

        spot p = spots.get(curr);
        for (int val = 1; val <= SIZE; val++) {
            if (p.checkValue(val)) {
                p.set(val);
                recSolve(curr + 1, spots);
                p.set(0);
                if (foundSolutions == MAX_SOLUTIONS)
                    return;
            }
        }
    }

    public String getSolutionText() {
        return firstSolution;
    }

    public long getElapsed() {
        return timeDiff;
    }

}
