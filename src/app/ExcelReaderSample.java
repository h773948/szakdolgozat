package app;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderSample {

    public static Double[] ReadExcelSample(String URL) throws IOException {
        List<Double> values= new ArrayList<Double>();
        double sum=0;
        int count=0;
        String excelFilePath = URL;
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
                            System.out.println(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                           //System.out.println(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            if(cell.getNumericCellValue()<0){break;}
                            values.add(cell.getNumericCellValue());
                            break;
                    }
                }
            }
        }
        System.out.println(values.size());
        Double[] valuesArr = new Double[values.size()];

        for(int i=0; i< values.size();i++){
            valuesArr[i] = values.get(i);
          //  System.out.print(valuesArr[i]);
        }

        //visszaadja az utolsó oszlop számát, 1től indexelve
        int noOfColumns = firstSheet.getRow(0).getLastCellNum();
        System.err.println(noOfColumns);
        workbook.close();
        inputStream.close();


        return valuesArr;
    }
}
