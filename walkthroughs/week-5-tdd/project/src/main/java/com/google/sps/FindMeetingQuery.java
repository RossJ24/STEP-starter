// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

public final class FindMeetingQuery {
    private final int DAY_DURATION = 1440;
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // Return value of the query
    ArrayList<TimeRange> ret = new ArrayList<TimeRange>();
    if(request.getDuration() > DAY_DURATION){
        return ret;
    }

    if(events.isEmpty()) {
        ret.add(TimeRange.WHOLE_DAY);
        return ret;
    }

    ArrayList<Event> otherMeetings;
    if(request.getOptionalAttendees().size() >= 0 && request.getAttendees().isEmpty()) {
        otherMeetings = AttendeesOtherMeetings(events, request, true);
    } 
    else {
        otherMeetings = AttendeesOtherMeetings(events, request, false);
    }
    if(otherMeetings.isEmpty()) {
        ret.add(TimeRange.WHOLE_DAY);
        return ret;
    }
        ArrayList<TimeRange> timeranges = new ArrayList<TimeRange>();
        for(Event e : otherMeetings){
            timeranges.add(e.getWhen());
        }
        Collections.sort(timeranges, TimeRange.ORDER_BY_START);
        performUnionOverTimeranges(timeranges);
        int currentStart = 0;
        for(TimeRange tr : timeranges) {
            TimeRange newTimeRange = TimeRange.fromStartEnd(currentStart, tr.start(),false);
            if(newTimeRange.start() != newTimeRange.end() && newTimeRange.duration() >= request.getDuration()){
                ret.add(newTimeRange);
            }
            currentStart =  tr.end();
        }
        TimeRange newTimeRange = TimeRange.fromStartEnd(currentStart,DAY_DURATION,false);
        if(newTimeRange.start() != newTimeRange.end() && newTimeRange.duration() >= request.getDuration()) {
            ret.add(newTimeRange);
        } 
        if(!request.getOptionalAttendees().isEmpty()) {
            ret = tryToFitOptionalAttendees(ret,events,request);
        }
        return ret;
  }

  /**
    * Function that determines whether any attendees of the requested meeting have any other meeting that day
    * @param events a collection of events
    * @param request a meeting request 
    * @param optional a boolean flag that determines whether the function checks optional attendees or mandatory attendees
    * @return ret an Arraylist of the other Events
    */
  public ArrayList<Event> AttendeesOtherMeetings(Collection<Event> events, MeetingRequest request, boolean optional) {
    return (ArrayList<Event>) events.stream().filter((e) -> isOverlapBetweenAttendees(e, request, optional)).collect(Collectors.toList());
  }

  /**
    * Function that checks a single event and determines whether the requested meetings attendees are included in it
    * @param event the Event that the MeetingRequest will be checked againt
    * @param request the requested Meeting
    * @param optional a boolean that determines whether the function checks optional attendees or mandatory attendees
    * @return a boolean that respresents whether the attendees overlap
    */
  public boolean isOverlapBetweenAttendees(Event event, MeetingRequest request, boolean optional) {
      for(String eventAttendee : event.getAttendees()) {
          for(String requestAttendee : optional ? request.getOptionalAttendees() : request.getAttendees()) {
              if(eventAttendee.compareTo(requestAttendee) == 0) {
                  return true;
              }
          }
      }
      return false;
  }
  /**
    * Function that iterates through the timeranges and removes and overlapping timeranges
    * @param timeranges an Arraylist of TimeRanges
    * @return void 
    */
  public void performUnionOverTimeranges(ArrayList<TimeRange> timeranges){ 
      for(int i = 1; i < timeranges.size(); ++i){
        if(timeranges.get(i-1).overlaps(timeranges.get(i))) {
            TimeRange newrange = mergeTimeRanges(timeranges.get(i-1), timeranges.get(i));
            timeranges.set(i-1,newrange);
            timeranges.remove(i);
            --i;
        }
      }
  }
  /**
    * Function that merges two overalapping timeranges
    * @param tr1 a TimeRange that needs to be merged
    * @param tr2 another TimeRange that needs to be merged
    * @return new TimeRange created from the overlap between tr1 and tr2
   */
  private TimeRange mergeTimeRanges(TimeRange tr1, TimeRange tr2) {
      int earliest = Math.min(tr1.start(), tr2.start());
      int latest = Math.max(tr1.end(), tr2.end());
      return TimeRange.fromStartEnd(earliest,latest,false);
  }

  /**
    * Function that iterates through each event, looking for one that conflicts with optional attendees of the request
    * @param timeranges an arraylist of timeranges that is to be reduced
    * @param events a collection of events that need to be checked against
    * @param request the meeting request
    * @return an arraylist of timeranges with the conflicting ranges removed
    */
  public ArrayList<TimeRange> tryToFitOptionalAttendees(ArrayList<TimeRange> timeranges, Collection<Event> events, MeetingRequest request) {
    ArrayList<String> optionalAttendees = new ArrayList<String>(request.getOptionalAttendees());
    Set<TimeRange> rangesToBeRemoved = new HashSet<TimeRange>();
    if(optionalAttendees.isEmpty()){
        return timeranges;
    }
    for(Event event : events ){
        for(String attendee : event.getAttendees()){
            for(String optionalAttendee : request.getOptionalAttendees()){
                if(attendee.compareTo(optionalAttendee) == 0){
                    int currentindex = 0;
                    for(TimeRange tr : timeranges){
                        if(tr.overlaps(event.getWhen())){
                            rangesToBeRemoved.add(tr);
                        }
                        ++currentindex;
                    } 
                }
            }
        }
    }
    if(rangesToBeRemoved.size() >= timeranges.size()){
        return timeranges;
    }
    else{
        timeranges.removeAll(rangesToBeRemoved);
        return timeranges;
    }
  }
}
