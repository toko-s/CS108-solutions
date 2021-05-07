import junit.framework.TestCase;


public class BoardTest extends TestCase {
    Board b;
    Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

    // This shows how to build things in setUp() to re-use
    // across tests.

    // In this case, setUp() makes shapes,
    // and also a 3X6 board, with pyr placed at the bottom,
    // ready to be used by tests.

    protected void setUp() throws Exception {
        b = new Board(3, 6);

        pyr1 = new Piece(Piece.PYRAMID_STR);
        pyr2 = pyr1.computeNextRotation();
        pyr3 = pyr2.computeNextRotation();
        pyr4 = pyr3.computeNextRotation();

        s = new Piece(Piece.S1_STR);
        sRotated = s.computeNextRotation();
        b.place(pyr1, 0, 0);
    }

    // Check the basic width/height/max after the one placement
    public void testSample1() {
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(2, b.getMaxHeight());
        assertEquals(3, b.getRowWidth(0));
        assertEquals(1, b.getRowWidth(1));
        assertEquals(0, b.getRowWidth(2));
        assertEquals(3, b.getWidth());
        assertEquals(6, b.getHeight());
    }

    // Place sRotated into the board, then check some measures
    public void testSample2() {
        b.commit();
        int result = b.place(sRotated, 1, 1);
        assertEquals(Board.PLACE_OK, result);
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(4, b.getColumnHeight(1));
        assertEquals(3, b.getColumnHeight(2));
        assertEquals(4, b.getMaxHeight());
    }

    // Makre  more tests, by putting together longer series of
    // place, clearRows, undo, place ... checking a few col/row/max
    // numbers that the board looks right after the operations.
    public void testPlace() {
        b.undo();
        assertEquals(Board.PLACE_ROW_FILLED, b.place(pyr1, 0, 0));
        b.commit();
        assertEquals(Board.PLACE_OK, b.place(sRotated, 1, 1));
        b.undo();
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(2, b.getMaxHeight());
        assertEquals(3, b.getRowWidth(0));
        assertEquals(1, b.getRowWidth(1));
        assertEquals(0, b.getRowWidth(2));
        b.commit();
        assertEquals(Board.PLACE_BAD, b.place(sRotated, 0, 0));
        b.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, b.place(sRotated, 2, 5));
        b.undo();
    }

    public void testClear() {
        b.undo();
        b.place(new Piece(Piece.STICK_STR), 0, 0);
        b.clearRows();
        b.commit();
        b.place(new Piece(Piece.STICK_STR), 1, 0);
        b.clearRows();
        b.commit();
        b.place(new Piece(Piece.STICK_STR), 2, 0);
        b.commit();
        assertEquals(4, b.getMaxHeight());
        assertEquals(4, b.clearRows());
    }

    public void testSequences1() {
        b.commit();
        assertEquals(1, b.clearRows());
        assertTrue(b.getGrid(1, 0));
        assertFalse(b.getGrid(1, 1));
        b.undo();
        assertEquals(3, b.getRowWidth(0));
        assertEquals(2, b.getMaxHeight());

        b.place(sRotated, 1, 1);
        b.commit();
        assertEquals(Board.PLACE_ROW_FILLED, b.place(new Piece(Piece.STICK_STR), 0, 1));
        b.commit();
        assertEquals(3, b.clearRows());
        b.commit();
        assertEquals(Board.PLACE_ROW_FILLED, b.place(sRotated, 1, 0));
        assertEquals(3,b.getMaxHeight());
        b.commit();
        assertEquals(2, b.clearRows());
    }

    public void testSequences2() {
        b = new Board(10,15);
        Piece[] allPieces = Piece.getPieces();
        Piece l = allPieces[Piece.L1];
        for(int i =0 ;i <5; i++){
            b.place(l,2,b.dropHeight(l,2));
            b.commit();
            System.out.println(b);
            assertEquals(3*(i + 1) ,b.getColumnHeight(2));
            assertEquals(3 * i + 1,b.getColumnHeight(3));
        }

    }


    public void testDrop(){
        assertEquals(1,b.dropHeight(sRotated,1));
        assertEquals(2,b.dropHeight(sRotated,0));
        b.commit();
        b.place(sRotated,1,1);
        Piece[] p = Piece.getPieces();
        Piece L_Down = p[Piece.L2].fastRotation().fastRotation();
        assertEquals(2, b.dropHeight(L_Down,0));
    }

}