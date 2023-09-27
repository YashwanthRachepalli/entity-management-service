package com.ams.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequestDto {

    @NotEmpty
    private String apartmentName;

    private String visitReason;

    @Valid
    private VisitorDto visitorDto;

}
