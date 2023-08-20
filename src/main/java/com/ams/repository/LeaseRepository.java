package com.ams.repository;

import com.ams.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, UUID> {

    @Query(value = "select * from lease l where l.apartment_id=:apartmentId", nativeQuery = true)
    Collection<Lease> findALLLeasesByApartmentId(UUID apartmentId);

}
