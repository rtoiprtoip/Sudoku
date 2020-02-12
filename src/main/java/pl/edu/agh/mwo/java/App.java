package pl.edu.agh.mwo.java;

import org.apache.poi.ss.usermodel.Workbook;

public class App {
    public static void main(String[] args) {

        Workbook f1Wb = WorkbookLoader.openSudokuWorkbook();

        System.out.println("Zadanie 1:");
        SudokuBoardChecker sdkChecker = new SudokuBoardChecker(f1Wb);
        int numOfSheets = f1Wb.getNumberOfSheets();
        for (int i = 0; i < numOfSheets; i++) {
            if (sdkChecker.verifyBoardStructure(i)) {
                System.out.println(i + " sudoku sheet is correct.");
            } else {
                System.out.println(i + " sudoku sheet is not correct.");
            }
        }
        System.out.println("*********************");
        System.out.println("Zadanie 2:");
        for (int i = 0; i < numOfSheets; i++) {
            if (sdkChecker.verifyBoard(i)) {
                System.out.println(i + " sudoku sheet is correct.");
            } else {
                System.out.println(i + " sudoku sheet is not correct.");
            }
        }
    }

}