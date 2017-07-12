package com.develogical;

import com.weather.*;

import java.util.HashMap;
import java.util.Map;

public class CachingProxy implements ForecasterInterface {

    //create a map to store results
    private Map<String,String> cacheOutlook = new HashMap();
    private Map<String,Integer> cacheTemperature = new HashMap();

    private ForecasterInterface forecaster = null;

    CachingProxy(ForecasterInterface forecaster){
        this.forecaster = forecaster;
    }

    @Override
    public String getOutlook(Region region, Day day) {
        //check if outlook is in cache
        String report = region + " " + day;
        if (cacheOutlook.containsKey(report))
            return cacheOutlook.get(report);

        String outlook = forecaster.getOutlook(region, day);
        cacheOutlook.put(report, outlook);
        return outlook;
    }

    @Override
    public int getTemperature(Region region, Day day) {
        //TODO implement cache

        String report = region + " " + day;
        int temperature = forecaster.getTemperature(region, day);
        cacheTemperature.put(report, temperature);
        return temperature;
    }

//    public static void main(String[] args) {
//        ForecasterAdapter forecasterAdapter = new ForecasterAdapter();
//        CachingProxy cachingProxy = new CachingProxy(forecasterAdapter);
//        System.out.println(cachingProxy.getOutlook(Region.LONDON, Day.MONDAY));
//
//        System.out.println(cachingProxy.getOutlook(Region.LONDON, Day.MONDAY));
//    }

}
