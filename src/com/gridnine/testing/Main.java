package com.gridnine.testing;

import com.gridnine.testing.testclasses.Flight;
import com.gridnine.testing.testclasses.FlightBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        List<FilterCondition> filterConditionList = new LinkedList<>();
        FlightFilter flightFilter = new FlightFilter(filterConditionList);

        //System.out.println(flights);

        filterConditionList.add(new FilterCondition.DepartureBeforeCurrentTime());
        System.out.println(flightFilter.filter(flights));

        filterConditionList.clear();
        filterConditionList.add(new FilterCondition.DepartureBeforeArrival());
        System.out.println(flightFilter.filter(flights));

        filterConditionList.clear();
        filterConditionList.add(new FilterCondition.TimeOnGroundGreaterThan(2, TimeUnit.HOURS));
        System.out.println(flightFilter.filter(flights));
    }
}
