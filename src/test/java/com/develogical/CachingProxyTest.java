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

public class CachingProxyTest {

    @Mock
    ForecasterInterface forecasterInterface = new ForecasterInterface() {
        @Override
        public String getOutlook(Region region, Day day) {
            return null;
        }

        @Override
        public int getTemperature(Region region, Day day) {
            return 0;
        }
    };

    @Test
    public void checkThatProxyMapHasResult(){
        CachingProxy cachingProxy =  new CachingProxy();
        Region region = Region.EDINBURGH;
        Day day = Day.TUESDAY;
        cachingProxy.getOutlook(region, day);
        String key = region + " " + day;
        assertTrue(cachingProxy.cache.containsKey(key));
    }

}
