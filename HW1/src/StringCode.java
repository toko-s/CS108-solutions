import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

    /**
     * Given a string, returns the length of the largest run.
     * A a run is a series of adajcent chars that are the same.
     *
     * @param str
     * @return max run length
     */
    public static int maxRun(String str) {
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            int curr = 0;
            char toCompare = str.charAt(i);
            for (int j = i; j < str.length() - 1; j++) {
                if (toCompare != str.charAt(j)) {
                    i = j - 1;
                    break;
                }
                curr++;
            }
            if (curr > max)
                max = curr;
        }
        return max; // YOUR CODE HERE
    }


    /**
     * Given a string, for each digit in the original string,
     * replaces the digit with that many occurrences of the character
     * following. So the string "a3tx2z" yields "attttxzzz".
     *
     * @param str
     * @return blown up string
     */
    public static String blowup(String str) {
        String res = "";
        for (int i = 0; i < str.length() - 1; i++) {
            char ch = str.charAt(i);
            if (isNum(ch)) {
                String tmp = "";
                for (int j = 0; j < getNum(ch); j++)
                    tmp += str.charAt(i + 1);
                res += tmp;
            } else
                res += ch;
        }
        if (str.length() != 0 && !isNum(str.charAt(str.length() - 1)))
            res += str.charAt(str.length() - 1);
        return res; // YOUR CODE HERE
    }

    private static boolean isNum(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static int getNum(char ch) {
        return ch - '0';
    }

    /**
     * Given 2 strings, consider all the substrings within them
     * of length len. Returns true if there are any such substrings
     * which appear in both strings.
     * Compute this in linear time using a HashSet. Len will be 1 or more.
     */
    public static boolean stringIntersect(String a, String b, int len) {
        HashSet<String> seen = new HashSet<>();
        for (int i = 0; i <= a.length() - len; i++)
            seen.add(a.substring(i, i + len));
        for(int j =0 ;j <= b.length() - len; j++)
            if(seen.contains(b.substring(j, j + len)))
                return true;

        return false; // YOUR CODE HERE
    }
}
