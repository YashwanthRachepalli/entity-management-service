package com.ams.streams.event;

import com.ams.entity.Visitor;
import com.ams.streams.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitEvent {

    private String requestId;

    private String apartmentId;

    private String tenantId;

    private String visitorId;

    private String visitReason;

    private RequestStatus requestStatus;

    private ReplayAttributes replayAttributes;
}
