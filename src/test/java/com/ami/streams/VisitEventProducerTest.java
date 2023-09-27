package com.ami.streams;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.ami.dataprovider.VisitEventDataProvider;
import com.ams.streams.VisitEventProducer;
import com.ams.streams.event.VisitEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class VisitEventProducerTest {

    @Mock
    KafkaTemplate<String, VisitEvent> kafkaTemplate;

    @InjectMocks
    VisitEventProducer visitEventProducer;

    @Test
    public void testSendVisitEvents() throws Exception {
        CompletableFuture<SendResult<String, VisitEvent>> completableFuture = new CompletableFuture<>();
        completableFuture.completeExceptionally(new InterruptedException("Test send result"));
        when(kafkaTemplate.send(any(), any(),any()))
                .thenReturn(completableFuture);

        visitEventProducer.sendVisitEvents(VisitEventDataProvider
                .visitEvent("A301", UUID.randomUUID(), UUID.randomUUID()));
    }

}
