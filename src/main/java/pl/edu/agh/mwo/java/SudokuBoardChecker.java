package pl.edu.agh.mwo.java;

import org.apache.poi.ss.usermodel.*;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoardChecker {

    private final Workbook wb;

    public SudokuBoardChecker(Workbook workbook) {
        wb = workbook;
    }

    public boolean verifyBoardStructure(int sheetIndex) {
        Sheet ws = wb.getSheetAt(sheetIndex);
        for (Row row : ws) {
            for (Cell cell : row) {
                if (!isCellSyntacticallyCorrect(cell)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyBoard(int sheetIndex) {
        return verifyBoardStructure(sheetIndex) && verifyBoardContent(sheetIndex);
    }

    private boolean verifyBoardContent(int sheetIndex) {
        Sheet ws = wb.getSheetAt(sheetIndex);
        int[][] sudokuArr = SudokuSheetToArray(ws);

        for (int row = 0; row < 9; row++) {
            if (checkDuplicatesInRow(sudokuArr, row)) return false;
        }

        for (int column = 0; column < 9; column++) {
            if (checkDuplicatesInColumn(sudokuArr, column)) return false;
        }

        for (int startColumn = 0; startColumn < 9; startColumn += 3) {
            for (int startRow = 0; startRow < 9; startRow += 3) {
                if (checkDuplicatesInSmallSquare(sudokuArr, startRow, startColumn)) return false;
            }
        }

        return true;
    }

    private boolean checkDuplicatesInRow(int[][] sudokuArr, int row) {
        Set<Integer> digits = new HashSet<>();
        for (int col = 0; col < 9; col++) {
            int digit = sudokuArr[row][col];
            if (digit != 0) {
                boolean wasPresentBefore = digits.add(digit);
                if (wasPresentBefore) return true;
            }
        }
        return false;
    }

    private boolean checkDuplicatesInColumn(int[][] sudokuArr, int col) {
        Set<Integer> digits = new HashSet<>();
        for (int row = 0; row < 9; row++) {
            int digit = sudokuArr[row][col];
            if (digit != 0) {
                boolean wasPresentBefore = digits.add(digit);
                if (wasPresentBefore) return true;
            }
        }
        return false;
    }

    private boolean checkDuplicatesInSmallSquare(int[][] sudokuArr, int startRow, int startCol) {
        Set<Integer> digits = new HashSet<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                int digit = sudokuArr[row + startRow][col + startCol];
                if (digit != 0) {
                    boolean wasPresentBefore = digits.add(digit);
                    if (wasPresentBefore) return true;
                }

            }
        }
        return false;
    }

    private boolean isCellSyntacticallyCorrect(Cell cell) {
        if (cell.getCellType() == CellType.BLANK)
            return true;

        if (cell.getCellType() == CellType.NUMERIC)
            return isSingleDigitPositiveNaturalNumber(cell.getNumericCellValue());

        return false;
    }

    private boolean isSingleDigitPositiveNaturalNumber(double number) {
        return number > 0 && number < 10 && number == (int) number;
    }

    private int[][] SudokuSheetToArray(Sheet ws) {
        int[][] sudokuArr = new int[9][9];
        int rowCounter = 0;
        int colCounter;
        for (Row row : ws) {
            colCounter = 0;
            for (Cell cell : row) {
                int value = cell.getCellType() != CellType.BLANK
                        ? (int) cell.getNumericCellValue()
                        : 0;
                sudokuArr[rowCounter][colCounter] = value;
                colCounter++;
            }
            rowCounter++;
        }
        return sudokuArr;
    }
}
