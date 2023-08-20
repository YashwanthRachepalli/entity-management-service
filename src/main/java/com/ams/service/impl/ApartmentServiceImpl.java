package com.ams.service.impl;

import com.ams.entity.Apartment;
import com.ams.repository.ApartmentRepository;
import com.ams.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public Apartment getApartmentByName(String name) throws Exception {
        return apartmentRepository.findByApartmentName(name)
                .orElseThrow(() -> new Exception("Apartment not found!"));
    }

    @Override
    public Collection<Apartment> findAllApartmentsByBuildingName(String name) {
        return apartmentRepository.getAllApartmentsByBuildingName(name);
    }
}
