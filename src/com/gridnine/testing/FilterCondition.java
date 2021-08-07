package com.gridnine.testing;

import com.gridnine.testing.testclasses.Flight;
import com.gridnine.testing.testclasses.Segment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class FilterCondition {
    /** This method must return true if flight should be included in filtered list, if it shouldn't it must return false */
    public abstract boolean check(Flight flight);

    public static class DepartureBeforeArrival extends FilterCondition{
        @Override
        public boolean check(Flight flight) {
            for(Segment segment : flight.getSegments()){
                if(segment.getArrivalDate().isBefore(segment.getDepartureDate())) return false;
            }
            return true;
        }
    }

    public static class DepartureBeforeCurrentTime extends FilterCondition{

        @Override
        public boolean check(Flight flight) {
            for(Segment segment : flight.getSegments()){
                if(segment.getDepartureDate().isBefore(LocalDateTime.now())) return false;
            }
            return true;
        }
    }

    public static class TimeOnGroundGreaterThan extends FilterCondition{
        private long maxTimeOnGround;

        @Override
        public boolean check(Flight flight) {
            long timeOnGround = 0;
            LocalDateTime departureTime;
            List<Segment> segments = flight.getSegments();
            for(int i = 0; i < segments.size()-1; i++){
                timeOnGround += segments.get(i+1).getDepartureDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - segments.get(i).getArrivalDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                if(timeOnGround>maxTimeOnGround) return false;
            }
            return true;
        }

        public TimeOnGroundGreaterThan() {
        }

        public TimeOnGroundGreaterThan(long maxTimeOnGround, TimeUnit timeUnit) {
            this.maxTimeOnGround = timeUnit.toMillis(maxTimeOnGround);
        }

        public void setMaxTimeOnGround(long maxTimeOnGround, TimeUnit timeUnit) {
            this.maxTimeOnGround = timeUnit.toMillis(maxTimeOnGround);
        }
    }
}
