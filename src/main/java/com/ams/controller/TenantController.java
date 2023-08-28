package com.ams.controller;

import com.ams.model.TenantDto;
import com.ams.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/tenant")
@Tag(name = "tenant", description = "api to manage tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @Operation(summary = "adds a tenant to the building", description = "adds a tenant to the building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<TenantDto> createTenant(
            @Parameter(description = "creates a new tenant", required = true) @Valid @RequestBody TenantDto tenant) {
        Optional<TenantDto> tenantDto = tenantService.saveOrUpdate(tenant);
        if (tenantDto.isEmpty()) {
            log.info("Error in saving Tenant Entity");
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(tenantDto.get());
    }

}
