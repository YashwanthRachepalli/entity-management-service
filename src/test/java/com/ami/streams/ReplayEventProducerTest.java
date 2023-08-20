package com.ami.streams;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.VisitEventDataProvider;
import com.ams.streams.ReplayEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ReplayEventProducerTest {

    @Mock
    private KafkaTemplate kafkaTemplate;

    @InjectMocks
    private ReplayEventProducer replayEventProducer;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(replayEventProducer, "maxReplayCount", 2);
    }

    @Test
    public void testReplayMessageIfApplicable() {
        UUID visitorId = UUID.randomUUID();
        replayEventProducer.replayMessageIfApplicable(VisitEventDataProvider
                .visitEventWithMaxReplayCount("A302", visitorId, UUID.randomUUID()), "test");

        replayEventProducer.replayMessageIfApplicable(VisitEventDataProvider
                .visitEventWithReplayAttributes("A302", visitorId, UUID.randomUUID()), "test");

        when(kafkaTemplate.send(any(), any(), any())).thenThrow(RuntimeException.class);

        replayEventProducer.replayMessageIfApplicable(VisitEventDataProvider
                .visitEvent("A302", visitorId, UUID.randomUUID()), "test");
    }

}
