package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class ForecasterClient {

    Forecaster forecaster = new Forecaster();

    //get summary forecast
    public String getOutlook(Region region, Day day) {
        Forecast forecast = forecaster.forecastFor(region, day);
        return forecast.summary();
    }

    public int getTemp(Region region, Day day) {
        Forecast forecast = forecaster.forecastFor(region, day);
        return forecast.temperature();
    }


    public static void main(String[] args) {
        // This is just an example of using the 3rd party API - delete this class before submission.


//        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
//
//        System.out.println("London outlook: " + londonForecast.summary());
//        System.out.println("London temperature: " + londonForecast.temperature());
//
//        Forecast edinburghForecast = forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);
//
//        System.out.println("Edinburgh outlook: " + edinburghForecast.summary());
//        System.out.println("Edinburgh temperature: " + edinburghForecast.temperature());
    }


}
