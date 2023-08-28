package com.ams.model;

import com.ams.constants.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {
    private UUID tenantId;

    @NotEmpty
    @Pattern(regexp = ValidationConstants.PASSWORD_REGEX_PATTERN, message = ValidationConstants.PASSWORD_VALIDATION_MESSAGE)
    private String password;

    @NotEmpty
    @Size(min = 2, message = ValidationConstants.FIRST_NAME_VALIDATION_MESSAGE)
    private String firstName;
    @Size(min = 2, message = ValidationConstants.LAST_NAME_VALIDATION_MESSAGE)
    private String lastName;

    @NotEmpty
    private String govtIssuedIdentifier;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String mobileNumber;
    @Valid
    private AddressDto address;

}
