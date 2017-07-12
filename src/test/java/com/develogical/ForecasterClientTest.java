package com.develogical;

import com.weather.*;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ForecasterClientTest {

    @Mock
    ForecasterAdapter forecasterAdapter = new ForecasterAdapter();

    Forecaster forecaster = new Forecaster();
    @Test
    public void getOutlookAndTemperatureForALocation() {
        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
        assertNotNull(londonForecast.summary());
        assertNotNull(londonForecast.temperature());
    };

    @Test
    public void getOutlookAndTemperatureFromForecasterAdapter(){
        ForecasterAdapter adapter = new ForecasterAdapter();
        assertNotNull(adapter.getOutlook(Region.LONDON, Day.MONDAY));
        assertNotNull(adapter.getTemperature(Region.LONDON, Day.MONDAY));
    }

}
