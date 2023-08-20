package com.ams.service;

import com.ams.entity.Tenant;
import com.ams.model.TenantDto;
import java.util.Optional;
import java.util.UUID;

public interface TenantService {
    Optional<TenantDto> saveOrUpdate(TenantDto tenant);

    Tenant getTenantById(UUID tenantId) throws Exception;
}
