package app.model;

import java.util.List;

public class OutputTable {

    private String station;
    private List<String> pollenNames;
    private List<Long> TAPC;
    private List<Double> TAPCSignificance;
    private List<Long> APP;
    private List<Double> APPSignificance;
    private List<Long> start;
    private List<Double> startSignificance;
    private List<Long> end;
    private List<Double> endSignificance;
    private List<Long> duration;
    private List<Double> durationSignificance;

    public OutputTable(String station) {
        this.station = station;
    }


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<String> getPollenNames() {
        return pollenNames;
    }

    public void setPollenNames(List<String> pollenNames) {
        this.pollenNames = pollenNames;
    }

    public List<Long> getTAPC() {
        return TAPC;
    }

    public void setTAPC(List<Long> TAPC) {
        this.TAPC = TAPC;
    }

    public List<Double> getTAPCSignificance() {
        return TAPCSignificance;
    }

    public void setTAPCSignificance(List<Double> TAPCSignificance) {
        this.TAPCSignificance = TAPCSignificance;
    }

    public List<Long> getAPP() {
        return APP;
    }

    public void setAPP(List<Long> APP) {
        this.APP = APP;
    }

    public List<Double> getAPPSignificance() {
        return APPSignificance;
    }

    public void setAPPSignificance(List<Double> APPSignificance) {
        this.APPSignificance = APPSignificance;
    }

    public List<Long> getStart() {
        return start;
    }

    public void setStart(List<Long> start) {
        this.start = start;
    }

    public List<Long> getEnd() {
        return end;
    }

    public void setEnd(List<Long> end) {
        this.end = end;
    }

    public List<Long> getDuration() {
        return duration;
    }

    public void setDuration(List<Long> duration) {
        this.duration = duration;
    }

    public List<Double> getStartSignificance() {
        return startSignificance;
    }

    public void setStartSignificance(List<Double> startSignificance) {
        this.startSignificance = startSignificance;
    }

    public List<Double> getEndSignificance() {
        return endSignificance;
    }

    public void setEndSignificance(List<Double> endSignificance) {
        this.endSignificance = endSignificance;
    }

    public List<Double> getDurationSignificance() {
        return durationSignificance;
    }

    public void setDurationSignificance(List<Double> durationSignificance) {
        this.durationSignificance = durationSignificance;
    }
}
