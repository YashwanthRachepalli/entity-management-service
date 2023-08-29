package com.ami.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;

import com.ami.dataprovider.VisitEventDataProvider;
import com.ami.dataprovider.VisitorDataProvider;
import com.ams.controller.VisitorController;
import com.ams.model.VisitRequestStatus;
import com.ams.model.VisitorDto;
import com.ams.service.VisitorService;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.meta.When;
import java.util.List;import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class VisitorControllerTest {

    @Mock
    private VisitorService visitorService;

    @InjectMocks
    private VisitorController visitorController;

    @Test
    public void testGetAllVisitors() {
        when(visitorService.getAllVistors(anyInt(), anyInt()))
                .thenReturn(List.of(VisitorDataProvider.getVisitorDto()));

        ResponseEntity response = visitorController.getAllVisitors(1,2);
        Truth.assertThat(response.getStatusCode().value())
                .isEqualTo(200);
    }

    @Test
    public void testCreateVisitorAndReturnsInternalServerError() {
        when(visitorService.createVisitor(any())).thenReturn(null);

        ResponseEntity response = visitorController
                .saveOrUpdateVisitor(VisitorDataProvider.getVisitorDto());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(500);
    }

    @Test
    public void testCreateVisitor() {
        UUID uuid = UUID.randomUUID();
        when(visitorService.createVisitor(any()))
                .thenReturn(VisitorDataProvider.getVisitorWithId(uuid));

        ResponseEntity<VisitorDto> response = visitorController
                .saveOrUpdateVisitor(VisitorDataProvider.getVisitorDto());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Truth.assertThat(response.getBody().getVisitorId()).isEqualTo(uuid);
    }

    @Test
    public void testGetAllVisitRequests() throws Exception {
        when(visitorService.getAllVisitRequests())
                .thenReturn(CompletableFuture.supplyAsync(() ->
                        VisitEventDataProvider.getVisitRequestStatuses()));

        ResponseEntity<List<VisitRequestStatus>> response =
                visitorController.getAllVisitRequests();

        Truth.assertThat(response.getBody().size()).isEqualTo(1);

        when(visitorService.getAllVisitRequests())
                .thenThrow(Exception.class);

        ResponseEntity<List<VisitRequestStatus>> response1 =
                visitorController.getAllVisitRequests();

        Truth.assertThat(response1.getStatusCode().value()).isEqualTo(500);
    }


}
