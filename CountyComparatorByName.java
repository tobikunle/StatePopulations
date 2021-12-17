package osu.cse2123;
/**
 * A Comparator for County objects in ascending order of name
 * 
 * @author Tani Obikunle
 * @version 12.9.21
 */

import java.util.Comparator;

public class CountyComparatorByName implements Comparator<County> {
	

	@Override
	public int compare(County o1, County o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
}