package com.develogical;

import com.weather.*;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ForecasterClientTest {

    Forecaster forecaster = new Forecaster();
    @Test
    public void getOutlookAndTemperatureForALocation() {
        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
        assertNotNull(londonForecast.summary());
        assertNotNull(londonForecast.temperature());
    };
//    @Test
//    public void getOutlookAndTemperatureForAnyLocation() {
//        ForecasterClient forecastClient = new ForecasterClient();
//        assertNotNull(forecastClient.getOutlook());
//        assertNotNull(forecastClient.getTemp());
//    };
    @Test
    public void getOutlookAndTemperatureForAnyLocation() {
        ForecasterClient forecastClient = new ForecasterClient();
        assertNotNull(forecastClient.getOutlook(Region.LONDON, Day.MONDAY));
        assertNotNull(forecastClient.getTemp(Region.LONDON, Day.MONDAY));
    };
}
