package com.develogical;

import com.oocode.weather.*;
import com.weather.Forecast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AverageForecasterTest {

    @Mock
    ForecasterInterface dayForecasterAdapter;

    @Mock
    ForecasterInterface forecasterAdapter;

    @Before
    public void setUp(){
        when(dayForecasterAdapter.getTemperature(ForecasterInterface.Region.LONDON, ForecasterInterface.Day.MONDAY)).thenReturn(2);
        when(forecasterAdapter.getTemperature(ForecasterInterface.Region.LONDON, ForecasterInterface.Day.MONDAY)).thenReturn(2);
    }

    @Test
    public void getAverageTemperatureFromTwoServices(){
        AveragingForecaster dayAveragingForecaster = new AveragingForecaster(dayForecasterAdapter, forecasterAdapter);
        String temperature = dayAveragingForecaster.averageTemperature(ForecasterInterface.Region.LONDON, ForecasterInterface.Day.MONDAY);
        assertEquals("2.0" , temperature);
        verify(forecasterAdapter).getTemperature(ForecasterInterface.Region.LONDON, ForecasterInterface.Day.MONDAY);
        verify(dayForecasterAdapter).getTemperature(ForecasterInterface.Region.LONDON, ForecasterInterface.Day.MONDAY);
    }

}
