package com.ams.service.impl;

import com.ams.entity.Tenant;
import com.ams.model.TenantDto;
import com.ams.repository.TenantRepository;
import com.ams.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public Optional<TenantDto> saveOrUpdate(TenantDto tenantDto) {
        log.info("Creating tenant: {}", tenantDto);
        Optional<Tenant> tenant = tenantRepository
                .findByGovtIssuedIdentifier(tenantDto.getGovtIssuedIdentifier());
        if (tenant.isPresent()) {
            log.info("Found tenant with govtIssuedIdentifier: {}", tenant.get());
            tenantDto.setTenantId(tenant.get().getTenantId());
        }
        return Optional.of(updateTenantDetails(tenantDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Tenant getTenantById(UUID tenantId) throws Exception {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new Exception("Tenant not found!"));
    }

    private TenantDto updateTenantDetails(TenantDto tenantDto) {
        try {
            Tenant tenant = tenantRepository.save(mapper.map(tenantDto, Tenant.class));
            return mapper.map(tenant, TenantDto.class);
        } catch (Exception e) {
            log.error("Exception while updating tenant datils for {} with {}", tenantDto.getTenantId(), e);
            throw e;
        }
    }
}
