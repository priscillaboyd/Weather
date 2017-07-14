package com.develogical;

import com.oocode.weather.DayForecaster;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class Example {
    public static void main(String[] args) {
        lib1();
        lib2();
    }

    private static void lib1() {
        Forecaster forecaster = new Forecaster();

        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());

        Forecast edinburghForecast = forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);

        System.out.println("Edinburgh outlook: " + edinburghForecast.summary());
        System.out.println("Edinburgh temperature: " + edinburghForecast.temperature());
    }

    private static void lib2() {
        Double londonForecastTemp = DayForecaster.forecast(com.oocode.weather.Forecaster.Regions.SE_ENGLAND, 0);
        String londonForecastSummary = DayForecaster.forecastSummary(com.oocode.weather.Forecaster.Regions.SE_ENGLAND, 0);

        System.out.println("London outlook: " + londonForecastSummary);
        System.out.println("London temperature: " + londonForecastTemp);

        Double edinburghForecastTemp = DayForecaster.forecast(com.oocode.weather.Forecaster.Regions.SE_SCOTLAND, 0);
        String edinburghForecastSummary = DayForecaster.forecastSummary(com.oocode.weather.Forecaster.Regions.SE_SCOTLAND, 0);

        System.out.println("Edinburgh outlook: " + edinburghForecastSummary);
        System.out.println("Edinburgh temperature: " + edinburghForecastTemp);
    }
}
