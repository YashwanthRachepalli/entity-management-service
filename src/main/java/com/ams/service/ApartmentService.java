package com.ams.service;

import com.ams.entity.Apartment;

import java.util.Collection;
import java.util.Optional;

public interface ApartmentService {

    Apartment getApartmentByName(String name) throws Exception;

    Collection<Apartment> findAllApartmentsByBuildingName(String Name);

}
