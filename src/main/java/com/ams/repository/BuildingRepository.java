package com.ams.repository;

import com.ams.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {

}
