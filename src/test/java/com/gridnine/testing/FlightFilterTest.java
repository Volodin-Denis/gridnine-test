package test.java.com.gridnine.testing;

import com.gridnine.testing.FilterCondition;
import com.gridnine.testing.testclasses.Flight;
import com.gridnine.testing.FlightFilter;
import com.gridnine.testing.testclasses.FlightBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FlightFilterTest {
    List<Flight> flights;
    List<FilterCondition> filterConditionList = new LinkedList<>();
    @Before
    public void init(){
        flights = FlightBuilder.createFlights();
    }

    @Test
    public void returnEmptyListWhenInputIsEmpty(){
        flights = new ArrayList<>();
        List<FilterCondition> filterConditionList = new LinkedList<>();
        FlightFilter flightFilter = new FlightFilter(filterConditionList);
        Assert.assertEquals(new ArrayList<Flight>().toString(),flightFilter.filter(flights).toString());
    }
    @Test
    public void returnFilteredListWithDepartureBeforeArrivalFilterCondition(){
        filterConditionList.add(new FilterCondition.DepartureBeforeArrival());
        FlightFilter flightFilter = new FlightFilter(filterConditionList);

        List<Flight> expected = new LinkedList<>(FlightBuilder.createFlights());
        expected.remove(3);

        Assert.assertEquals(expected.toString(),flightFilter.filter(flights).toString());
    }

    @Test
    public void returnFilteredListWithDepartureBeforeCurrentTimeFilterCondition(){
        filterConditionList.add(new FilterCondition.DepartureBeforeCurrentTime());
        FlightFilter flightFilter = new FlightFilter(filterConditionList);

        List<Flight> expected = new LinkedList<>(FlightBuilder.createFlights());
        expected.remove(2);

        Assert.assertEquals(expected.toString(),flightFilter.filter(flights).toString());
    }

    @Test
    public void returnFilteredListWithTimeOnGroundGreaterThanFilterCondition(){
        filterConditionList.add(new FilterCondition.TimeOnGroundGreaterThan(2, TimeUnit.HOURS));
        FlightFilter flightFilter = new FlightFilter(filterConditionList);

        List<Flight> expected = new LinkedList<>(FlightBuilder.createFlights());
        expected.remove(5);
        expected.remove(4);

        Assert.assertEquals(expected.toString(),flightFilter.filter(flights).toString());
    }

    @Test
    public void returnFilteredListWithAllFilterConditions(){
        filterConditionList.add(new FilterCondition.TimeOnGroundGreaterThan(2, TimeUnit.HOURS));
        filterConditionList.add(new FilterCondition.DepartureBeforeCurrentTime());
        filterConditionList.add(new FilterCondition.DepartureBeforeArrival());
        FlightFilter flightFilter = new FlightFilter(filterConditionList);

        List<Flight> expected = new LinkedList<>(FlightBuilder.createFlights());
        expected.remove(5);
        expected.remove(4);
        expected.remove(3);
        expected.remove(2);

        Assert.assertEquals(expected.toString(),flightFilter.filter(flights).toString());
    }
}
