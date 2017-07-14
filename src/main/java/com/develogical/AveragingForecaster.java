package com.develogical;

/**
 * Created by ape03 on 14/07/2017.
 */
public class AveragingForecaster implements ForecasterInterface {

    private final ForecasterInterface forecaster1;
    private final ForecasterInterface forecaster2;

    public AveragingForecaster(ForecasterInterface forecaster1, ForecasterInterface forecaster2){
        this.forecaster1 = forecaster1;
        this.forecaster2 = forecaster2;
    }

    public String averageTemperature(ForecasterInterface.Region region, Day day) {
        double apeForecastsTemperature = forecaster2.getTemperature(region, day);
        double temperature = forecaster1.getTemperature(region, day);
        return Double.toString((apeForecastsTemperature+temperature)/2.0d);
    }

    @Override
    public String getOutlook(Region region, Day day) {
        return null;
    }

    @Override
    public int getTemperature(Region region, Day day) {
        return 0;
    }
}
