package com.ams.model;

import com.ams.model.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseDto {

    private UUID leaseId;

    @FutureOrPresent
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate startDate;

    @Future
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate endDate;

    @NotNull
    private Double securityDeposit;
    @NotNull
    private Double rentalFee;

    @Future
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate rentalDate;

}
