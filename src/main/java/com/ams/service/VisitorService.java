package com.ams.service;

import com.ams.entity.Visitor;
import com.ams.model.VisitorDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitorService {

    List<VisitorDto> getAllVistors(int page, int size);

    VisitorDto createVisitor(VisitorDto visitorDto);

    Optional<Visitor> getVisitorById(UUID id);

}
