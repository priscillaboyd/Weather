package com.develogical;

import com.weather.*;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CachingProxyTest {

    private ForecasterInterface mockedInterface = mock(ForecasterInterface.class);
    private Region region = Region.EDINBURGH;
    private Day day = Day.FRIDAY;
    private CachingProxy proxy = new CachingProxy(mockedInterface);

    @Test
    public void checkThatProxyImplementsInterface(){
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        assertEquals(proxy.getOutlook(region, day), "snow");
        when(mockedInterface.getTemperature(region, day)).thenReturn(30);
        assertEquals(proxy.getTemperature(region, day), 30);
    }

    @Test
    public void checkProxyCachingWorksForOutlook(){
        long startTime;
        long endTime;

        //First run - should take longer
        startTime = System.nanoTime();
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        proxy.getOutlook(region, day);
        endTime = System.nanoTime();
        long firstRunDuration = endTime - startTime;

        //Second run - should be quicker (using cache)
        startTime = System.nanoTime();
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        proxy.getOutlook(region, day);
        endTime = System.nanoTime();
        long secondRunDuration = endTime - startTime;

        //Check the first run takes longer than the second (cached)
        assertThat(firstRunDuration > secondRunDuration, is(true));
    }

}
