package osu.cse2123;
/**
 * A program that displays population data based on the range of years requested
 * 
 * @author Tani Obikunle
 * @author Jack Price
 * @version 12/5/21
 *
 */

import java.text.NumberFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Reporting {
	
	/**
	 * organizes input file into each state and its counties
	 * @param input the input file
	 * @return list the list of states and counties
	 * @throws FileNotFoundException
	 */
	public static List<State> organize(String input) throws FileNotFoundException{
		
		//create a list of states for the combined state population file to be loaded into
		List<State> list = new ArrayList<State>();
		
		//first scanner to scan the populationFiles.csv file to get each state 
		File file1 = new File(input);
		Scanner read1 = new Scanner(file1);
		
		//while scanner has next line 
		while(read1.hasNext()) {
			//create a list of strings for the states to be loaded into
			List<String> strList = Arrays.asList(read1.nextLine().split(","));
			//new state object load to load each state
			State state = new SimpleState();
			//set the name of the state
			state.setName(strList.get(0));
			
			//second scanner to scan each state population file to get each county
			File file2 = new File(strList.get(1));
			Scanner read2 = new Scanner(file2);
			
			//splitting each state info by commas --> 2 commas total
			List<String> header = Arrays.asList(read2.nextLine().split(","));
			
			//while the state info scanner 
			while (read2.hasNext()) {
				//new county object to load info for each county
				County county = new SimpleCounty();
				//list of strings for the county info
				List<String> data = Arrays.asList(read2.nextLine().split(","));
				//set county name to first item of the list
				county.setName(data.get(0));
				//for loop to get population for each year 2010-2019
				for (int i = 1; i < data.size(); i++) {
					county.addPopulation(Integer.parseInt(header.get(i)), Integer.parseInt(data.get(i)));;
				}
				
				//add each county to the appropriate state
				state.addCounty(county);
			}
			//close county scanner
			read2.close();
			list.add(state);

		}
		//close state scanner
		read1.close();
		//return list of states and counties
		return list;

	}
	
	/**
	 * displays the correctly formatted data by calling on other methods
	 * @param states list of states which contain counties
	 * @param year1 first year user input
	 * @param year2 second year user input
	 */
	public static void displayData(List<State> states, int year1, int year2) {
		
		//strings to put in the formatted output
		String header = "State/County";

		String growth = "Growth";
		
		//header formatting - used a character counter 
		System.out.format("%-15s %12d %12d %12s", header, year1, year2, growth);
		System.out.println();
		System.out.println("--------------- ------------ ------------ ------------");
		System.out.println();
	
		//***STATES***
		//for the number of states there are, create a heading with state name, population from year 1, population from year 2, and growth
		for (int i = 0; i < states.size(); i++) {
			
			State state = states.get(i);
			
			String formGr;
			
			//found a number formatter on stack overflow to add commas to a number larger than 999
			String formSP1 = NumberFormat.getIntegerInstance().format(state.getPopulation(year1));
			String formSP2 = NumberFormat.getIntegerInstance().format(state.getPopulation(year2));
			
			//if the growth is a positive number or zero, add a plus sign infront and format it with commas
			if (stateGrowth(state, year1, year2) > 0 ) {
				formGr = "+" + NumberFormat.getIntegerInstance().format(stateGrowth(state, year1, year2));
			}
			//if the growth is negative, just format it with commas
			else {
			formGr = NumberFormat.getIntegerInstance().format(stateGrowth(state, year1, year2));
			}
			System.out.println("--------------- ------------ ------------ ------------");
			
			System.out.format("%-15s %12s %12s %12s", state.getName() , formSP1, 
					formSP2, formGr);
			System.out.println();
			System.out.println("--------------- ------------ ------------ ------------");
			
			//***COUNTIES***
			//for the number of counties in the current state
			for (int j = 0; j < state.getCounties().size(); j++){
				//first list to get the counties by population in year 1
				List<County> y1list = state.getCountiesByPopulation(year1);
				//second list to get the counties by population in year 2
				List<County> y2list = state.getCountiesByPopulation(year2);
				
				//create integer and assign it to the value of the growth between year 1 and year 2
				int countyGr = countyGrowth(y1list.get(j), year1, year2);
				//create string for the number formatter - number formatter returns a string
				String grow = "";
				
				//if the value of county growth is positive or 0, add a plus sign and format with commas
				if (countyGr >= 0) {
					grow = "+" + NumberFormat.getIntegerInstance().format(countyGr);
				}
				//if county growth is negative, just format with commas
				else if (countyGr < 0) {
					grow = "" + NumberFormat.getIntegerInstance().format(countyGrowth(y1list.get(j), year1, year2));
				}
				
				String formatY1 = NumberFormat.getIntegerInstance().format( y1list.get(j).getPopulation(year1));
				String formatY2 = NumberFormat.getIntegerInstance().format( y2list.get(j).getPopulation(year2));
				
				System.out.format("%-15.15s %12s %12s %12s", "   " + y1list.get(j).getName(), 
				formatY1, formatY2, grow);
				
				System.out.println();
			
			}
			System.out.println("--------------- ------------ ------------ ------------");
			
			//number for matting for average values
			String formAvg1 = NumberFormat.getIntegerInstance().format(average(state, year1));
			String formAvg2 = NumberFormat.getIntegerInstance().format(average(state, year2));
			
			System.out.format("%-15.15s %12s %12s", "   " + "Average pop.",formAvg1, formAvg2);
			System.out.println();
			
			//number formatting for median values
			String formMed1 = NumberFormat.getIntegerInstance().format(median(state, year1));
			String formMed2 = NumberFormat.getIntegerInstance().format(median(state, year2));
			
			System.out.format("%-15.15s %12s %12s%n", "   " + "Median pop.",formMed1,formMed2);
			
			//check
			if (i + 1 != states.size()) {
				System.out.println();
			}
			
		}
	}
	
	/**
	 * calculates the population growth of a county between year 1 and year 2 
	 * @param county 
	 * @param year1
	 * @param year2
	 * @return the county growth
	 */
	public static int countyGrowth(County county, int year1, int year2) {
		int growth = 0;
		
		//growth should equal year 2 population - year 1 population
		growth = county.getPopulation(year2) - county.getPopulation(year1);
		
		//return growth
		return growth;
		
	} 
	
	/**
	 * calculates the population growth of a state between year 1 and year 2 
	 * @param state
	 * @param year1
	 * @param year2
	 * @return the state growth
	 */
	public static int stateGrowth(State state, int year1, int year2) {
		int growth = 0;
		
		//growth should equal year 2 population - year 1 population
		growth = state.getPopulation(year2) - state.getPopulation(year1);
		
		//return growth
		return growth;
		
	} 
	
	/**
	 * calculates the average population of a state in a certain year
	 * @param state
	 * @param year
	 * @return average state population
	 */
	public static int average(State state, int year) {

		//state population divided by # counties
		int avgPop = state.getPopulation(year)/state.getCounties().size();
		
		//return average
		return avgPop;
		
	} 
	
	/**
	 * calculates median population of a state	
	 * @param state
	 * @param year
	 * @return the median population of a state
	 */
	public static int median(State state, int year) {
		int med = 0;
		//get number of counties 
		int size = state.getCounties().size();
		//even number of counties
		if (size % 2 == 0) {
			//find the indexes of the two middle counties
			int idx1 = (size / 2);
			int idx2 = (size / 2) -1;
			
			//set median to the county at the index of the average of the two middle indexes
			med = (state.getCountiesByPopulation(year).get(idx1).getPopulation(year)
					+ state.getCountiesByPopulation(year).get(idx2).getPopulation(year)) / 2;
		}
		//odd number of counties
		else {
			//index of median county is (size - 1) /2
			int index = (size - 1) / 2;
			med = state.getCountiesByPopulation(year).get(index).getPopulation(year);
		}
		
		//return median
		return med;
		
	} 

	public static void main(String[] args) {	
		//open scanner for user input
		Scanner input = new Scanner(System.in); 
		
		
		System.out.print("Enter a list of population files: ");
		String fname = input.nextLine();
		//try incase user enters a file that does not exist
		try {
		List<State> test = organize(fname);
		
		//prompt user to enter first year 
		System.out.print("Enter a start year: ");
		int startYear = input.nextInt();
		//incase user enters a year out of range
		while (startYear > 2019 || startYear < 2010) {
			System.out.println("Please choose a year between 2010 and 2019");
			
			//reprompt user for a valid year
			System.out.print("Enter a start year: ");
			startYear = input.nextInt();
			
		}
		//prompt user to enter second year 
		System.out.print("Enter an end year: ");
		int endYear = input.nextInt();
		//incase user enters a year out of range
		while (endYear > 2019 || endYear < 2010) {
			System.out.println("Please choose a year between 2010 and 2019");
			
			//reprompt user for a valid year
			System.out.print("Enter an end year: ");
			endYear = input.nextInt();
		}
		
		System.out.println();
		
		//call to displayData method
		displayData(test, startYear, endYear);
		}
		//catch message that shows if user enters invalid file 
		catch (Exception FileNotFoundException) {
			System.out.println("File Not Found!");
		}
		
		//close scanner
		input.close();
		
		
	}

}
