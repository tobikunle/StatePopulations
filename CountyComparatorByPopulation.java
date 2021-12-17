package osu.cse2123;
/**
 * A Comparator for County objects in descending order of population
 * 
 * @author Tani Obikunle
 * @version 12.9.21
 */

import java.util.Comparator;

public class CountyComparatorByPopulation implements Comparator<County> {
	
	private int year;
	
	public CountyComparatorByPopulation(int year) {
		this.year = year;
	}

	@Override
	public int compare(County o1, County o2) {
		int answer = 0;
		
		if (o1.getPopulation(year) > o2.getPopulation(year)) {
			answer = -1;
		}
		else if (o1.getPopulation(year) < o2.getPopulation(year)) {
			answer = 1;
		}
		else {
			answer = 0;
		}
		
		return answer;
	}
	
}