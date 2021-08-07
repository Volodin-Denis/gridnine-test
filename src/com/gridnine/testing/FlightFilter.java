package com.gridnine.testing;


import com.gridnine.testing.testclasses.Flight;

import java.util.LinkedList;
import java.util.List;

/**
 *  Filters List of Flights depending on FilterCondition implementation
 */
public class FlightFilter {

    private List<FilterCondition> filterConditionList;

    public FlightFilter(){
    }
    public FlightFilter(List<FilterCondition> filterConditionList){
        this.filterConditionList = filterConditionList;
    }

    public List<Flight> filter(List<Flight> flights){
        List<Flight> out = new LinkedList<>();
        for(Flight flight : flights) {
            boolean include = true;
            for (FilterCondition filterCondition : filterConditionList) {
                include &= filterCondition.check(flight);
                if(!include) break;
            }
            if (include) out.add(flight);
        }
        return out;
    }

    public List<FilterCondition> getFilterCondition() {
        return filterConditionList;
    }
    public void setFilterCondition(List<FilterCondition> filterConditionList) {
        this.filterConditionList = filterConditionList;
    }
}
