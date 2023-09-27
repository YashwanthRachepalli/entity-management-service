package com.ami.dataprovider;

import com.ams.entity.Tenant;
import com.ams.model.AddressDto;
import com.ams.model.TenantDto;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class TenantDataProvider {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static TenantDto getTenantDto() {
        return TenantDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("test@gmail.com")
                .mobileNumber("9786543210")
                .preferredContact("SMS")
                .govtIssuedIdentifier("testIdentifier")
                .address(getAddressDto())
                .build();
    }

    public static AddressDto getAddressDto() {
        return AddressDto.builder()
                .addressLine1("hno xxx, near xxx")
                .pinCode("523523")
                .build();
    }

    public static TenantDto getTenantDtoWithId(UUID uuid) {
        return TenantDto.builder()
                .tenantId(uuid)
                .firstName("testFirstName")
                .password("Commit@123")
                .lastName("testLastName")
                .email("test@gmail.com")
                .mobileNumber("9786543210")
                .preferredContact("SMS")
                .govtIssuedIdentifier("testIdentifier")
                .address(getAddressDto())
                .build();
    }

    public static AddressDto getAddressDtoWithId(UUID uuid) {
        return AddressDto.builder()
                .addressId(uuid)
                .addressLine1("hno xxx, near xxx")
                .pinCode("523523")
                .build();
    }

    public static Tenant getTenant(UUID uuid) {
        return modelMapper.map(getTenantDtoWithId(uuid), Tenant.class);
    }
}
