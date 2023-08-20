package com.ams.repository;

import com.ams.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, UUID> {

    Optional<Visitor> findByGovtIssuedIdentifier(String govtIssuedIdentifier);

}
