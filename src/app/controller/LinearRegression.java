package app.controller;

import java.util.ArrayList;
import java.util.List;


public class LinearRegression {

    //eredetileg a listák <Float> voltak
//emiatt sok helyen a castolásokat meg kell nézni
    private List<Long> XData;
    private List<Long> YData;
    private Float result1;
    private Float result2;
    private Double c_lineSlope;
    private Float c_xmean;
    private Float c_ymean;

    public LinearRegression (List<Long> xData, List<Long> yData) {
        List<Long> xDataTMP = new ArrayList<>();
        List<Long> yDataTMP = new ArrayList<>();

        for(int i=0; i<xData.size();i++){
            //figyelembeveszem a 0-kat mert úgy stimmelnek az előjelek a szignifikancia és regressziós egyenes meredeksége között
            if (yData.get(i)>=0){
                xDataTMP.add(xData.get(i));
                yDataTMP.add(yData.get(i));
            }
        }
        this.XData = xDataTMP;
        this.YData = yDataTMP;
       // System.out.println("xdata: " + XData);
       // System.out.println("ydata: " + YData);

        c_xmean = getXMean(XData) ;
        c_ymean = getYMean( YData ) ;
        c_lineSlope= getLineSlope() ;
        if (XData.size()==0 && YData.size()==0){
            c_lineSlope=0.0;
            c_xmean=0f;
            c_ymean=0f;
        }
    }

    public double predict10YearDiff(){
        return this.predictValue(10)-this.predictValue(1);
    }

    public Double predictValue ( int inputValue ) {
        Double YIntercept = getYIntercept(c_lineSlope) ;
        Double prediction = ( c_lineSlope * inputValue ) + YIntercept ;
        return prediction ;
    }

    /////////////////korrelációs együttható
    public double getCorrelationConstant(){
        double numerator=0;
        double denominator=0;
        double denominator1=0;
        double denominator2=0;

        for (int i=0;i<XData.size();i++){
            numerator += (XData.get(i)-c_xmean)*(YData.get(i)-c_ymean);
            denominator1+=(XData.get(i)-c_xmean)*(XData.get(i)-c_xmean);
            denominator2+=(YData.get(i)-c_ymean)*(YData.get(i)-c_ymean);
            denominator = Math.sqrt(denominator1*denominator2);
        }
        if (denominator==0)return 0;
        return (numerator/denominator);

    }

    public double getVariance(){
        double numerator=0;
        double denominator=0;
        double tmpy=0;
        for (int i=0;i<YData.size();i++){
            tmpy+=(YData.get(i)-c_ymean)*(YData.get(i)-c_ymean);
        }
        numerator=Math.sqrt(tmpy/YData.size());

        double tmpx=0;
        for (int i=0;i<XData.size();i++){
            tmpx+=(XData.get(i)-c_xmean)*(XData.get(i)-c_xmean);
        }
        denominator=Math.sqrt(tmpx/XData.size());

        if (denominator==0) return 0;
        return (numerator/denominator);

    }


    public double getLineSlope () {
        return getCorrelationConstant()* getVariance();
    }

    public double getYIntercept (Double lineSlope) {
        return c_ymean - (lineSlope * c_xmean);
    }

    public Float getXMean (List<Long> Xdata) {
        result1 = 0.0f ;
        for (Integer i = 0; i < Xdata.size(); i++) {
            result1 = result1 + Xdata.get(i);
        }
        c_xmean=result1 / Xdata.size();
        return result1 / Xdata.size();
    }

    public Float getYMean (List<Long> Ydata) {
        result2 = 0.0f ;
        for (Integer i = 0; i < Ydata.size(); i++) {
            result2 = result2 + Ydata.get(i);
        }
        c_ymean=result2 / Ydata.size();
        return result2 / Ydata.size();
    }

    public Double getC_lineSlope() {
        return c_lineSlope;
    }
}
