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
        String report = region + " " + day;

        //Use what's in cache if already exists
        if (cacheOutlook.containsKey(report))
            return cacheOutlook.get(report);

        //If it doesn't, do it and add to the cache
        String outlook = forecaster.getOutlook(region, day);
        cacheOutlook.put(report, outlook);
        return outlook;
    }

    @Override
    public int getTemperature(Region region, Day day) {
        String report = region + " " + day;

        //Use what's in cache if already exists
        if (cacheTemperature.containsKey(report))
            return cacheTemperature.get(report);

        //If it doesn't, do it and add to the cache
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
