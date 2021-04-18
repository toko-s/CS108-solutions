
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {

    public void testCharArea1() {
        char[][] grid = new char[][]{
                {'a', 'y', ' '},
                {'x', 'a', 'z'},
        };


        CharGrid cg = new CharGrid(grid);

        assertEquals(4, cg.charArea('a'));
        assertEquals(1, cg.charArea('z'));
    }

    public void testCharArea2() {
        char[][] grid = new char[][]{
                {'c', 'a', ' '},
                {'b', ' ', 'b'},
                {' ', ' ', 'a'}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(6, cg.charArea('a'));
        assertEquals(3, cg.charArea('b'));
        assertEquals(1, cg.charArea('c'));
    }

    public void testCountPlus() {
        char[][] grid = new char[][]{
                {' '},
                {' '},
                {'p'},
                {' '},
                {' '},
                {'z'},
                {' '},
        };

        char[][] grid1 = new char[][]{
                {'c', 'a', ' ', '7'},
        };
        CharGrid cg = new CharGrid(grid);
        CharGrid cg1 = new CharGrid(grid1);
        assertEquals(0, cg1.countPlus());
        assertEquals(0, cg.countPlus());
    }

    public void testCountPlus2(){
        char[][] grid1 = new char[][]{
                {'c', 'a', ' '},
                {'a', 'a', 'a'},
                {' ', 'a', 'a'}
        };


        char[][] grid2 = new char[][]{
                {' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
                {'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
                {' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
                {' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
                {'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
                {' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
        };

        char[][] grid3 = new char[][]{
                {'+', '+', '+', '+', ' ', '+', '+', ' ', '+'},
                {' ', '+', '+', '+', ' ', '+', '+', '+', '+'},
                {'+', '+', '+', '+', ' ', ' ', '+', '+', '+'},
                {'+', '+', ' ', '+', ' ', '+', '+', '+', '+'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'+', '+', '+', '+', ' ', '+', '+', '+', '+'},
                {'+', '+', '+', ' ', ' ', '+', '+', '+', ' '},
                {'+', '+', '+', '+', ' ', '+', '+', '+', '+'},
                {'+', ' ', '+', '+', ' ', '+', ' ', '+', '+'},
        };

        CharGrid cg1 = new CharGrid(grid1);
        CharGrid cg2 = new CharGrid(grid2);
        CharGrid cg3 = new CharGrid(grid3);
        assertEquals(1, cg1.countPlus());
        assertEquals(2, cg2.countPlus());
        assertEquals(5, cg3.countPlus());
    }

}
