package app;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleExcelReaderExample {

    public static void main(String[] args) throws IOException {
        List values= new ArrayList();
        double sum=0;
        int count=0;
        String excelFilePath = "Amiens_1987_2017.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();

                if(columnIndex==1) {

                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:

                            if(cell.getNumericCellValue()<0){ /*System.out.println("sor: "+ cell.getRowIndex() + "oszlop: "+ cell.getColumnIndex());*/
                                break;}
                            System.out.println("oszlop: " + (cell.getColumnIndex()+1));
                            System.out.println("sor: " + (cell.getRowIndex()+1));
                           // values.add(cell.getNumericCellValue());
                            sum+=cell.getNumericCellValue();
                            count+=1;
                            System.out.println("+++ertek:"+  cell.getNumericCellValue());
                            //System.out.println(cell.getDateCellValue()); //ha datum
                            break;
                    }
                    //System.out.println(cell.getCellType());
                   // System.out.print(" - ");
                }
            }
           // System.out.println(values.toString());
        }
        System.out.println("sum " + Math.round(sum*100.0)/100.0);
        System.out.println("count " + count);
        workbook.close();
        inputStream.close();
    }

}