package app.model;

import app.view.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PollenDatas {

    private String name;
    private List<Long> values = new ArrayList<Long>();
    private List<DailyDataOfOneDay> dailyValues = new ArrayList<DailyDataOfOneDay>();

    //start,end duration
    private List<Long> startArr = new ArrayList<>();
    private List<Long> stopArr = new ArrayList<>();
    private List<Long> stopTmpArr = new ArrayList<>();
    private List<Long> startInds = new ArrayList<>();
    private List<Long> durInds = new ArrayList<>();

    //nem indul előröl évente a számozás, hanem az egész dokumentumon végigmegy az indexelés
    private List<Integer> startIndsAll = new ArrayList<>();
    private List<Long> stopInds = new ArrayList<>();
    private List<Integer> stopIndsAll = new ArrayList<>();
    //megvan-e már töltve a a start tömb
    private boolean startArrFlag = false;
    private boolean stopArrFlag = false;

    public PollenDatas(String name, List<Long> values) {
        this.name = name;
        this.values = values;
    }

    public PollenDatas(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Long> getValues() {
        return values;
    }

    public void appendValues(Long d) {
        values.add(d);
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    public List<DailyDataOfOneDay> getDailyValues() {
        return dailyValues;
    }

    public void setDailyValues(List<DailyDataOfOneDay> dailyValues) {
        this.dailyValues = dailyValues;
    }
    public void appendDailyValues(int dayIndex, Long l) {

        try {
            dailyValues.get(dayIndex).appendValues(l);

        }catch (IndexOutOfBoundsException e){
            dailyValues.add(new DailyDataOfOneDay(new ArrayList<>()));
            dailyValues.get(dayIndex).appendValues(l);
        }
    }

    public void incrementValues(int ind, Long d) throws IOException {

        Long tmp = 0l;
        try {
            tmp = this.values.get(ind);

        } catch (IndexOutOfBoundsException e) {
            //System.out.println("elkaptam");
            // this.values.add(tmp);
            this.values.add(-999l);

        }
        if (d >= 0) {
            if (this.values.get(ind) < 0) {
                this.values.set(ind, d);
            } else
                this.values.set(ind, tmp + d);
        }

        //dailyDatasnál ha túlindexel
        if(ind==365){
            System.err.println("túl sok index!!!!");
            Controller.setErrorMsg("DailyDatas too much index");
            throw new IOException();
        }

    }

    public void setPeakValue(int ind, Long l) {
        Long tmp = 0l;

        try {
            tmp = this.values.get(ind);

        } catch (IndexOutOfBoundsException e) {
            //System.out.println("uj maximum");
            this.values.add(tmp);

        }
        if (l > tmp) {
            this.values.set(ind, l);
        }

    }


    // Start,end, duration


    public List<Long> getStartArr() {
        return startArr;
    }

    public void setStartArr(List<Long> startArr) {
        this.startArr = startArr;
    }

    public void appendStartArr(Long l) {
        this.startArr.add(l);
    }

    public void clearStartArr() {
        this.startArr.clear();
    }

    public boolean isStartArrFlag() {
        return startArrFlag;
    }

    public void setStartArrFlag(boolean startArrFlag) {
        this.startArrFlag = startArrFlag;
    }

    public List<Long> getStartInds() {
        return startInds;
    }

    public void setStartInds(List<Long> startInds) {
        this.startInds = startInds;
    }

    public void appendStartInds(long i) {

        //van érték a bemeneten
        if (i >= 0) {
            //még nincs értéke az utolsó elemnek a listában(-999)
            if (this.startInds.get(startInds.size() - 1) < 0) {
                this.startInds.set(startInds.size() - 1, i);
            } else {
                this.startInds.set(startInds.size() - 1, this.startInds.get(startInds.size() - 1) + i);
            }
        } else {
            this.startInds.add(i);
        }
    }

    public void appendStartIndsAll(int i) {
        if (i >= 0) {
            //még nincs értéke az utolsó elemnek a listában(-999)
            if (this.startIndsAll.get(startIndsAll.size() - 1) < 0) {
                this.startIndsAll.set(startIndsAll.size() - 1, i);
            } else {
                this.startIndsAll.set(startIndsAll.size() - 1, this.startIndsAll.get(startIndsAll.size() - 1) + i);
            }
        } else {
            this.startIndsAll.add(i);
        }
    }

    public List<Integer> getStartIndsAll() {
        return startIndsAll;
    }

    public void setStartIndsAll(List<Integer> startIndsAll) {
        this.startIndsAll = startIndsAll;
    }

    public List<Long> getStopInds() {
        return stopInds;
    }

    public void setStopInds(List<Long> stopInds) {
        this.stopInds = stopInds;
    }

    public List<Integer> getStopIndsAll() {
        return stopIndsAll;
    }

    public void setStopIndsAll(List<Integer> stopIndsAll) {
        this.stopIndsAll = stopIndsAll;
    }

    public void appendStopInds(long i) {
        if (i==365){
            System.out.println("365");
        }
          //van érték a bemeneten
        if (i >= 0) {
            //még nincs értéke az utolsó elemnek a listában(-999)
            this.stopInds.set(stopInds.size() - 1, i);
        } else if (i == -999) {
            this.stopInds.add(i);
        }
    }

    public void appendStopIndsAll(int i) {
        //van érték a bemeneten
        if (i >= 0) {
            //még nincs értéke az utolsó elemnek a listában(-999)
            this.stopIndsAll.set(stopIndsAll.size() - 1, i);
        } else {
            this.stopIndsAll.add(i);
        }
    }

    public boolean isStopArrFlag() {
        return stopArrFlag;
    }

    public void setStopArrFlag(boolean stopArrFlag) {
        this.stopArrFlag = stopArrFlag;
    }

    public List<Long> getStopArr() {
        return stopArr;
    }

    public void setStopArr(List<Long> stopArr) {
        this.stopArr = stopArr;
    }

    public void clearStopArr() {
        this.stopArr.clear();
    }

    public void clearStopTmpArr() {
        this.stopTmpArr.clear();
    }

    public List<Long> getStopTmpArr() {
        return stopTmpArr;
    }

    public void setStopTmpArr(List<Long> stopTmpArr) {
        this.stopTmpArr = stopTmpArr;
    }

    public void takeIntoStopTmpArr(Long l) {
        if (l > 0) {
            if (this.stopTmpArr.size() == 5) {
                this.stopTmpArr.remove(0);
            }
            this.stopTmpArr.add(l);
            setStopArr(new ArrayList<>(this.stopTmpArr));
        } else clearStopTmpArr();
    }


    public List<Long> getDurInds() {
        return durInds;
    }

    public void setDurInds(List<Long> durInds) {
        this.durInds = durInds;
    }

    public void appendDurInds(long num) {
        this.durInds.add(num);
    }

    public static void calcDurInds(List<PollenDatas> p) {
        for (int i = 0; i < p.size(); i++) {
            for (int j = 0; j < p.get(i).getStartInds().size(); j++) {
                p.get(i).durInds.add((p.get(i).getStopInds().get(j) - p.get(i).getStartInds().get(j))+1);
            }
        }
    }
}



