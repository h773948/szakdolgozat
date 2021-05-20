package app.model;

import java.util.ArrayList;
import java.util.List;

public class DailyDataOfOneDay {
    List<Long> values=new ArrayList<Long>();

    public DailyDataOfOneDay(List<Long> values) {
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
    public void appendValues(Long l){
        this.values.add(l);
    }
}
