package com.develogical;

import com.weather.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachingProxy implements ForecasterInterface {

    //create a map to store results
    Map<String,String> cacheOutlook = new LinkedHashMap();
    Map<String,Integer> cacheTemperature = new LinkedHashMap();
    private ForecasterInterface forecaster = null;
    int cacheMaxSize = 5;

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
        if (cacheTemperature.containsKey(report)){
            return cacheTemperature.get(report);
        }

        //add it to the cache
        if(cacheTemperature.size() == cacheMaxSize) {
            //if adding to cache exceeds limit, get rid of last entry
            cacheTemperature.remove(cacheTemperature.get(cacheMaxSize));
            System.out.println(cacheTemperature);
        }

        int temperature = forecaster.getTemperature(region, day);
        cacheTemperature.put(report, temperature);
        return temperature;

    }

    // get max cache size
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }

    // enforce cache limit
    private boolean exceedsCacheLimit(Map cache){
        // if adding another one exceeds limit, return true
        if((cache.size()+1) > cacheMaxSize)
            return true;

        // otherwise it doesn't exceed limit
        return false;
    }

    // get cache size
    public int cacheSize(Map cache) {
        return cache.size();
    }

}
