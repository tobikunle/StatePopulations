package osu.cse2123;

/**
 * A concrete class that implements County.java
 * 
 * @author taniobikunle
 *
 */

import java.util.HashMap;

public class SimpleCounty implements County {

	private String name;
	private HashMap<Integer, Integer> yearPop;
	
	public SimpleCounty() {
		this.name = "";
		this.yearPop = new HashMap<Integer, Integer>();
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addPopulation(int year, int pop) {
		this.yearPop.put(year, pop);

	}

	@Override
	public int getPopulation(int year) {
		return this.yearPop.get(year);
	}
	
	@Override
	//for value of hash map (population)
	public String toString() {
		String str = "";
		//iterate through hashmap
		for(HashMap.Entry<Integer,Integer> entry : yearPop.entrySet()) {
			//add value to the string 
			str = str + entry.getValue() + " ";
		}
		//return string 
		return str;
	}

}
