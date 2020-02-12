package pl.edu.agh.mwo.java;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;

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
        if (verifyBoardStructure(sheetIndex)) {
            Sheet ws = wb.getSheetAt(sheetIndex);
            ArrayList<Integer> tmpArray = new ArrayList<>();
            int[][] sudokuArr = SudokuSheetToArray(ws);
            //check rows
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (tmpArray.contains(sudokuArr[i][j]) && sudokuArr[i][j] != 0) {
                        return false;
                    } else {
                        tmpArray.add(sudokuArr[i][j]);
                    }
                }
                tmpArray.clear();
            }
            //check columns
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (tmpArray.contains(sudokuArr[j][i]) && sudokuArr[j][i] != 0) {
                        return false;
                    } else {
                        tmpArray.add(sudokuArr[j][i]);
                    }
                }
                tmpArray.clear();
            }
            //check 3x3 fields
            for (int ii = 0; ii < 9; ii += 3) {
                for (int jj = 0; jj < 9; jj += 3) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (tmpArray.contains(sudokuArr[j + jj][i + ii]) && sudokuArr[j + jj][i + ii] != 0) {
                                return false;
                            } else {
                                tmpArray.add(sudokuArr[j + jj][i + ii]);
                            }
                        }
                        tmpArray.clear();
                    }
                }
            }
            return true;
        } else {
            return false;
        }

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
