package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CachingProxyTest {

    ForecasterInterface forecaster = mock(ForecasterInterface.class);

    @Test
    public void checkThatProxyMapHasResultOfOutlook(){
        CachingProxy cachingProxy =  new CachingProxy(forecaster);
        Region region = Region.EDINBURGH;
        Day day = Day.TUESDAY;
        cachingProxy.getOutlook(region, day);
        String key = region + " " + day;
        assertTrue(cachingProxy.cacheOutlook.containsKey(key));
    }

//    @Test
//    public void checkThatProxyMapHasResultOfTemp(){
//        CachingProxy cachingProxy =  new CachingProxy();
//        Region region = Region.EDINBURGH;
//        Day day = Day.TUESDAY;
//        cachingProxy.getTemperature(region, day);
//        String key = region + " " + day;
//        assertTrue(cachingProxy.cacheTemperature.containsKey(key));
//    }


}
