package com.ams.service.impl;

import com.ams.entity.Visitor;
import com.ams.model.VisitorDto;
import com.ams.repository.VisitorRepository;
import com.ams.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<VisitorDto> getAllVistors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Visitor> visitors =  visitorRepository.findAll(pageable);
        List<VisitorDto> visitorList = visitors.get()
                .map(visitor -> modelMapper.map(visitor, VisitorDto.class))
                .collect(Collectors.toList());

        return visitorList;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public VisitorDto createVisitor(VisitorDto visitorDto) {
        Optional<Visitor> existingVisitor =
                visitorRepository.findByGovtIssuedIdentifier(visitorDto.getGovtIssuedIdentifier());
        if (existingVisitor.isEmpty()) {
            return modelMapper.map(
                    visitorRepository.save(modelMapper.map(visitorDto, Visitor.class)),
                    VisitorDto.class);
        }

        Visitor updatedVisitor = modelMapper.map(visitorDto, Visitor.class);
        updatedVisitor.setVisitorId(existingVisitor.get().getVisitorId());

        return modelMapper.map(visitorRepository.save(updatedVisitor), VisitorDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Visitor> getVisitorById(UUID id){
        return visitorRepository.findById(id);
    }

}
