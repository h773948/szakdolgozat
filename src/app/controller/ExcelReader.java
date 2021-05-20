package app.controller;

import app.view.Controller;
import app.model.OutputTable;
import app.model.PollenDatas;
import app.model.Station;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReader {

    public static Station ReadExcel(String URL) throws IOException {

        List<String> pollenNames = new ArrayList<>();
        List<PollenDatas> pollenDatas = new ArrayList<PollenDatas>();
        List<PollenDatas> annualDatas = new ArrayList<>();
        List<PollenDatas> dailyDatas = new ArrayList<>();
        //minden évben a csúcsértékeket tartalmazza pollenenként
        List<PollenDatas> annualPeakPollenDatas = new ArrayList<>();

        //List<Double> values = new ArrayList<Double>();
        String excelFilePath = URL;
        String fileName = URL;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Station station = new Station();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int szokoevRowIndex = -1;
        int annualPollenYearIndex = -1;
        //év hányadik napjánál járunk
        int currDailyDayIndex = -1;
        //a jelenlegi ev elso napjanak indexe
        int currFirstDayInd = 0;

        boolean isSzokoev=false;
        int currStopInd = 0;


        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            //currdailydayindex növelés
            //currDailyDayIndex++;


            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                if (cell.getRowIndex() == 0 && cell.getColumnIndex() == 0) {
                    fileName = cell.getStringCellValue();
                }

                //feltölti a years(long), és dates(date) listákat
                if (columnIndex == 0 && cell.getCellType() == CellType.NUMERIC) {



                    //ha már hozzáadtuk az adott évet....
                    if (station.getYears().contains((long) Integer.parseInt(new SimpleDateFormat("yyyy").
                            format(DateUtil.getJavaDate(cell.getNumericCellValue()))))) {
                        //System.out.println("mar bennevan");

                    } else {
                        isSzokoev=false;
                        station.appendYears((long) Integer.parseInt(new SimpleDateFormat("yyyy").
                                format(DateUtil.getJavaDate(cell.getNumericCellValue()))));
                        station.appendYearsXdata((long) Integer.parseInt(new SimpleDateFormat("yyyy").
                                format(DateUtil.getJavaDate(cell.getNumericCellValue()))) - station.getYears().get(0) + 1);

                        annualPollenYearIndex++;
                        currFirstDayInd = cell.getRowIndex();
                        currDailyDayIndex = -1;

                        for (int i = 0; i < annualDatas.size(); i++) {
                            //minden év elején hozzáadom a startInds listához h nincs érték, illetve nulllázom a startArr-t és startArrFlag-t
                            annualDatas.get(i).appendStartInds(-999);
                            annualDatas.get(i).appendStartIndsAll(-999);
                            annualDatas.get(i).clearStartArr();
                            annualDatas.get(i).setStartArrFlag(false);
                            //ugyanez a stop-al

                            annualDatas.get(i).appendStopInds(-999);
                            annualDatas.get(i).appendStopIndsAll(-999);
                            annualDatas.get(i).clearStopArr();
                            annualDatas.get(i).setStopArrFlag(false);


                        }

                    }
                    //szökőév
                    if (Integer.parseInt((new SimpleDateFormat("MM")).format(DateUtil.getJavaDate(cell.getNumericCellValue()))) == 2
                            && Integer.parseInt((new SimpleDateFormat("dd")).format(DateUtil.getJavaDate(cell.getNumericCellValue()))) == 29) {

                        //szokoevek
                        //System.out.println(Integer.parseInt((new SimpleDateFormat("yyyy")).format(DateUtil.getJavaDate((double) cell.getNumericCellValue()))));
                        szokoevRowIndex = cell.getRowIndex();
                        isSzokoev=true;
                    }

                    Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                    if (cell.getDateCellValue() != null && szokoevRowIndex != cell.getRowIndex()) {
                        station.appendDates(javaDate);
                        currDailyDayIndex++;
                    }
                    //ez is jó akár ha a január 1-el indul újra, de az biztosabb, ha új év kerül a years listába akkor induljon újra....
                    /*if (Integer.parseInt((new SimpleDateFormat("MM")).format(DateUtil.getJavaDate(cell.getNumericCellValue()))) == 1
                            && Integer.parseInt((new SimpleDateFormat("dd")).format(DateUtil.getJavaDate(cell.getNumericCellValue()))) == 1) {

                        currDailyDayIndex=0;
                    }*/

                }
                if (cell.getRowIndex() == 0 && cell.getColumnIndex() > 0 && cell.getCellType() == CellType.STRING) {
                    pollenNames.add(cell.getStringCellValue());
                }










                //a febr 29-eket nem is olvasom be
                if (szokoevRowIndex != cell.getRowIndex()) {//System.out.println(columnIndex + " szokoev " + cell.getRowIndex());

                    if (columnIndex > 0) {


                        switch (cell.getCellType()) {
                            case STRING:
                                //ez azért van, mert ha hiányzik egy adat akkor ne adja hozzá a ""-stringet a pollenNevekhez
                                //ettől függetlenül hibás lesz az output
                                if (cell.getRowIndex() == 0) {
                                    pollenDatas.add(new PollenDatas(cell.getStringCellValue()));
                                    annualDatas.add(new PollenDatas(cell.getStringCellValue()));
                                    dailyDatas.add(new PollenDatas(cell.getStringCellValue()));
                                    annualPeakPollenDatas.add(new PollenDatas(cell.getStringCellValue()));
                                } else {
                                    System.err.println("There is a mistake in the excel: row/column:" + (cell.getRowIndex() + 1) + "/" + (cell.getColumnIndex() + 1));
                                    Controller.setErrorMsg("There is a mistake in the excel: row/column:" + (cell.getRowIndex() + 1) + "/" + (cell.getColumnIndex() + 1));
                                    //Controller.errorMsg="There is a mistake in the excel: row/column:" + (cell.getRowIndex() + 1) + "/" + (cell.getColumnIndex() + 1);
                                    throw new IOException();
                                }
                                break;

                            case NUMERIC:
                                try {
                                    pollenDatas.get(columnIndex - 1).appendValues(Math.round(cell.getNumericCellValue()));
                                    annualDatas.get(columnIndex - 1).incrementValues(annualPollenYearIndex, Math.round(cell.getNumericCellValue()));
                                    annualPeakPollenDatas.get(columnIndex - 1).setPeakValue(annualPollenYearIndex, Math.round(cell.getNumericCellValue()));


                                    //daily append
                                    dailyDatas.get(columnIndex - 1).incrementValues(currDailyDayIndex, Math.round(cell.getNumericCellValue()));
                                    dailyDatas.get(columnIndex - 1).appendDailyValues(currDailyDayIndex,Math.round(cell.getNumericCellValue()));

                                    //startArr...
                                    if (!annualDatas.get(columnIndex - 1).isStartArrFlag()) {


                                        //if (cell.getNumericCellValue() >= 0) {
                                        if (cell.getNumericCellValue() > 0) {
                                            annualDatas.get(columnIndex - 1).appendStartArr(Math.round(cell.getNumericCellValue()));
                                            //megállási feltétele a startarr-nek
                                            if (annualDatas.get(columnIndex - 1).getStartArr().size() == 5) {
                                                annualDatas.get(columnIndex - 1).setStartArrFlag(true);
                                                //az elsőnek az indexét szeretném elmenteni, ezért vonok ki 4-et... hint: 5. elemből 4-et kell kivonni h az 1. indexet kapjuk
                                                annualDatas.get(columnIndex - 1).appendStartInds(cell.getRowIndex() - 4 - currFirstDayInd);
                                                annualDatas.get(columnIndex - 1).appendStartIndsAll(cell.getRowIndex() - 4);
                                                //////////////////////////////////////////////valami;rt corylus a masodik evben egy indexszel odebb van....
                                            }
                                        } else {
                                            annualDatas.get(columnIndex - 1).clearStartArr();
                                        }

                                    }
                                    //stopArr....
                                    annualDatas.get(columnIndex - 1).takeIntoStopTmpArr(Math.round(cell.getNumericCellValue()));
                                    if (annualDatas.get(columnIndex - 1).getStopTmpArr().size() == 5) {
                                            //currStopInd=cell.getRowIndex()-4-currFirstDayInd;
                                            //int currStopRowInd=cell.getRowIndex()-4-currFirstDayInd;
                                            //annualDatas.get(columnIndex-1).setStopArr(annualDatas.get(columnIndex-1).getStopTmpArr());

                                        if(isSzokoev) {
                                            annualDatas.get(columnIndex - 1).appendStopInds((cell.getRowIndex() - currFirstDayInd) - 1);
                                            annualDatas.get(columnIndex - 1).appendStopIndsAll(cell.getRowIndex());

                                        }else {annualDatas.get(columnIndex - 1).appendStopInds(cell.getRowIndex() - currFirstDayInd);
                                            annualDatas.get(columnIndex - 1).appendStopIndsAll(cell.getRowIndex());

                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.err.println("There is a mistake in the excel around: row/column:" + cell.getRowIndex() + "/" + (cell.getColumnIndex() + 1));
                                    System.err.println("If not, check for duplicated dates(rows).");
                                    //Controller.errorMsg="There is a mistake in the excel: row/column:" + (cell.getRowIndex()) + "/" + (cell.getColumnIndex() + 1)+"\r\n"
                                    //        +"If not, check for duplicated dates(rows.)";
                                    Controller.setErrorMsg("There is a mistake in the excel: row/column:" + (cell.getRowIndex()) + "/" + (cell.getColumnIndex() + 1)+"\r\n"
                                            +"If not, check for duplicated dates(rows.)");

                                    throw new IOException();
                                }


                        }
                    }
                }
            }
        }
        //ha a beolvasás lefut, egyből érdemes bezárni az inputot
        workbook.close();
        inputStream.close();

        OutputTable annualOutput = new OutputTable(station.getName());
        OutputTable dailyOutput = new OutputTable(station.getName());



        //output table adattagjai
        List<Long> TAPC = new ArrayList<>();
        List<Double> TAPCSignificance = new ArrayList<>();
        List<Long> APP = new ArrayList<>();
        List<Double> APPSignificance = new ArrayList<>();
        List<Long> start = new ArrayList<>();
        List<Double> startSignificance = new ArrayList<>();
        List<Long> end = new ArrayList<>();
        List<Double> endSignificance = new ArrayList<>();
        List<Long> duration = new ArrayList<>();
        List<Double> durationSignificance = new ArrayList<>();
        PollenDatas.calcDurInds(annualDatas);
        for (int i = 0; i < annualDatas.size(); i++) {
            //tapc
            LinearRegression tapcRegression = new LinearRegression(station.getYearsXdata(), annualDatas.get(i).getValues());
            TAPC.add(Math.round(tapcRegression.predict10YearDiff()));
            //TAPCSignificance
            TAPCSignificance.add(RunMK.getSignificance(annualDatas.get(i).getValues()));
            //app
            LinearRegression appRegression = new LinearRegression(station.getYearsXdata(), annualPeakPollenDatas.get(i).getValues());
            APP.add(Math.round(appRegression.predict10YearDiff()));
            //APPSignificance
            APPSignificance.add(RunMK.getSignificance(annualPeakPollenDatas.get(i).getValues()));

            //start
            LinearRegression startRegression = new LinearRegression(station.getYearsXdata(), annualDatas.get(i).getStartInds());
            start.add(Math.round(startRegression.predict10YearDiff()));
            //startSignificance
            startSignificance.add(RunMK.getSignificance(annualDatas.get(i).getStartInds()));

            //end
            LinearRegression endRegression = new LinearRegression(station.getYearsXdata(), annualDatas.get(i).getStopInds());
            end.add(Math.round(endRegression.predict10YearDiff()));
            //endSignificance
            endSignificance.add(RunMK.getSignificance(annualDatas.get(i).getStopInds()));

            //duration
            LinearRegression durationRegression = new LinearRegression(station.getYearsXdata(), annualDatas.get(i).getDurInds());
            duration.add(Math.round(durationRegression.predict10YearDiff()));
            //durationSignificance
            durationSignificance.add(RunMK.getSignificance(annualDatas.get(i).getDurInds()));
        }
        station.setName(fileName);
        station.setPollenDatas(pollenDatas);


        //annua Output table
        annualOutput.setStation(station.getName());
        annualOutput.setPollenNames(pollenNames);
        annualOutput.setTAPC(TAPC);
        annualOutput.setTAPCSignificance(TAPCSignificance);
        annualOutput.setAPP(APP);
        annualOutput.setAPPSignificance(APPSignificance);
        annualOutput.setStart(start);
        annualOutput.setStartSignificance(startSignificance);
        annualOutput.setEnd(end);
        annualOutput.setEndSignificance(endSignificance);
        annualOutput.setDuration(duration);
        annualOutput.setDurationSignificance(durationSignificance);



        //leghosszabb "ideális mesterséges" pollenszezon
        List<Integer> idealStarts = new ArrayList<>();
        List<Integer> idealEnds = new ArrayList<>();
        for (int i = 0; i < dailyDatas.size(); i++) {
            long idealStart = 364;
            long idealEnd = 0;
            for (int j = 0; j < annualDatas.get(i).getStartInds().size(); j++) {
                if (idealStart > annualDatas.get(i).getStartInds().get(j) && annualDatas.get(i).getStartInds().get(j) >= 0) {
                    idealStart = annualDatas.get(i).getStartInds().get(j);
                }
                if (idealEnd < annualDatas.get(i).getStopInds().get(j)) {
                    idealEnd = annualDatas.get(i).getStopInds().get(j);
                }

            }
            idealStarts.add((int)idealStart);
            idealEnds.add((int)idealEnd);
        }

        //output daily table
        List<Double> TAPCSignificanceOfDailyTrend = new ArrayList<>();
        List<Long> TAPCOfDailyTrend = new ArrayList<>();




        for (int i = 0; i < dailyDatas.size(); i++) {


            //tulajdonképpen az évek száma vagy napok, vagy adatok száma
            List<Long> xdata = new ArrayList<>();
            //adatok
            List<Long> ydata = new ArrayList<>();
            /*for(int j=0 ; j<dailyDatas.get(i).getValues().size() ; j++){
                xdata.add((long)(j+1));
            }*/
            for (int j = 0; j < ((idealEnds.get(i) - idealStarts.get(i)) + 1); j++) {
                xdata.add((long) (j + 1));
            }
            for (int j = (int) (long) idealStarts.get(i); j < (idealEnds.get(i) + 1); j++) {
                ydata.add(dailyDatas.get(i).getValues().get(j));
            }
            //ydata pedig a values volt korábban, de mivel csak a pollenszezont nézzük, így külön ki kell számolni h mely adatok esnek bele


            LinearRegression l = new LinearRegression(xdata, ydata);



            //ideális pollenszezon duration
            int mestersegesDuration = 1+idealEnds.get(i)-idealStarts.get(i);

            //TAPCOfDailyTrend.add(Math.round(l.predict10YearDiff()));
            TAPCOfDailyTrend.add(Math.round(mestersegesDuration*l.getC_lineSlope()));


            //TAPCSignificanceOfDaily
            TAPCSignificanceOfDailyTrend.add(RunMK.getSignificance(ydata));

        }



        dailyOutput.setStation(station.getName());
        dailyOutput.setPollenNames(pollenNames);
        dailyOutput.setTAPC(TAPCOfDailyTrend);
        dailyOutput.setTAPCSignificance(TAPCSignificanceOfDailyTrend);

        CreateOutput output = new CreateOutput(annualOutput, dailyOutput);

        //output.createAnnualExcel(fileName+"_annual.xlsx");
        //output.createDailyExcel(fileName+"_daily.xlsx");
        output.createExcels(fileName);



        return station;
    }

}
