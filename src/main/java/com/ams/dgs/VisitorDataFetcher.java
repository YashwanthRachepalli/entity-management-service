package com.ams.dgs;

import com.ams.entity.Visitor;
import com.ams.model.VisitorDto;
import com.ams.repository.VisitorRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@DgsComponent
public class VisitorDataFetcher {

    @Autowired
    VisitorRepository visitorRepository;

    @Autowired
    ModelMapper modelMapper;

    @DgsQuery(field = "getVisitorById")
    public VisitorDto getVisitorById(String id) {
        Visitor visitor = visitorRepository.findById(UUID.fromString(id))
                .orElseThrow(RuntimeException::new);
        return modelMapper.map(visitor, VisitorDto.class);
    }

}
