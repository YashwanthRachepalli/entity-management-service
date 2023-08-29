package com.ami.dataprovider;

import com.ams.model.VisitRequestStatus;
import com.ams.streams.event.ReplayAttributes;
import com.ams.streams.event.VisitEvent;
import com.ams.streams.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VisitEventDataProvider {

    public static VisitEvent visitEvent(String apartmentName, UUID visitorID, UUID requestId) {
        return VisitEvent.builder()
                .requestStatus(RequestStatus.PENDING)
                .apartmentId(apartmentName)
                .visitorId(visitorID.toString())
                .requestId(requestId.toString())
                .build();
    }

    public static VisitEvent visitEventWithReplayAttributes(String apartmentName, UUID visitorID, UUID requestId) {
        return VisitEvent.builder()
                .requestStatus(RequestStatus.PENDING)
                .apartmentId(apartmentName)
                .visitorId(visitorID.toString())
                .requestId(requestId.toString())
                .replayAttributes(ReplayAttributes.builder()
                        .replayCount(1)
                        .latestPublishedTimeStamp(LocalDateTime.now())
                        .build())
                .build();
    }

    public static VisitEvent visitEventWithMaxReplayCount(String apartmentName, UUID visitorID, UUID requestId) {
        return VisitEvent.builder()
                .requestStatus(RequestStatus.PENDING)
                .apartmentId(apartmentName)
                .visitorId(visitorID.toString())
                .requestId(requestId.toString())
                .replayAttributes(ReplayAttributes.builder()
                        .replayCount(2)
                        .latestPublishedTimeStamp(LocalDateTime.now())
                        .build())
                .build();
    }

    public static List<VisitRequestStatus> getVisitRequestStatuses() {
        return List.of(VisitRequestStatus.builder()
                .requestId(UUID.randomUUID().toString())
                .requestStatus(RequestStatus.PENDING.name())
                .build());
    }
}
