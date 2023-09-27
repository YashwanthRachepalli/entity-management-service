package com.ami.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.VisitEventDataProvider;
import com.ams.controller.VisitRequestController;
import com.ams.model.VisitRequestDto;
import com.ams.model.VisitRequestStatus;
import com.ams.model.VisitorDto;
import com.ams.service.VisitRequestService;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class VisitRequestContollerTest {

    @Mock
    VisitRequestService visitRequestService;

    @InjectMocks
    VisitRequestController visitRequestController;

    @Test
    public void testInternalServerError() throws Exception {
        when(visitRequestService.createVisitRequest(any()))
                .thenReturn(null);

        VisitRequestDto visitRequestDto = VisitRequestDto.builder()
                .apartmentName("A301")
                .visitReason("test")
                .visitorDto(VisitorDto.builder().build())
                .build();

        ResponseEntity<VisitRequestStatus> responseEntity =
                visitRequestController.createVisitRequest(visitRequestDto);

        Truth.assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
    }

    @Test
    public void testSuccess() throws Exception {
        when(visitRequestService.createVisitRequest(any()))
                .thenReturn(VisitEventDataProvider.getVisitRequestStatuses().get(0));

        VisitRequestDto visitRequestDto = VisitRequestDto.builder()
                .apartmentName("A301")
                .visitReason("test")
                .visitorDto(VisitorDto.builder().build())
                .build();

        ResponseEntity<VisitRequestStatus> responseEntity =
                visitRequestController.createVisitRequest(visitRequestDto);

        Truth.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
    }

}
