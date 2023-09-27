package com.ams.service.impl;

import com.ams.entity.Apartment;
import com.ams.entity.Tenant;
import com.ams.model.VisitRequestDto;
import com.ams.model.VisitRequestStatus;
import com.ams.model.VisitorDto;
import com.ams.service.ApartmentService;
import com.ams.service.LeaseService;
import com.ams.service.VisitRequestService;
import com.ams.service.VisitorService;
import com.ams.streams.VisitEventProducer;
import com.ams.streams.event.VisitEvent;
import com.ams.streams.model.RequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class VisitRequestServiceImpl implements VisitRequestService {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private VisitEventProducer visitEventProducer;

    public static final String VISIT_REQUEST_MESSAGE = " is trying to reach you with reason ";

    @Override
    public VisitRequestStatus createVisitRequest(VisitRequestDto visitRequestDto) throws Exception {
        Apartment apartment = apartmentService.getApartmentByName(visitRequestDto.getApartmentName());
        Optional<Tenant> tenant = leaseService.getActiveTenantId(apartment.getApartmentId(), LocalDate.now());
        if (tenant.isEmpty()) {
            throw new Exception("No Tenant found for this apartment.");
        }
        VisitorDto visitor = visitorService.createVisitor(visitRequestDto.getVisitorDto());

        VisitRequestStatus visitRequestStatus = visitEventProducer.sendVisitEvents(createVisitEvent(apartment.getApartmentName(),
                visitor.getVisitorId().toString(), tenant.get().getTenantId().toString(),
                modifyReasonWithVisitorName(visitRequestDto.getVisitReason(), visitor)));
        return visitRequestStatus;
    }

    private String modifyReasonWithVisitorName(String visitReason, VisitorDto visitor) {
        return new StringBuilder()
                .append(visitor.getFirstName())
                .append(VISIT_REQUEST_MESSAGE)
                .append(visitReason)
                .toString();
    }

    private VisitEvent createVisitEvent(String apartmentName, String visitorId,
                                        String tenantId, String reason) {
        return VisitEvent.builder()
                .apartmentId(apartmentName)
                .visitorId(visitorId)
                .tenantId(tenantId)
                .visitReason(reason)
                .requestStatus(RequestStatus.PENDING)
                .build();
    }
}
