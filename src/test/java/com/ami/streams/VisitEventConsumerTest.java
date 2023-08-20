package com.ami.streams;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.ApartmentDataProvider;
import com.ami.dataprovider.VisitEventDataProvider;
import com.ami.dataprovider.VisitorDataProvider;
import com.ams.service.ApartmentService;
import com.ams.service.LeaseService;
import com.ams.service.VisitorService;
import com.ams.streams.ReplayEventProducer;
import com.ams.streams.VisitEventConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VisitEventConsumerTest {

    @Mock
    private ReplayEventProducer replayEventProducer;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private LeaseService leaseService;

    @Mock
    private VisitorService visitorService;

    @InjectMocks
    private VisitEventConsumer visitEventConsumer;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(visitEventConsumer, "topic", "VISIT_EVENT_TEST");
    }

    @Test
    public void testConsumerWithExistingVisitor() throws Exception {
        UUID visitorId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        when(visitorService.getVisitorById(any()))
                .thenReturn(Optional.of(VisitorDataProvider.getVisitor(visitorId)));
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));

        when(leaseService.hasActiveLease(any(), any())).thenReturn(Boolean.TRUE);

        visitEventConsumer.listen(List.of(VisitEventDataProvider.visitEvent("A301", visitorId, UUID.randomUUID())));
    }

    @Test
    public void testConsumerWithException() throws Exception {
        UUID visitorId = UUID.randomUUID();
        when(visitorService.getVisitorById(any()))
                .thenReturn(Optional.of(VisitorDataProvider.getVisitor(visitorId)));
        when(apartmentService.getApartmentByName(any()))
                .thenThrow(Exception.class);

        visitEventConsumer.listen(List.of(VisitEventDataProvider.visitEvent("A301", visitorId, UUID.randomUUID())));
    }

    @Test
    public void testConsumerWithInvalidVisitorId() throws Exception {
        UUID visitorId = UUID.randomUUID();
        when(visitorService.getVisitorById(any()))
                .thenReturn(Optional.empty());

        visitEventConsumer.listen(List.of(VisitEventDataProvider.visitEvent("A301", visitorId, UUID.randomUUID())));
    }
}
