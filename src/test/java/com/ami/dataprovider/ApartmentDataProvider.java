package com.ami.dataprovider;

import com.ams.entity.Apartment;
import com.ams.entity.Building;
import com.ams.model.ApartmentType;

import java.util.UUID;

public class ApartmentDataProvider {

    public static Apartment getApartment(String name, UUID apartmentId, UUID buildingId) {
        return Apartment.builder()
                .apartmentId(apartmentId)
                .apartmentName(name)
                .apartmentType(ApartmentType.FOUR_BHK)
                .size(1800.00)
                .building(getBuilding(buildingId))
                .build();
    }

    public static Building getBuilding(UUID buildingId) {
        return Building.builder()
                .buildingId(buildingId)
                .address("hno xxx, near xxx")
                .pinCode("523523")
                .name("test building name")
                .build();
    }

}
