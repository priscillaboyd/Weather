package com.develogical;

import com.weather.*;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CachingProxyTest {

    private ForecasterInterface mockedInterface = mock(ForecasterInterface.class);
    private Region region = Region.EDINBURGH;
    private Day day = Day.FRIDAY;
    private CachingProxy proxy = new CachingProxy(mockedInterface, 5);

    @Test
    public void checkThatProxyImplementsInterface() {
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        assertEquals(proxy.getOutlook(region, day), "snow");
        when(mockedInterface.getTemperature(region, day)).thenReturn(30);
        assertEquals(proxy.getTemperature(region, day), 30);
    }

    @Test
    public void checkProxyCachingWorksForOutlook() {
        //mock that the request has returned "snow" from getOutlook
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");

        //check that mock interface is only called once (i.e. means cache is working)
        proxy.getOutlook(region, day);
        verify(mockedInterface, times(1)).getOutlook(region, day);

        //check that we've received the right data again
        assertEquals(proxy.getOutlook(region, day), "snow");
    }

    @Test
    public void checkProxyCachingWorksForTemperature() {
        //mock that the request has returned "snow" from getOutlook
        when(mockedInterface.getTemperature(region, day)).thenReturn(10);

        //check that mock interface is only called once (i.e. means cache is working)
        proxy.getOutlook(region, day);
        verify(mockedInterface, times(1)).getOutlook(region, day);

        //check that we've received the right data again
        assertEquals(proxy.getTemperature(region, day), 10);
    }

    @Test
    public void checkOutlookCacheHasAMaxSizeThatWorks() {
        CachingProxy proxyWithLimit = new CachingProxy(mockedInterface, 5);

        //add various entries (more than limit stipulates)
        proxyWithLimit.getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        proxyWithLimit.getOutlook(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
        proxyWithLimit.getOutlook(Region.WALES, Day.SATURDAY);
        proxyWithLimit.getOutlook(Region.EDINBURGH, Day.MONDAY);
        proxyWithLimit.getOutlook(Region.MANCHESTER, Day.MONDAY);
        proxyWithLimit.getOutlook(Region.NORTH_ENGLAND, Day.SATURDAY);

        //getting 'manchester' should bypass i/f (zero invocations)
        when(mockedInterface.getTemperature(Region.MANCHESTER, Day.MONDAY)).thenReturn(25);
        proxyWithLimit.getOutlook(Region.MANCHESTER, Day.MONDAY);
        verify(mockedInterface, times(0)).getOutlook(region, day);
        assertEquals(proxyWithLimit.getTemperature(Region.MANCHESTER, Day.MONDAY), 25);

        //getting SE england should go through i/f (one invocation)
        when(mockedInterface.getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY)).thenReturn(10);
        proxyWithLimit.getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        verify(mockedInterface, times(1)).getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        assertEquals(proxyWithLimit.getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY), 10);
    }

//    @Test
//    public void checkTemperatureCacheHasAMaxSizeThatWorks() {
//        // set cacheSize is the same cache max size (reached limit)
//        int cacheSize = proxy.cacheTemperature.size();
//
//        // set max size same as current cache size
//        int cacheMaxSize = cacheSize;
//
//        //add various entries (more than limit stipulates)
//        proxy.getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
//        proxy.getOutlook(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
//        proxy.getOutlook(Region.WALES, Day.SATURDAY);
//        proxy.getOutlook(Region.EDINBURGH, Day.MONDAY);
//        proxy.getOutlook(Region.MANCHESTER, Day.MONDAY);
//        proxy.getOutlook(Region.NORTH_ENGLAND, Day.SATURDAY);
//
//        //assert size of cache still meets limit
//        assertTrue(cacheSize <= cacheMaxSize);
//    }

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

        // TODO: check if removeOldestEntry has run?

//        assertNotSame(entry,
//                proxy.getTemperature(Region.EDINBURGH, Day.MONDAY));
//    }
    }

    @Test
    public void checkCacheEntriesHaveTimeStamp(){

        //check that you have a timestamp when retrieving a cache entry

    }

}
