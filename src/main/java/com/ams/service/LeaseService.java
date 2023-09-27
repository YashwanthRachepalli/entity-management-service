package com.ams.service;

import com.ams.entity.Tenant;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface LeaseService {

    LeaseDto saveOrUpdate(LeaseRequest request) throws Exception;

    boolean hasActiveLease(UUID apartmentId, LocalDate date);

    Optional<Tenant> getActiveTenantId(UUID apartmentId, LocalDate date) throws Exception;
}
