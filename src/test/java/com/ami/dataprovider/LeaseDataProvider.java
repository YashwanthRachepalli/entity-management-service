package com.ami.dataprovider;

import com.ams.entity.Lease;
import com.ams.model.LeaseDto;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

public class LeaseDataProvider {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LeaseDto getLeaseDto() {
        return LeaseDto.builder()
                .startDate(LocalDate.of(2023, 8, 20))
                .endDate(LocalDate.of(2025, 8, 20))
                .rentalDate(LocalDate.of(2023, 9, 01))
                .securityDeposit(300000.00)
                .rentalFee(30000.00)
                .build();
    }

    public static LeaseDto getLeaseDtoWithId(UUID uuid) {
        return LeaseDto.builder()
                .leaseId(uuid)
                .startDate(LocalDate.of(2023, 8, 20))
                .endDate(LocalDate.of(2025, 8, 20))
                .rentalDate(LocalDate.of(2023, 9, 01))
                .securityDeposit(300000.00)
                .rentalFee(30000.00)
                .build();
    }

    public static Lease getLease(UUID uuid) {
        LeaseDto leaseDto = LeaseDto.builder()
                .leaseId(uuid)
                .startDate(LocalDate.of(2022, 8, 20))
                .endDate(LocalDate.of(2024, 8, 20))
                .rentalDate(LocalDate.of(2023, 9, 01))
                .securityDeposit(300000.00)
                .rentalFee(30000.00)
                .build();
        return modelMapper.map(leaseDto, Lease.class);
    }

    public static LeaseDto getLeaseDtoWithEmptySeurityDeposit() {
        return LeaseDto.builder()
                .startDate(LocalDate.of(2023, 8, 20))
                .endDate(LocalDate.of(2025, 8, 20))
                .rentalDate(LocalDate.of(2023, 9, 01))
                .rentalFee(30000.00)
                .build();
    }

}
