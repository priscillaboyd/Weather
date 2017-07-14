package com.develogical;

import com.weather.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        //add various entries (more than limit stipulates)
        mockedInterface.getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        mockedInterface.getOutlook(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
        mockedInterface.getOutlook(Region.WALES, Day.SATURDAY);
        mockedInterface.getOutlook(Region.EDINBURGH, Day.MONDAY);
        mockedInterface.getOutlook(Region.MANCHESTER, Day.MONDAY);
        mockedInterface.getOutlook(Region.NORTH_ENGLAND, Day.MONDAY);

        // expect manchester data only makes one call in total via i/f
        proxy.getOutlook(Region.MANCHESTER, Day.SATURDAY);
        verify(mockedInterface, times(1)).getOutlook(Region.MANCHESTER, Day.SATURDAY);

        // expect SE makes two calls in total (as first call was taken out of cache)
        proxy.getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        verify(mockedInterface, times(2)).getOutlook(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
    }

    @Test
    public void checkTemperatureCacheHasAMaxSizeThatWorks() {
        //add various entries (more than limit stipulates)
        mockedInterface.getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        mockedInterface.getTemperature(Region.SOUTH_WEST_ENGLAND, Day.MONDAY);
        mockedInterface.getTemperature(Region.WALES, Day.SATURDAY);
        mockedInterface.getTemperature(Region.EDINBURGH, Day.MONDAY);
        mockedInterface.getTemperature(Region.MANCHESTER, Day.MONDAY);
        mockedInterface.getTemperature(Region.NORTH_ENGLAND, Day.MONDAY);

        // expect manchester data only makes one call in total via i/f
        proxy.getTemperature(Region.MANCHESTER, Day.SATURDAY);
        verify(mockedInterface, times(1)).getTemperature(Region.MANCHESTER, Day.SATURDAY);

        // expect SE makes two calls in total (as first call was taken out of cache)
        proxy.getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
        verify(mockedInterface, times(2)).getTemperature(Region.SOUTH_EAST_ENGLAND, Day.MONDAY);
    }

    @Test
    public void CacheEntriesOlderThanAnHourAreRemoved() {
        LocalDateTime dateTime = mock(LocalDateTime.class);
        when(dateTime).thenReturn(dateTime.plusHours(1));

        //simulate +1h has passed
        assertTrue(proxy.resetCache());

    }

}

