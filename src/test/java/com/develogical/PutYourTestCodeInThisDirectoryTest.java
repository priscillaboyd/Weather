package com.develogical;

import com.weather.*;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PutYourTestCodeInThisDirectoryTest {
    @Test
    public void getOutlookAndTemperatureForALocation() {
        Forecaster forecaster = new Forecaster();
        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
        assertNotNull(londonForecast.summary());
        assertNotNull(londonForecast.temperature());
    };
}
