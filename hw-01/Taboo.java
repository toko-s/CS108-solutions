
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
    private HashMap<T, Set<T>> rules;

    /**
     * Constructs a new Taboo using the given rules (see handout.)
     *
     * @param rules rules for new Taboo
     */
    public Taboo(List<T> rules) {
        this.rules = new HashMap<>();
        fillRules(rules);
    }

    private void fillRules(List<T> lst) {
        for (int i = 0; i < lst.size() - 1; i++) {
            T elem = lst.get(i);
            T follow = lst.get(i + 1);
            if (follow != null) {
                if (rules.containsKey(elem))
                    rules.get(elem).add(follow);
                else {
                    Set<T> tmp = new HashSet<>();
                    tmp.add(follow);
                    rules.put(elem, tmp);
                }
            }else
                rules.put(elem,Collections.<T>emptySet());
        }
    }

    /**
     * Returns the set of elements which should not follow
     * the given element.
     *
     * @param elem
     * @return elements which should not follow the given element
     */
    public Set<T> noFollow(T elem) {
        if(rules.containsKey(elem))
            return rules.get(elem);
        else
            return Collections.emptySet();
    }

    /**
     * Removes elements from the given list that
     * violate the rules (see handout).
     *
     * @param list collection to reduce
     */
    public void reduce(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            T elem = list.get(i);
            Set<T> curr_rule = rules.get(elem);
			if(curr_rule != null &&  curr_rule.contains(list.get(i+1))) {
                list.remove(i + 1);
                i--;
            }
        }
    }
}
