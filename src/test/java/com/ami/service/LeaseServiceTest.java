package com.ami.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;

import com.ami.dataprovider.ApartmentDataProvider;
import com.ami.dataprovider.LeaseDataProvider;
import com.ami.dataprovider.TenantDataProvider;
import com.ams.entity.Lease;
import com.ams.entity.Tenant;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.repository.LeaseRepository;
import com.ams.service.ApartmentService;
import com.ams.service.TenantService;
import com.ams.service.impl.LeaseServiceImpl;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LeaseServiceTest {

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private TenantService tenantService;

    @Mock
    private ApartmentService apartmentService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private LeaseServiceImpl leaseService;

    @Test
    public void testExceptionIncreatingLease() throws Exception {
        UUID tenantId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        UUID leaseId = UUID.randomUUID();
        when(tenantService.getTenantById(any()))
                .thenReturn(TenantDataProvider.getTenant(tenantId));
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));

        when(leaseRepository.findALLLeasesByApartmentId(apartmentId))
                .thenReturn(List.of(LeaseDataProvider.getLease(leaseId)));

        LeaseRequest leaseRequest = LeaseRequest.builder()
                .tenantId(tenantId)
                .apartmentName("A301")
                .leaseDto(LeaseDataProvider.getLeaseDto())
                .build();

        Assertions.assertThrows(Exception.class, () -> leaseService.saveOrUpdate(leaseRequest));
    }

    @Test
    public void testNewLeaseCreation() throws Exception {
        UUID tenantId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        UUID leaseId = UUID.randomUUID();
        when(tenantService.getTenantById(any()))
                .thenReturn(TenantDataProvider.getTenant(tenantId));
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));

        when(leaseRepository.findALLLeasesByApartmentId(apartmentId))
                .thenReturn(Collections.emptyList());

        when(leaseRepository.save(any()))
                .thenReturn(LeaseDataProvider.getLease(leaseId));

        LeaseRequest leaseRequest = LeaseRequest.builder()
                .tenantId(tenantId)
                .apartmentName("A301")
                .leaseDto(LeaseDataProvider.getLeaseDto())
                .build();
        LeaseDto lease = leaseService.saveOrUpdate(leaseRequest);
        Truth.assertThat(lease.getLeaseId()).isEqualTo(leaseId);
    }

    @Test
    public void testNewLeaseCreationWithException() throws Exception {
        UUID tenantId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        UUID leaseId = UUID.randomUUID();
        when(tenantService.getTenantById(any()))
                .thenReturn(TenantDataProvider.getTenant(tenantId));
        when(apartmentService.getApartmentByName(any()))
                .thenReturn(ApartmentDataProvider.getApartment("A301", apartmentId, buildingId));

        when(leaseRepository.findALLLeasesByApartmentId(apartmentId))
                .thenReturn(Collections.emptyList());

        doThrow(IllegalArgumentException.class).when(leaseRepository)
                .save(any());

        LeaseRequest leaseRequest = LeaseRequest.builder()
                .tenantId(tenantId)
                .apartmentName("A301")
                .leaseDto(LeaseDataProvider.getLeaseDto())
                .build();

        Assertions.assertThrows(Exception.class, () -> leaseService.saveOrUpdate(leaseRequest));
    }

    @Test
    public void testGetActiveTenantNegativeScenarios() {
        when(leaseRepository.findALLLeasesByApartmentId(any()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(Exception.class,
                () -> leaseService.getActiveTenantId(UUID.randomUUID(), LocalDate.now()));
    }

    @Test
    public void testGetActiveTenantWithActiveLease() throws Exception {
        UUID tenantId = UUID.randomUUID();
        Lease lease = LeaseDataProvider.getLease(UUID.randomUUID());
        Tenant tenant = TenantDataProvider.getTenant(tenantId);
        lease.setTenant(tenant);
        when(leaseRepository.findALLLeasesByApartmentId(any()))
                .thenReturn(List.of(lease));

        Optional<Tenant> result = leaseService.getActiveTenantId(UUID.randomUUID(), LocalDate.now());

        Truth.assertThat(result).isEqualTo(Optional.of(tenant));
    }

    @Test
    public void testGetActiveTenantWithNoActiveLease() throws Exception {
        UUID tenantId = UUID.randomUUID();
        Lease lease = LeaseDataProvider.getLease(UUID.randomUUID());
        lease.setEndDate(LocalDate.now());
        Tenant tenant = TenantDataProvider.getTenant(tenantId);
        lease.setTenant(tenant);
        when(leaseRepository.findALLLeasesByApartmentId(any()))
                .thenReturn(List.of(lease));

        Assertions.assertThrows(Exception.class, () -> leaseService.getActiveTenantId(UUID.randomUUID(), LocalDate.now()));
    }

}
