package com.ams.repository;

import com.ams.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {

    Optional<Apartment> findByApartmentName(String name);

    @Query(value = "select a.apartment_id, a.apartment_size, a.apartment_name, a.apartment_type from " +
            "apartment a inner join building b on a.building_id = b.building_id " +
            "where b.building_name=:buildingName", nativeQuery = true)
    Collection<Apartment> getAllApartmentsByBuildingName(String buildingName);
}
