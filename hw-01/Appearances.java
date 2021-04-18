import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int res =0;
		HashMap<T,Integer> first = new HashMap<>();
		HashMap<T,Integer> second = new HashMap<>();
		fillMap(first,a);
		fillMap(second,b);
		for(T key : first.keySet())
			if(first.get(key).equals(second.get(key)))
				res++;


		return res; // YOUR CODE HERE
	}

	private static <T> void fillMap(HashMap<T,Integer> map, Collection<T> array){
		for(T elem : array){
			if(map.containsKey(elem))
				map.put(elem,map.get(elem) + 1);
			else
				map.put(elem,1);
		}
	}
	
}
