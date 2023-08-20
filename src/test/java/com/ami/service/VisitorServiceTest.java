package com.ami.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.VisitorDataProvider;
import com.ams.model.VisitorDto;
import com.ams.repository.VisitorRepository;
import com.ams.service.impl.VisitorServiceImpl;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private VisitorServiceImpl visitorService;

    @Test
    public void testGetAllVisitors() {
        UUID visitorId = UUID.randomUUID();
        when(visitorRepository.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(List.of(VisitorDataProvider.getVisitor(visitorId))));

        List<VisitorDto> response = visitorService.getAllVistors(0, 1);

        Truth.assertThat(response.size()).isEqualTo(1);
    }

    @Test
    public void testCreateVisitor() {
        UUID visitorId = UUID.randomUUID();
        when(visitorRepository.findByGovtIssuedIdentifier(any()))
                .thenReturn(Optional.empty());

        when(visitorRepository.save(any())).thenReturn(VisitorDataProvider.getVisitor(visitorId));

        VisitorDto visitorDto = visitorService.createVisitor(VisitorDataProvider.getVisitorDto());

        Truth.assertThat(visitorDto.getVisitorId()).isEqualTo(visitorId);

    }

    @Test
    public void testUpdateExistingVisitor() {
        UUID visitorId = UUID.randomUUID();
        when(visitorRepository.findByGovtIssuedIdentifier(any()))
                .thenReturn(Optional.of(VisitorDataProvider.getVisitor(visitorId)));

        when(visitorRepository.save(any())).thenReturn(VisitorDataProvider.getVisitor(visitorId));

        VisitorDto visitorDto = visitorService.createVisitor(VisitorDataProvider.getVisitorDto());

        Truth.assertThat(visitorDto.getVisitorId()).isEqualTo(visitorId);

    }
}
