package com.ami.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.TenantDataProvider;
import com.ams.controller.TenantController;
import com.ams.model.TenantDto;
import com.ams.service.TenantService;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TenantControllerTest {

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private TenantController tenantController;

    @Test
    public void testCreateTenant() {
        UUID uuid = UUID.randomUUID();
        when(tenantService.saveOrUpdate(any()))
                .thenReturn(Optional.of(TenantDataProvider.getTenantDtoWithId(uuid)));

        ResponseEntity<TenantDto> response = tenantController
                .createTenant(TenantDataProvider.getTenantDto());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Truth.assertThat(response.getBody().getTenantId()).isEqualTo(uuid);
    }

    @Test
    public void testCreateTenantWithInternalServerError() {
        UUID uuid = UUID.randomUUID();
        when(tenantService.saveOrUpdate(any()))
                .thenReturn(Optional.empty());

        ResponseEntity<TenantDto> response = tenantController
                .createTenant(TenantDataProvider.getTenantDto());

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(500);
    }

}
