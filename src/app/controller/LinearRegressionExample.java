package app.controller;

import java.util.ArrayList;
import java.util.List;


//https://equipintelligence.medium.com/java-algorithms-the-linear-regression-classifier-41a31f1c9018

public class LinearRegressionExample {

//eredetileg a listák <Float> voltak
//emiatt sok helyen a castolásokat meg kell nézni
        private List<Long> XData;
        private List<Long> YData;
        private Float result1;
        private Float result2;

        public LinearRegressionExample (List<Long> xData, List<Long> yData) {
            List<Long> xDataTMP = new ArrayList<>();
            List<Long> yDataTMP = new ArrayList<>();
            if(xData.size()!= yData.size()){
                System.err.println("xdata!=ydata RIP");
            }
            for(int i=0; i<xData.size();i++){
                if (yData.get(i)>0){
                    xDataTMP.add(xData.get(i));
                    yDataTMP.add(yData.get(i));
                }

            }
            /*System.out.println("xData:"+ xData);
            System.out.println("xData:"+ yData);
            System.out.println("xDataTMP:"+ xDataTMP);
            System.out.println("yDataTMP:"+ yDataTMP);*/



            this.XData = xDataTMP;
            this.YData = yDataTMP;
        }

        public double predict10YearDiff(){
            return this.predictValue(10)-this.predictValue(1);
        }

        public Float predictValue ( int inputValue ) {
            Long X1 = XData.get( 0 ) ;
            System.out.println("Xdata(0)"+ XData.get( 0 ));
            Long Y1 = YData.get( 0 ) ;
            System.out.println("Ydata(0)"+ YData.get( 0 ));

            Float Xmean = getXMean(XData) ;
            Float Ymean = getYMean( YData ) ;
            Float lineSlope = getLineSlope( Xmean , Ymean , X1 , Y1 ) ;
            System.out.println("lineslope"+lineSlope);
            Float YIntercept = getYIntercept( Xmean , Ymean , lineSlope ) ;

            ////////////
            System.out.println("yintercept"+YIntercept);
            Float prediction = ( lineSlope * inputValue ) + YIntercept ;
            return prediction ;
        }

        public Float getLineSlope (Float Xmean, Float Ymean, Long X1, Long Y1) {
            float num1 = X1 - Xmean;
            float num2 = Y1 - Ymean;
            float denom = (X1 - Xmean) * (X1 - Xmean);
            return (num1 * num2) / denom;
        }

        public float getYIntercept (Float Xmean, Float Ymean, Float lineSlope) {
            return Ymean - (lineSlope * Xmean);
        }

        public Float getXMean (List<Long> Xdata) {
            result1 = 0.0f ;
            for (Integer i = 0; i < Xdata.size(); i++) {
                result1 = result1 + Xdata.get(i);
            }
            return result1 / Xdata.size();
        }

        public Float getYMean (List<Long> Ydata) {
            result2 = 0.0f ;
            for (Integer i = 0; i < Ydata.size(); i++) {
                result2 = result2 + Ydata.get(i);
            }
            return result2 / Ydata.size();
        }



}
