package pl.edu.agh.mwo.java;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;


public class WorkbookLoader {

    public static Workbook openSudokuWorkbook() {
        try {
            return WorkbookFactory.create(new File("src/main/resources/sudoku.xlsx"));
        } catch (EncryptedDocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
