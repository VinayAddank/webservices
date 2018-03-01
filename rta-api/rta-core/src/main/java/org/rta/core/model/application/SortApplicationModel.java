package org.rta.core.model.application;

import java.util.Comparator;

/**
 * ApplicationModel sorting basis of applicationDate
 * 
 * @Author sohan.maurya created on Sep 6, 2016.
 */
public class SortApplicationModel implements Comparator<ApplicationModel> {

    @Override
    public int compare(ApplicationModel applicationModel1, ApplicationModel applicationModel2) {
        if (applicationModel1.getApplicationDate() == applicationModel2.getApplicationDate())
            return 0;
        else if (applicationModel1.getApplicationDate() < applicationModel2.getApplicationDate())
            return 1;
        else
            return -1;
    }

}
