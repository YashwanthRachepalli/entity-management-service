package com.ami.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.LeaseDataProvider;
import com.ams.controller.LeaseController;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.service.LeaseService;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LeaseControllerTest {

    @Mock
    private LeaseService leaseService;

    @InjectMocks
    private LeaseController leaseController;

    @Test
    public void test() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(leaseService.saveOrUpdate(any()))
                .thenReturn(LeaseDataProvider.getLeaseDtoWithId(uuid));

        ResponseEntity<LeaseDto> response = leaseController.createLease(LeaseRequest.builder()
                .leaseDto(LeaseDataProvider.getLeaseDto()).tenantId(UUID.randomUUID()).apartmentName("A301")
                .build());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Truth.assertThat(response.getBody().getLeaseId()).isEqualTo(uuid);
    }

    @Test
    public void testInternalServerError() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(leaseService.saveOrUpdate(any()))
                .thenReturn(null);

        ResponseEntity<LeaseDto> response = leaseController.createLease(LeaseRequest.builder()
                .leaseDto(LeaseDataProvider.getLeaseDto()).tenantId(UUID.randomUUID()).apartmentName("A301")
                .build());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(500);
    }

}
