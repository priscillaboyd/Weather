package com.develogical;

import com.oocode.weather.*;

/**
 * Created by ape03 on 12/07/2017.
 */
// should talk to the forecaster service
public class DayForecasterAdapter implements ForecasterInterface {

    @Override
    public String getOutlook(Region region, Day day) {
        return DayForecaster.forecastSummary(Forecaster.Regions.N_WALES, 1);
    }

    @Override
    public int getTemperature(Region region, Day day) {
//        return DayForecaster.forecast(Forecaster.Regions.MID_SCOTLAND, 2);
        return 1;
    }



}
