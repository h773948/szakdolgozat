package app.controller;

import app.view.Controller;
import app.model.OutputTable;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOutput {

    OutputTable annualOutputTable;
    OutputTable dailyOutputTable;

    public CreateOutput(OutputTable annualOutputTable, OutputTable dailyOutputTable) {
        this.annualOutputTable = annualOutputTable;
        this.dailyOutputTable = dailyOutputTable;
    }

    public OutputTable getAnnualOutputTable() {
        return annualOutputTable;
    }

    public void setAnnualOutputTable(OutputTable annualOutputTable) {
        this.annualOutputTable = annualOutputTable;
    }

    public OutputTable getDailyOutputTable() {
        return dailyOutputTable;
    }

    public void setDailyOutputTable(OutputTable dailyOutputTable) {
        this.dailyOutputTable = dailyOutputTable;
    }


    public void createExcels(String originalFileName) throws IOException {
        String url = chooseDir();
        createAnnualExcel(url, originalFileName);
        createDailyExcel(url, originalFileName);

    }

    public String chooseDir() {

        Stage directoryChoosingStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("choose final directory");
        Controller.getH().update("Choose where you want to save the output.", "Hint");
        File selectedDirectory = directoryChooser.showDialog(directoryChoosingStage);
        return selectedDirectory.getAbsolutePath();
    }

    public void createAnnualExcel(String url, String sheetname) throws IOException {
        String excelFilePath = url + "\\" + sheetname + "_annual.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetname);
        int rowCount = 0;
        Row row = sheet.createRow(rowCount++);
        writeAnnualHeaderRow(row);

        for (int i = 0; i < annualOutputTable.getPollenNames().size(); i++) {
            row = sheet.createRow(rowCount++);
            writeAnnualPollen(i, row);

        }

        try {
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            Date date = new Date(System.currentTimeMillis());

            FileOutputStream outputStream = new FileOutputStream(url + "\\" + sheetname + "_annual_" + formatter.format(date) + ".xlsx");
            workbook.write(outputStream);
            outputStream.close();
        }
        workbook.close();


    }

    public void createDailyExcel(String url, String sheetname) throws IOException {
        String excelFilePath = url + "\\" + sheetname + "_daily.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetname);
        int rowCount = 0;
        Row row = sheet.createRow(rowCount++);
        writeDailyHeaderRow(row);

        for (int i = 0; i < dailyOutputTable.getPollenNames().size(); i++) {
            row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(dailyOutputTable.getPollenNames().get(i));

            cell = row.createCell(1);
            cell.setCellValue(dailyOutputTable.getTAPC().get(i));

            cell = row.createCell(2);
            cell.setCellValue(dailyOutputTable.getTAPCSignificance().get(i));
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            Date date = new Date(System.currentTimeMillis());

            FileOutputStream outputStream = new FileOutputStream(url + "\\" + sheetname + "_daily_" + formatter.format(date) + ".xlsx");
            workbook.write(outputStream);
            outputStream.close();
        }
        workbook.close();
    }

    private void writeDailyHeaderRow(Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(dailyOutputTable.getStation());
        //System.out.println("stationname"+dailyOutputTable.getStation());

        cell = row.createCell(1);
        cell.setCellValue("TAPC");

        cell = row.createCell(2);
        cell.setCellValue("TAPCSignificance");

    }

    private void writeAnnualHeaderRow(Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(annualOutputTable.getStation());

        cell = row.createCell(1);
        cell.setCellValue("TAPC");

        cell = row.createCell(2);
        cell.setCellValue("TAPCSignificance");

        cell = row.createCell(3);
        cell.setCellValue("APP");

        cell = row.createCell(4);
        cell.setCellValue("APPSignificance");

        cell = row.createCell(5);
        cell.setCellValue("Start");

        cell = row.createCell(6);
        cell.setCellValue("StartSignificance");

        cell = row.createCell(7);
        cell.setCellValue("End");

        cell = row.createCell(8);
        cell.setCellValue("EndSignificance");

        cell = row.createCell(9);
        cell.setCellValue("Duration");

        cell = row.createCell(10);
        cell.setCellValue("DurationSignificance");
    }


    private void writeAnnualPollen(int i, Row row) {

        Cell cell = row.createCell(0);
        cell.setCellValue(annualOutputTable.getPollenNames().get(i));

        cell = row.createCell(1);
        cell.setCellValue(annualOutputTable.getTAPC().get(i));

        cell = row.createCell(2);
        cell.setCellValue(annualOutputTable.getTAPCSignificance().get(i));

        cell = row.createCell(3);
        cell.setCellValue(annualOutputTable.getAPP().get(i));

        cell = row.createCell(4);
        cell.setCellValue(annualOutputTable.getAPPSignificance().get(i));

        cell = row.createCell(5);
        cell.setCellValue(annualOutputTable.getStart().get(i));

        cell = row.createCell(6);
        cell.setCellValue(annualOutputTable.getStartSignificance().get(i));

        cell = row.createCell(7);
        cell.setCellValue(annualOutputTable.getEnd().get(i));

        cell = row.createCell(8);
        cell.setCellValue(annualOutputTable.getEndSignificance().get(i));

        cell = row.createCell(9);
        cell.setCellValue(annualOutputTable.getDuration().get(i));

        cell = row.createCell(10);
        cell.setCellValue(annualOutputTable.getDurationSignificance().get(i));

    }
}
