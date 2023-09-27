package com.ams.service.impl;

import com.ams.entity.Apartment;
import com.ams.entity.Lease;
import com.ams.entity.Tenant;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.repository.LeaseRepository;
import com.ams.service.ApartmentService;
import com.ams.service.LeaseService;
import com.ams.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public LeaseDto saveOrUpdate(LeaseRequest request) throws Exception {
        Tenant tenant = tenantService.getTenantById(request.getTenantId());
        Apartment apartment = apartmentService.getApartmentByName(request.getApartmentName());
        log.info("Creating a new lease for apartment: {} and tenant: {}", apartment, tenant);

        if (hasActiveLease(apartment.getApartmentId(), request.getLeaseDto().getStartDate())) {
            throw new Exception("This apartment cannot be leased");
        }
        try {
            Lease lease = modelMapper.map(request.getLeaseDto(), Lease.class);
            lease.setApartment(apartment);
            lease.setTenant(tenant);

            Lease newLease = leaseRepository.save(lease);
            return modelMapper.map(newLease, LeaseDto.class);
        } catch (Exception e) {
            log.error("Error while creating lease: {}", e);
            throw new Exception("Error creating lease!");
        }
    }

    @Override
    public boolean hasActiveLease(UUID apartmentId, LocalDate date) {
        Collection<Lease> leases = leaseRepository.findALLLeasesByApartmentId(apartmentId);
        return leases.stream().anyMatch(lease -> lease.getEndDate().isAfter(date));
    }

    @Override
    public Optional<Tenant> getActiveTenantId(UUID apartmentId, LocalDate date) throws Exception {
        Collection<Lease> leases = leaseRepository.findALLLeasesByApartmentId(apartmentId);
        if (CollectionUtils.isEmpty(leases)) {
           throw new Exception("No lease found for this apartment");
        }
        Optional<Lease> activeLease = leases.stream()
                .filter(lease -> lease.getEndDate().isAfter(date))
                .findFirst();
        if (activeLease.isEmpty()) {
            throw new Exception("No Active Lease found for apartment!");
        }
        return Optional.of(activeLease.get().getTenant());
    }
}
