package com.ami.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.ApartmentDataProvider;
import com.ami.dataprovider.TenantDataProvider;
import com.ami.dataprovider.VisitEventDataProvider;
import com.ami.dataprovider.VisitorDataProvider;
import com.ams.model.VisitRequestDto;
import com.ams.model.VisitRequestStatus;
import com.ams.model.VisitorDto;
import com.ams.service.ApartmentService;
import com.ams.service.LeaseService;
import com.ams.service.VisitorService;
import com.ams.service.impl.VisitRequestServiceImpl;
import com.ams.streams.VisitEventProducer;
import com.ams.streams.model.RequestStatus;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VisitRequestServiceTest {

    @Mock
    VisitorService visitorService;

    @Mock
    LeaseService leaseService;

    @Mock
    ApartmentService apartmentService;

    @Mock
    VisitEventProducer visitEventProducer;

    @InjectMocks
    VisitRequestServiceImpl visitRequestService;

    @Test
    public void testCreateVisitRequestsWithNoTenant() throws Exception {
        UUID visitorId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));
        when(leaseService.getActiveTenantId(any(), any())).thenReturn(Optional.empty());

        VisitRequestDto visitRequestDto = VisitRequestDto.builder()
                .apartmentName("A301")
                .visitReason("test")
                .visitorDto(VisitorDto.builder().build())
                .build();

        Assertions.assertThrows(Exception.class,
                () -> visitRequestService.createVisitRequest(visitRequestDto));
    }

    @Test
    public void testCreateVisitRequestsWithActiveTenant() throws Exception {
        UUID visitorId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));
        when(leaseService.getActiveTenantId(any(), any()))
                .thenReturn(Optional.of(TenantDataProvider.getTenant(UUID.randomUUID())));

        when(visitorService.createVisitor(any())).thenReturn(VisitorDataProvider.getVisitorWithId(UUID.randomUUID()));

        VisitRequestDto visitRequestDto = VisitRequestDto.builder()
                .apartmentName("A301")
                .visitReason("test")
                .visitorDto(VisitorDto.builder().build())
                .build();

        when(visitEventProducer.sendVisitEvents(any()))
                .thenReturn(VisitEventDataProvider.getVisitRequestStatuses().get(0));

        VisitRequestStatus response = visitRequestService.createVisitRequest(visitRequestDto);

        Truth.assertThat(response.getRequestStatus()).isEqualTo(RequestStatus.PENDING.name());
    }
}
