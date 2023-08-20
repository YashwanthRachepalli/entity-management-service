package com.ami.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.ApartmentDataProvider;
import com.ams.entity.Apartment;
import com.ams.repository.ApartmentRepository;
import com.ams.service.impl.ApartmentServiceImpl;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Test
    public void test() throws Exception {
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        when(apartmentRepository.findByApartmentName(any()))
                .thenReturn(Optional.of(ApartmentDataProvider
                        .getApartment("A301", apartmentId, buildingId)));

        Apartment apartment = apartmentService.getApartmentByName("A301");

        Truth.assertThat(apartment.getApartmentId()).isEqualTo(apartmentId);
        Truth.assertThat(apartment.getBuilding().getBuildingId()).isEqualTo(buildingId);
    }

    @Test
    public void testErrorScenario() throws Exception {
        when(apartmentRepository.findByApartmentName(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> apartmentService.getApartmentByName("A301"));
    }

    @Test
    public void testGetAllApartments() throws Exception {
        UUID apartmentId = UUID.randomUUID();
        UUID buildingId = UUID.randomUUID();
        when(apartmentRepository.getAllApartmentsByBuildingName(any()))
                .thenReturn(List.of(ApartmentDataProvider
                        .getApartment("A301", apartmentId, buildingId)));

        Collection<Apartment> apartments = apartmentService.findAllApartmentsByBuildingName("A301");

        Truth.assertThat(apartments.size()).isEqualTo(1);
    }

}
