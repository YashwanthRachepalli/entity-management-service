package com.ams.streams;

import com.ams.streams.event.ReplayAttributes;
import com.ams.streams.event.VisitEvent;
import com.ams.streams.model.RequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@ConditionalOnExpression("${kafka.enabled}")
public class ReplayEventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${kafka.max-replay-count}")
    private int maxReplayCount;

    public void replayMessageIfApplicable(VisitEvent visitEvent, String topic) {
        log.info("Replaying message: {}", visitEvent);
        try {
            if (ObjectUtils.isEmpty(visitEvent.getReplayAttributes())
                    || (visitEvent.getReplayAttributes().getReplayCount() < maxReplayCount)) {
                VisitEvent replayEvent = constructReplayMessage(visitEvent);
                kafkaTemplate.send(topic, visitEvent.getRequestId(), replayEvent);
            } else {
                Optional<VisitEvent> cancelEvent = constructCancellationEvent(visitEvent);
                if(cancelEvent.isPresent()) {
                    kafkaTemplate.send(topic, visitEvent.getRequestId(), cancelEvent.get());
                }
            }
        } catch (Exception e) {
            log.error("Error in replaying/cancelling the message: {}", e);
        }

    }

    private Optional<VisitEvent> constructCancellationEvent(VisitEvent visitEvent) {
        if (!ObjectUtils.isEmpty(visitEvent)) {
            return Optional.of(VisitEvent.builder()
                    .requestId(visitEvent.getRequestId())
                    .visitorId(visitEvent.getVisitorId())
                    .apartmentId(visitEvent.getApartmentId())
                    .requestStatus(RequestStatus.CANCELLED)
                    .build());
        }
        return Optional.empty();
    }

    private VisitEvent constructReplayMessage(VisitEvent visitEvent) {
        return VisitEvent.builder()
                .requestId(visitEvent.getRequestId())
                .requestStatus(visitEvent.getRequestStatus())
                .apartmentId(visitEvent.getApartmentId())
                .visitorId(visitEvent.getVisitorId())
                .replayAttributes(getUpdatedReplayAttributes(visitEvent.getReplayAttributes()))
                .build();
    }

    private ReplayAttributes getUpdatedReplayAttributes(ReplayAttributes replayAttributes) {
        if (ObjectUtils.isEmpty(replayAttributes)) {
            return ReplayAttributes.builder()
                    .replayCount(0)
                    .latestPublishedTimeStamp(LocalDateTime.now())
                    .build();
        } else {
            replayAttributes.setReplayCount(replayAttributes.getReplayCount() + 1);
            replayAttributes.setLatestPublishedTimeStamp(LocalDateTime.now());
            return replayAttributes;
        }
    }

}
