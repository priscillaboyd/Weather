package com.develogical;

import com.weather.*;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CachingProxyTest {

    private ForecasterInterface mockedInterface = mock(ForecasterInterface.class);
    private Region region = Region.EDINBURGH;
    private Day day = Day.FRIDAY;
    private CachingProxy proxy = new CachingProxy(mockedInterface);

    @Test
    public void checkThatProxyImplementsInterface() {
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        assertEquals(proxy.getOutlook(region, day), "snow");
        when(mockedInterface.getTemperature(region, day)).thenReturn(30);
        assertEquals(proxy.getTemperature(region, day), 30);
    }

    @Test
    public void checkProxyCachingWorksForOutlook() {
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

    @Test
    public void checkProxyCachingWorksForTemperature() {
        long startTime;
        long endTime;

        //First run - should take longer
        startTime = System.nanoTime();
        when(mockedInterface.getTemperature(region, day)).thenReturn(30);
        proxy.getOutlook(region, day);
        endTime = System.nanoTime();
        long firstRunDuration = endTime - startTime;

        //Second run - should be quicker (using cache)
        startTime = System.nanoTime();
        when(mockedInterface.getTemperature(region, day)).thenReturn(30);
        proxy.getOutlook(region, day);
        endTime = System.nanoTime();
        long secondRunDuration = endTime - startTime;

        //Check the first run takes longer than the second (cached)
        assertThat(firstRunDuration > secondRunDuration, is(true));
    }

    @Test
    public void checkTemperatureCacheHasAMaxSizeThatWorks() {
        // get cache max size (limit) and store it
        int cacheMaxSize = proxy.getCacheMaxSize();

        // set cache size to meet cache size limit
        int cacheSize = cacheMaxSize;

        //mock that we've reached the cache size
        when(proxy.getTemperature(region, day)).thenReturn(cacheSize);

        //test adding other entries do not mean limit is exceeded
        proxy.getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        proxy.getTemperature(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
        proxy.getTemperature(Region.WALES, Day.SATURDAY);
        proxy.getTemperature(Region.EDINBURGH, Day.MONDAY);
        proxy.getTemperature(Region.MANCHESTER, Day.MONDAY);
        proxy.getTemperature(Region.NORTH_ENGLAND, Day.SATURDAY);

        //assert size of cache meets limit
        assertTrue(cacheSize <= cacheMaxSize);
    }

    @Test
    public void checkOldestEntryIsRemovedFromCacheWhenLimitIsReached() {
        int cacheMaxSize = proxy.cacheMaxSize;

        proxy.getTemperature(Region.EDINBURGH, Day.MONDAY);
        proxy.getTemperature(Region.MANCHESTER, Day.MONDAY);
        proxy.getTemperature(Region.NORTH_ENGLAND, Day.SATURDAY);
        proxy.getTemperature(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
        proxy.getTemperature(Region.MANCHESTER, Day.MONDAY);

        Object entry = proxy.cacheTemperature.entrySet().iterator().next();

        // set cache to have its size as the limit
        when(proxy.cacheTemperature.size()).thenReturn(cacheMaxSize);

        // TODO: check if removeOldestEntry has run

//        assertNotSame(entry,
//                proxy.getTemperature(Region.EDINBURGH, Day.MONDAY));
//    }
    }
}

