package com.ams.model;

import com.ams.constants.ValidationConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private UUID addressId;

    @NotEmpty
    private String addressLine1;
    private String addressLine2;

    @NotEmpty
    @Pattern(regexp = ValidationConstants.PINCODE_REGEX_PATTERN)
    private String pinCode;

}
