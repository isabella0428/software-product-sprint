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

import java.util.*;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> requiredAttendees = request.getAttendees();
    ArrayList<TimeRange> conflictedTimeRange = new ArrayList<>();
    Collection<TimeRange> properTimeRange = new ArrayList<>();

    for (Event event : events) {
      Set<String> eventAttendees = event.getAttendees();
      for (String attendee : eventAttendees) {
        if (requiredAttendees.contains(attendee)) {
          conflictedTimeRange.add(event.getWhen());
          break;
        }
      }
    }
    
    Collections.sort(conflictedTimeRange, TimeRange.ORDER_BY_START);
    int start = TimeRange.START_OF_DAY;
    for (TimeRange tr : conflictedTimeRange) {
      if ((tr.start() >= start) && (tr.start() - start >= request.getDuration())) {
        properTimeRange.add(TimeRange.fromStartEnd(start, tr.start(), false));
      }

      if (start < tr.end()) {
        start = tr.end();
      }
    }

    if (TimeRange.END_OF_DAY - start >= request.getDuration()) {
      properTimeRange.add(TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true));
    }

    return properTimeRange;
  }
}
