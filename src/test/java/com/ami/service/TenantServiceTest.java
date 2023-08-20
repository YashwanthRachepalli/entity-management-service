package com.ami.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.TenantDataProvider;
import com.ams.entity.Tenant;
import com.ams.model.TenantDto;
import com.ams.repository.TenantRepository;
import com.ams.service.impl.TenantServiceImpl;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private TenantServiceImpl tenantService;

    @Test
    public void testUpdateExistingTenant() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.findByGovtIssuedIdentifier(any()))
                .thenReturn(Optional.of(TenantDataProvider.getTenant(tenantId)));

        when(tenantRepository.save(any()))
                .thenReturn(TenantDataProvider.getTenant(tenantId));

        Optional<TenantDto> tenantDto = tenantService.saveOrUpdate(TenantDataProvider.getTenantDto());

        Truth.assertThat(tenantDto.isPresent()).isTrue();
        Truth.assertThat(tenantDto.get().getTenantId()).isEqualTo(tenantId);
    }

    @Test
    public void testCreateTenant() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.findByGovtIssuedIdentifier(any()))
                .thenReturn(Optional.empty());

        when(tenantRepository.save(any()))
                .thenReturn(TenantDataProvider.getTenant(tenantId));

        Optional<TenantDto> tenantDto = tenantService.saveOrUpdate(TenantDataProvider.getTenantDto());

        Truth.assertThat(tenantDto.isPresent()).isTrue();
        Truth.assertThat(tenantDto.get().getTenantId()).isEqualTo(tenantId);
    }

    @Test
    public void testCreateTenantWithException() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.findByGovtIssuedIdentifier(any()))
                .thenReturn(Optional.empty());

        when(tenantRepository.save(any()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(Exception.class, () -> tenantService.saveOrUpdate(TenantDataProvider.getTenantDto()));

    }

    @Test
    public void testGetTenantById() throws Exception {
        UUID tenantId = UUID.randomUUID();

        when(tenantRepository.findById(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> tenantService.getTenantById(tenantId));

        when(tenantRepository.findById(any()))
                .thenReturn(Optional.of(TenantDataProvider.getTenant(tenantId)));

        Tenant tenant = tenantService.getTenantById(tenantId);

        Truth.assertThat(tenant.getTenantId()).isEqualTo(tenantId);
    }

}
