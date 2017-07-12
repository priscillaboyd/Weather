package com.develogical;

import com.weather.Day;
import com.weather.Region;

/**
 * Created by ape03 on 12/07/2017.
 */

public interface ForecasterInterface {

    public String getOutlook(Region region, Day day);

    public int getTemperature(Region region, Day day);

}
