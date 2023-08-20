package com.ams.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LeaseRequest {

    @NotNull
    private UUID tenantId;

    @NotEmpty
    private String apartmentName;

    @Valid
    private LeaseDto leaseDto;

}
