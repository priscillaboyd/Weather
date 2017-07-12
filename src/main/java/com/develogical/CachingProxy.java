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

        //removing oldest entry if limit has reached
        if(cacheTemperature.size() == cacheMaxSize) {
            removeOldestEntry(cacheTemperature);
        }

        int temperature = forecaster.getTemperature(region, day);
        cacheTemperature.put(report, temperature);
        return temperature;
    }

    // remove oldest entry from cache
    public void removeOldestEntry(Map cache){
        Object oldestEntry = cache.get(cacheMaxSize);
        cache.remove(oldestEntry);
    }

    // get max cache size
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }

    // get cache size
    public int cacheSize(Map cache) {
        return cache.size();
    }

}
