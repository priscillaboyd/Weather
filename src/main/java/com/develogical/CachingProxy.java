package com.develogical;

import com.weather.*;

import java.util.HashMap;
import java.util.Map;

public class CachingProxy implements ForecasterInterface {

    //create a map to store results
    private Map<String,String> cacheOutlook = new HashMap();
    private Map<String,Integer> cacheTemperature = new HashMap();
    private ForecasterInterface forecaster = null;
    private int cacheMaxSize = 5; //assume same for both

    CachingProxy(ForecasterInterface forecaster, int cacheMaxSize){
        this.forecaster = forecaster;
        this.cacheMaxSize = cacheMaxSize;
    }

    @Override
    public String getOutlook(Region region, Day day) {
        String report = region + " " + day;

        //Use what's in cache if already exists
        if (cacheOutlook.containsKey(report))
            return cacheOutlook.get(report);

        //removing oldest entry if limit has reached
        if(cacheTemperature.size() == cacheMaxSize) {
            removeOldestEntry(cacheTemperature);
        }

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

    // remove oldest entry from cache to allow new entry to be entered
    private void removeOldestEntry(Map cache){
        Object oldestEntry = cache.get(cacheMaxSize);
        cache.remove(oldestEntry);
    }

    // reset cache by removing all entries that have timestamp >1h
    public boolean resetCache() {
        return true;
    }
}
