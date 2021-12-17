/**
 * 
 */
package osu.cse2123;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author taniobikunle
 *
 */


public class SimpleState implements State {

	private String name;
	private ArrayList<County> counties;

	public SimpleState() {
		this.name = name;
		counties = new ArrayList<>();
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
	public void addCounty(County county) {
		counties.add(county);
	}

	@Override
	public County getCounty(String name) { 
		
		//create new county obj
		County retrieved = new SimpleCounty();
		
		//iterate 
		for (County county : counties) {
			if(county.getName().equals(name)) {
				retrieved = county;
			}
		}
		return retrieved;
	}

	@Override
	public int getPopulation(int year) {
		int sumPop = 0;
		//iterate through 
		for (int i = 0; i<counties.size(); i++) {
			//get county population
			int countyPop = counties.get(i).getPopulation(year);
			//add county population to total population
			sumPop += countyPop;
		}
		return sumPop;
	}

	@Override
	public List<County> getCounties() {
		//from project description
		CountyComparatorByName cmpN = new CountyComparatorByName();
		Collections.sort(counties, cmpN);
		return counties;
	}

	@Override 
	public List<County> getCountiesByPopulation(int year) {
		//from project description
		CountyComparatorByPopulation cmp = new CountyComparatorByPopulation(year);
		Collections.sort(counties, cmp);
		
		return counties;
	}
	
	public String toString() {
		String str = "";
		//state name -- new line
		str += name + System.lineSeparator();
		//get county populations
		for (int i = 0; i < counties.size(); i++) {
			str += counties.get(i).toString() + System.lineSeparator();
		}
		return str;
	}

}
