package com.develogical;

/**
 * Created by ape03 on 12/07/2017.
 */

public interface ForecasterInterface {
    static enum Region {
        LONDON,
        OXFORD;
    }

    static enum Day {
        MONDAY,
        TUESDAY;
    }

    String getOutlook(Region region, Day day);

    int getTemperature(Region region, Day day);

}
