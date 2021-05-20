package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Station {
    private String name;
    private List<PollenDatas> pollenDatas;
    private List<PollenDatas> annualDatas;
    private List<Long> years = new ArrayList<>();
    //a lineáris regresszióhoz kell az évek száma: 1,2,3.....n
    private List<Long> yearsXdata = new ArrayList<>();
    private List<Date> dates = new ArrayList<>();

    public Station(String name, List<PollenDatas> pollenDatas) {
        this.name = name;
        this.pollenDatas = pollenDatas;
    }

    public Station(String name) {
        this.name = name;
    }

    public Station() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PollenDatas> getPollenDatas() {
        return pollenDatas;
    }

    public void setPollenDatas(List<PollenDatas> pollenDatas) {
        this.pollenDatas = pollenDatas;
    }

    public List<PollenDatas> getAnnualDatas() {
        return annualDatas;
    }

    public void setAnnualDatas(List<PollenDatas> annualDatas) {
        this.annualDatas = annualDatas;
    }

    public List<Long> getYears() {
        return years;
    }

    public void setYears(List<Long> years) {
        this.years = years;
    }

    public void appendYears(Long y) {
        this.years.add(y);
    }

    public List<Long> getYearsXdata() {
        return yearsXdata;
    }

    public void appendYearsXdata(Long y) {
        this.yearsXdata.add(y);
    }


    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public void appendDates(Date d) {
        this.dates.add(d);
    }
}
