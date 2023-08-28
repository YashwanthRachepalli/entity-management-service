package com.ami.dataprovider;

import com.ams.entity.Visitor;
import com.ams.model.VisitorDto;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class VisitorDataProvider {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static VisitorDto getVisitorDto() {
        return VisitorDto.builder()
                .firstName("test_first_name")
                .govtIssuedIdentifier("test_identifier")
                .mobileNumber("9876543210")
                .build();
    }

    public static VisitorDto getVisitorWithId(UUID id) {
        return VisitorDto.builder()
                .visitorId(id)
                .firstName("test_first_name")
                .govtIssuedIdentifier("test_identifier")
                .mobileNumber("9876543210")
                .build();
    }

    public static Visitor getVisitor(UUID id) {

        return modelMapper.map(getVisitorWithId(id), Visitor.class);
    }

}
