package com.ams.streams;

import com.ams.model.VisitRequestStatus;
import com.ams.streams.event.VisitEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class VisitEventProducer {

    @Autowired
    private KafkaTemplate<String, VisitEvent> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public VisitRequestStatus sendVisitEvents(VisitEvent visitEvent) throws Exception {
        String requestId = UUID.randomUUID().toString();
        visitEvent.setRequestId(requestId);
        kafkaTemplate.send(topic, requestId, visitEvent);
        return VisitRequestStatus.builder()
                .requestStatus(visitEvent.getRequestStatus().name())
                .requestId(requestId)
                .build();
    }
}
