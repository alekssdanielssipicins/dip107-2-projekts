//231RDC003 Alekss Daniels Šipicins

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

abstract class Entry {
    public abstract Object getValue();
	public abstract boolean isValid();
}

class Id extends Entry {
	private Integer value;
	
	public Id(String id) {
		try{
			value = Integer.parseInt(id);
			if(value < 100 || value > 999) {
				value = null;
			}
		}
		catch (Exception e) {
			value = null;
		}
	}
	
	public boolean isValid() {
		if (value == null) {
			System.out.println("wrong id");
			return false;
		}
		else{
			return true;
		}
	}
	
	public Integer getValue() {
		return value;
	}
}

class City extends Entry {
	private String name;
	
	public City(String name) {
		try {
		String[] splitArray = name.split(" ");
		String temporaryWord = new String();
		for(String word: splitArray) {
			temporaryWord += word.substring(0, 1).toUpperCase();
			temporaryWord += word.substring(1).toLowerCase();
			temporaryWord += " ";
		}
		this.name = temporaryWord.substring(0, temporaryWord.length()-1);
		}
		catch (Exception e) {
			this.name = null;
		}
	}
	
	public boolean isValid() {
		if(name != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getValue() {
		return name;
	}
}

class Date extends Entry {
	private Integer day, month, year;
	
	public boolean checkValid() {
		if(day == null || month == null || year == null) {
			return false;
		}
		
		if (day <1 || year < 1000 || year > 9999) {
			return false;
		}
		switch (month) {
		case 1, 3, 5, 7, 8, 10, 12:
			if (day <= 31) {
				return true;
			}
			else{
				return false;
			}
		case 2:
			if ((year % 100 !=0 && year % 4 == 0) || year % 400 == 0) {
				if (day <= 29) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (day <= 28) {
				return true;
			}
			else {
				return false;
			}
		case 4, 6, 9, 11:
			if (day<=30) {
				return true;
			}
			else {
				return false;
			}
		default:
			return false;
		}
	}
	
	public Date(String ddmmyyyy) {
		String[] splitArray = ddmmyyyy.split("/");
		try {
		day = Integer.parseInt(splitArray[0]);
		month = Integer.parseInt(splitArray[1]);
		year = Integer.parseInt(splitArray[2]);
		}
		catch (Exception e) {
			day = null;
			month = null;
			year = null;
		}
	}
	
	public boolean isValid() {
		if (checkValid()) {
			return true;
		}
		else {
			System.out.println("wrong date");
			return false;
		}
	}
	
	public String getValue() {
		String value = new String();
		if (day < 10) {
			value += "0";
		}
		value += day;
		value += "/";
		
		if (month < 10) {
			value += "0";
		}
		value += month;
		value += "/";
		value += year;
		
		return value;
	}
	
	public boolean isBefore(Date date2) {  
		
		if(this.year < date2.year) {
			return true;
		} 
		else if(this.year == date2.year && this.month < date2.month) {
			return true;
		}
		else if(this.month == date2.month && this.day < date2.day) {
			return true;
		}
		else {
			return false;
		}
	}
}

class Days extends Entry {
	private Integer value;
	
	public Days(String days) {
		try {
			value = Integer.parseInt(days);
		}
		catch (Exception e) {
			value = null;
		}
	}
	
	public boolean isValid() {
		if (value == null) {
			System.out.println("wrong day count");
			return false;
		}
		else{
			return true;
		}
	}
	
	public Integer getValue() {
		return value;
	}
}

class Price extends Entry {
	private Double value;
	
	public Price(String price) {
		try {
			value = Double.parseDouble(price);
		}
		catch (Exception e) {
			value = null;
		}
	}
	
	public boolean isValid() {
		if (value == null) {
			System.out.println("wrong price");
			return false;
		}
		else {
			return true;
		}
	}
	
	public Double getValue() {
		return value;
	}
}

class Vehicle extends Entry {
	private String code;
	
	public Vehicle(String code) {
		switch(code.toUpperCase()) {
			case "TRAIN", "PLANE", "BUS", "BOAT":
				this.code = code.toUpperCase();
			default:
				code = null;
		}
	}
	
	public boolean isValid() {
		if (code != null) {
			return true;
		}
		else {
			System.out.println("wrong vehicle");
			return false;
		}
	}
	
	public String getValue() {
		return code;
	}
}

class CsvLine {
	
	private List<Entry> entries;
	
	private boolean full;
	
	public CsvLine(String stringLine) {
		
		
		String[] splitArray = stringLine.split(";");
		
		if (splitArray.length !=6) {
			System.out.println("wrong field count");
			full = false;
		}
		else {
			
			Id id = new Id(splitArray[0]);
			if(id.isValid()) {
				City city = new City(splitArray[1]);
				if(city.isValid()) {
					Date date = new Date(splitArray[2]);
					if(date.isValid()) {
						Days days = new Days(splitArray[3]);
						if(days.isValid()) {
							Price price = new Price(splitArray[4]);
							if(price.isValid()) {
								Vehicle vehicle = new Vehicle(splitArray[5]);
								if(vehicle.isValid()) {
									entries = new ArrayList<>(6);
									
									entries.add(id);
									entries.add(city);
									entries.add(date);
									entries.add(days);
									entries.add(price);
									entries.add(vehicle);
									full = true;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public CsvLine(String stringLine, boolean forEdit) {
		
		String[] splitArray = stringLine.split(";");
		
		if(splitArray.length == 6) {
			
			Id id = new Id(splitArray[0]);
			City city = new City(splitArray[1]);
			Date date = new Date(splitArray[2]);
			Days days = new Days(splitArray[3]);
			Price price = new Price(splitArray[4]);
			Vehicle vehicle = new Vehicle(splitArray[5]);
			
			entries.add(id);
			entries.add(city);
			entries.add(date);
			entries.add(days);
			entries.add(price);
			entries.add(vehicle);
			
			full = true;
			
		}
		else {
			full = false;
		}
		
	}
	
	public CsvLine(List<Entry> entries) {
		this.entries = entries;
	}
	
	public void printLine() {
		System.out.print(String.format("%-4s", entries.get(0).getValue()));
		System.out.print(String.format("%-21s", entries.get(1).getValue()));
		System.out.print(String.format("%-11s", entries.get(2).getValue()));
		System.out.print(String.format("%6s", entries.get(3).getValue()));
		System.out.print(String.format("%10.2f", entries.get(4).getValue()));
		System.out.println(" " + String.format("%-7s", entries.get(5).getValue()));
	}
	
	public String returnString() {
		StringBuilder sb = new StringBuilder();
	    sb.append(entries.get(0).getValue().toString());
	    for (int i = 1; i < entries.size(); i++) {
	        sb.append(";").append(entries.get(i).getValue().toString());
	    }
	    return sb.toString();
	}
	
	public List<Entry> returnArray(){
		return entries;
	}
	
	public boolean isFull() {
		if(full) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Double getPrice() {
		Entry price = entries.get(4);
		return (Double) price.getValue();
	}
	
	public Integer getId() {
		Entry id = entries.get(0);
		return (Integer) id.getValue();
	}
	
	public Date returnDate() {
		Date date = (Date) entries.get(2);
		return date;
	}
	
}


public class Main {
	
	public static List<CsvLine> fileToLineList(String fileName) throws IOException{
		
		List<CsvLine> lineList = new ArrayList<>();
		
		FileReader fin = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fin);
		
		String line;
		
		while((line = br.readLine()) != null) {
			
			CsvLine newLine = new CsvLine(line);
			lineList.add(newLine);
		}
		
		br.close();

		return lineList;
	}
	
	public static void print(String fileName) throws IOException{
		
		print(fileName, false, null);
	}
	
	public static void print(String fileName, boolean find, Double price) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName);
		
		System.out.println("------------------------------------------------------------\n"
				+ "ID  City                 Date         Days     Price Vehicle\n"
				+ "-------------------------------------------------------------");
		
		for (CsvLine line : lineList) {
			
			if (find) {
				if (line.getPrice() <= price) {
					line.printLine();
				}
			}
			else {
				line.printLine();
			}
        }
		
		System.out.println("------------------------------------------------------------");
		
	}

	public static void avg(String fileName) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName);
		Double sum = 0.0;
		for (CsvLine line : lineList) {
			sum += line.getPrice();
		}
		Double avg = sum/lineList.size();
		System.out.println(String.format("%.2f", avg));
	}
	
	public static void lineListToCsvFile(String fileName, List<CsvLine> lineList) throws IOException{
		
		File filePath = new File(fileName);
		
		filePath.delete();

		FileWriter fout = new FileWriter(fileName);
		PrintWriter out = new PrintWriter(fout);
		
		for(CsvLine line: lineList) {
			out.println(line.returnString());
		}
		
		out.close();

	}
	
	public static boolean checkIdUniqueness(List<CsvLine> lineList, int idToAdd){
		
		boolean unique = true;
		
		for(CsvLine line : lineList) {
			if(idToAdd == line.getId()) {
				unique = false;
				break;
			}
		}
		
		return unique;
	}
	
	public static Integer returnIdIndex(List<CsvLine> lineList, int id) {
		
		Integer index = null;
		
		int i = 0;
		
		for(CsvLine line : lineList) {
			if(line.getId() == id) {
				index = i;
			}
			i++;
		}
		
		return index;
		
	}
	
	public static void add(String fileName, String stringLine) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName); //
		
		CsvLine newCsvLine = new CsvLine(stringLine);
		
		int idToAdd = newCsvLine.getId();
			
		
		if(checkIdUniqueness(lineList, idToAdd)) {
				
			lineList.add(newCsvLine);
			
			lineListToCsvFile(fileName, lineList);
			System.out.println("added");
			
		}
		else {
			System.out.println("wrong id");
		}
	}
	
	public static void del(String fileName, int idToDelete) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName);
		
		Integer indexToDelete = returnIdIndex(lineList, idToDelete);
		
		if(indexToDelete != null) {
			lineList.remove((int)indexToDelete);
			lineListToCsvFile(fileName, lineList);
			System.out.println("deleted");
		}
		else {
			System.out.println("wrong id");
		}
		
	}
	
	public static void sort(String fileName) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName);

	    int arraySize = lineList.size();

	    for (int i=0; i<arraySize-1; i++) {
	        int minIndex = i;
	        for (int j=i+1; j<arraySize; j++) {
	            Date date1 = lineList.get(j).returnDate();
	            Date date2 = lineList.get(minIndex).returnDate();
	            if (date1.isBefore(date2)) {
	                minIndex = j;
	            }
	        }
	        if (minIndex != i) {
	        	
	            CsvLine temp = lineList.get(minIndex);
	            lineList.set(minIndex, lineList.get(i));
	            lineList.set(i, temp);
	        }
	    }
	    
	    lineListToCsvFile(fileName, lineList);
	}
	
	public static void edit(String fileName, String stringLine) throws IOException{
		
		List<CsvLine> lineList = fileToLineList(fileName);
		
		CsvLine editParameter = new CsvLine(stringLine, true);
		
		if(editParameter.isFull()) {
			int idToEdit = editParameter.getId();
			Integer indexToEdit = returnIdIndex(lineList, idToEdit);
			if(indexToEdit != null) {
				
				CsvLine lineToEdit = lineList.get(indexToEdit);
				
				List<Entry> entriesToEdit = lineToEdit.returnArray();
				List<Entry> entriesEditParameter = editParameter.returnArray();
				
				for(int i=1; i<6; i++) {
					Entry entryEditParameter = entriesEditParameter.get(i);
					if(entryEditParameter.getValue() != null) {
						entriesToEdit.set(i, entryEditParameter);
					}
				}
				CsvLine editedLine = new CsvLine(entriesToEdit);
				
				lineList.set(idToEdit, editedLine);
				lineListToCsvFile(fileName, lineList);
				System.out.println("changed");
			}
			else {
				System.out.println("wrong id");
			}
		}
		else {
			System.out.println("wrong field count");
		}
		
	}
	

public static void main(String[] args) throws IOException{
		
		String cmd = new String();
		
		String fileName = "db.csv";
		
		Scanner sc = new Scanner(System.in);
		
		while (!cmd.equals("exit")) {
			System.out.println("Enter command: ");
			String input = sc.nextLine();
			String[] inputArray = input.split(" ");
			cmd = inputArray[0];
			switch (cmd) {
			
			case "add":
				try{
					String stringLine = inputArray[1];
					add(fileName, stringLine);
					break;
				}
				catch (NullPointerException e) {
					break;
				}
				catch (Exception e) {
					System.out.println(e);
					break;
				}
				
			case "del":
				try {
					int idToDelete = Integer.parseInt(inputArray[1]);
					del(fileName, idToDelete);
					break;
				}
				catch(NumberFormatException e) {
					System.out.println("wrong id");
					break;
				}
				catch(Exception e) {
					System.out.println(e);
					break;
				}
				
			case "edit":
				
				try {
					String stringLine = inputArray[1];
					edit(fileName, stringLine);
					break;
				}
				catch(Exception e) {
					System.out.println(e);
					break;
				}
				
			case "print":
				try {
					print(fileName);
					break;
				}
				catch(Exception e) {
					System.out.println(e);
					break;
				}
				
			case "sort":
				try {
					sort(fileName);
					System.out.println("sorted");
					break;
				}
				catch (Exception e) {
					System.out.println(e);
					break;
				}	
				
			case "find":
				try {
					Double price = Double.parseDouble(inputArray[1]);
					print(fileName, true, price);
					break;
				}
				catch (NumberFormatException e) {
					System.out.println("wrong price");
					break;
				}
				catch (Exception e) {
					System.out.println(e);
					break;
				}
				
			case "avg":
				try {
					avg(fileName);
					break;
				}
				catch (Exception e) {
					System.out.println(e);
					break;
				}
				
			case "exit":
				sc.close();
				break;
				
			default:
				System.out.println("wrong command");
				break;
			}		
		}
	}
}

/*
 * Šī programma ir izstrādāta 2. projekta studiju kursā DIP107 uzdevuma ietvaros
 */