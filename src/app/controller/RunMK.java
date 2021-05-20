package app.controller;

import java.util.*;
import java.util.stream.Collector;

public class RunMK {

    public static int mkstat(List<Long> numList) {
        int invC = 0;
        int count = 0;

        for (int i = 0; i < numList.size() - 1; i++) {
            for (int j = i+1; j < numList.size(); j++) {
                //ha nincs érték akkor figyemen kívül hagyja az adatot
                if (numList.get(i) > -999 && numList.get(j) > -999) {
                    if (numList.get(i) > numList.get(j)) {
                        invC--;
                    } else if (numList.get(i) < numList.get(j)) {
                        invC++;
                    }
                    count++;
                }
            }
        }
        return invC;
    }

    public static double getSignificance(List<Long> numList) {
        //mann kendall statisztika, invC
        int S = mkstat(numList);
        double variance;
        long varianceSum = 0;
        double Zstat = -999;
        //double Zstat=0;

        //az ugyanolyan értékeket és mennyiségüket tárolja
        Map<Long, Long> countOfDuplicatesMap = new HashMap<>();
        for (Long value : numList) {
            if (countOfDuplicatesMap.containsKey(value)) {
                countOfDuplicatesMap.put((long) value, countOfDuplicatesMap.get(value) + 1);
            } else {
                countOfDuplicatesMap.put((long) value, 1l);
            }
        }
        //a summa alatti rész csak akkor számítódjon ki aha a countOfDuplivatesMap.value >1
        for (Object val : countOfDuplicatesMap.values()) {
            long tmp = (long) val;
            if (tmp > 1) {
                varianceSum += tmp * (tmp - 1) * (2 * tmp + 5);
            }
        }
        long n = numList.size();
        long firstPart = n * (n - 1) * (2 * n + 5);
        variance = Math.sqrt((firstPart - varianceSum) / 18.0);
        if (S > 0) {
            Zstat = ((S - 1) / variance);
        } else if (S < 0) {
            Zstat = ((S + 1) / variance);
        } else if (S == 0) {
            Zstat = 0.0;
        }

        if (Math.abs(Zstat) > 3.29) {
            //System.out.println(Zstat);
            //System.out.println("99.9% significant");
            return 99.9;
        } else if (Math.abs(Zstat) > 2.58) {
            //System.out.println(Zstat);
            //System.out.println("99% significant");
            return 99.0;
        } else if (Math.abs(Zstat) > 1.96) {
            //System.out.println(Zstat);
            //System.out.println("95% significant");
            return 95.0;
        } else //System.out.println("not significant");
        //System.out.println(Zstat);


        return 0;
    }



}
