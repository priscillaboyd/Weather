package com.develogical;

import com.weather.*;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CachingProxyTest {

    private ForecasterInterface mockedInterface = mock(ForecasterInterface.class);
    private Region region = Region.EDINBURGH;
    private Day day = Day.FRIDAY;

    /*/
        1) test that cache works to get result of outlook
        2) test that cache works to get result of temperature
     */

    //Test that caching works without knowing the implementation
    @Test
    public void checkThatCachingWorks(){
        CachingProxy proxy = new CachingProxy(mockedInterface);
        when(mockedInterface.getOutlook(region, day)).thenReturn("snow");
        assertEquals(proxy.getOutlook(region, day), "snow");
    }


}
