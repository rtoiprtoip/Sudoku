package pl.edu.agh.mwo.java;

import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;

public class PoiDemo {

    public static void printSheetNames(Workbook wb){
        System.out.println("The given workbook contains the following sheets:");
        for (Sheet sheet : wb) {
            System.out.println(sheet.getSheetName());
        }
    }

    public static void printCellsFromSheet(Sheet sheet){
        System.out.println(String.format("The content of %s sheets:", sheet.getSheetName()));

        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue() + " ");
            }
            System.out.println();
        }
    }

    public static void computePointsForARace(Sheet sheet) {
        System.out.println(String.format("The overall number of points in %s race:", sheet.getSheetName()));
        int sum =0;
        for (Row row : sheet) {
            Cell cell = row.getCell(5);
            if (cell.getCellType().equals(CellType.STRING)) {
                String value = cell.getStringCellValue();
                int intValue = Integer.valueOf(value);
                sum += intValue;
            }
        }
        System.out.println(sum);
    }

    public static void computeAllNumberInAGivenColumn(Sheet sheet, int column) {

        int sum =0;
        for (Row row : sheet) {
            Cell cell = row.getCell(column);
            if (cell.getCellType().equals(CellType.NUMERIC)) {
                sum += (int)cell.getNumericCellValue();
            }
        }
        System.out.println(String.format("The sum of all numbers in column %d is %d", column, sum));
    }
    
	public static void exf1a(Workbook wb) {
		System.out.println("winers are:"); 
		Set<String> winners = new HashSet<String>();
	    for (Sheet sheet :  wb) {
	    	winners.add(sheet.getRow(0).getCell(2).getStringCellValue());

	    }
	    System.out.println(winners); 
	}
	
	public static void howManyPoints(Workbook wb, String driver, int col) {
		double points = 0;
		for (Sheet sheet :  wb) {
			for (Row row : sheet) {
				if (row.getCell(col).getStringCellValue().equals(driver)){
					points = points + Double.parseDouble(row.getCell(5).getStringCellValue());
				}
			}
		}
		System.out.println(driver + " points: "); 
		System.out.println(points); 
	}
	
	public static void whoWins(Workbook wb) {
		Map <String, Integer> drivers = new HashMap<>();
		String tmp;
		Integer tmpInt;
		for (Sheet sheet :  wb) {
			for (Row row : sheet) {
				tmp = row.getCell(2).getStringCellValue();
				tmpInt = Integer.parseInt(row.getCell(5).getStringCellValue());
				if (!drivers.containsKey(tmp)) {
					drivers.put(tmp, tmpInt);
				}else {
					drivers.put(tmp, tmpInt + drivers.get(tmp));
				}
				
			}
		}
		List<Entry<String, Integer>> driversList = new ArrayList<>(drivers.entrySet());
		
		driversList.sort(Entry.comparingByValue());
		
		Collections.reverse(driversList);
		System.out.println(driversList);
	}
	
	
}
