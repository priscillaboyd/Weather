package com.develogical;

import com.weather.*;

/**
 * Created by ape03 on 12/07/2017.
 */
// should talk to the forecaster service
public class ForecasterAdapter implements ForecasterInterface {

    private Forecaster forecaster = null;

//    @Override
//    public String getOutlook(Region region, Day day) {
//        forecaster = new Forecaster();
//        Forecast forecast = forecaster.forecastFor(region, day);
//        return forecast.summary();
//    }
//
//    @Override
//    public int getTemperature(Region region, Day day) {
//        forecaster = new Forecaster();
//        Forecast forecast = forecaster.forecastFor(region, day);
//        return forecast.temperature();
//    }

    @Override
    public String getOutlook(Region region, Day day) {
        return null;
    }

    @Override
    public int getTemperature(Region region, Day day) {
        return 0;
    }
}
