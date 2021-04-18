// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;
import org.junit.Test;

public class TabooTest extends TestCase {

    public void testTaboo1() {
        Taboo<String> taboo = new Taboo<>(Arrays.asList("a", "c", "a", "b"));
        Set<String> result = new HashSet<>(Arrays.asList("c", "b"));

        assertEquals(result, taboo.noFollow("a"));
        assertEquals(Collections.emptySet(), taboo.noFollow("x"));

        List<String> toReduce = new ArrayList<>(Arrays.asList("a", "c", "b", "x", "c", "a"));
        taboo.reduce(toReduce);
        List<String> result1 = new ArrayList<>(Arrays.asList("a", "x", "c"));
        assertEquals(result1, toReduce);
    }

    public void testTaboo2() {
        Taboo<String> taboo = new Taboo<>(Collections.<String>emptyList());
        List<String> toReduce = new ArrayList<>(Arrays.asList("toko", "magari", "kaci"));
        List<String> result = new ArrayList<>(Arrays.asList("toko", "magari", "kaci"));
        assertEquals(Collections.emptySet(), taboo.noFollow("a"));
        assertEquals(Collections.emptySet(), taboo.noFollow("x"));
        taboo.reduce(toReduce);
        assertEquals(result, toReduce);

        Taboo<Integer> taboo1 = new Taboo<>(Arrays.asList(1, 7, null, 6, 12, 1, 42));
        Set<Integer> result1 = new HashSet<>(Arrays.asList(7, 42));
        assertEquals(result1, taboo1.noFollow(1));
        List<Integer> toReduce2 = new ArrayList<>(Arrays.asList(1, 6, 5, 6, 12, 7, 1, 42, 7));
        List<Integer> result2 = new ArrayList<>(Arrays.asList(1, 6, 5, 6, 7, 1));
        taboo1.reduce(toReduce2);
        assertEquals(result2, toReduce2);
    }
}
