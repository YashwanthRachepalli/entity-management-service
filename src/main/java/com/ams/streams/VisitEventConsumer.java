package com.ams.streams;

import com.ams.entity.Apartment;
import com.ams.service.ApartmentService;
import com.ams.service.LeaseService;
import com.ams.service.VisitorService;
import com.ams.streams.event.VisitEvent;
import com.ams.streams.model.RequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Component
@Slf4j
@ConditionalOnExpression("${kafka.enabled}")
public class VisitEventConsumer {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ReplayEventProducer replayEventProducer;

    @Value("${kafka.topic}")
    private String topic;

    @KafkaListener(
            id = "#{'${kafka.group-id}'}",
            topics = "#{'${kafka.topic}'}",
            groupId = "#{'${kafka.group-id}'}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(List<VisitEvent> messages) {
        log.info("In visit event consumer: {}", messages);

            messages.stream()
                    .filter(visitEvent -> !ObjectUtils.isEmpty(visitEvent.getRequestStatus())
                            && visitEvent.getRequestStatus().equals(RequestStatus.PENDING))
                    .forEach(visitEvent -> {
                try{
                    if(isExistingVisitor().negate().test(visitEvent.getVisitorId())) {
                        //Replay Event 3 times and cancel the request.
                        replayEventProducer.replayMessageIfApplicable(visitEvent, topic);
                        return;
                    }
                    Apartment apartment = apartmentService.getApartmentByName(visitEvent.getApartmentId());
                    if (leaseService.hasActiveLease(apartment.getApartmentId(), LocalDate.now())) {
                        //send message to tenant mobile number living in the apartment for acceptance.
                        log.info("Sending message to tenant for acceptance");
                    }
                } catch (Exception e) {
                    log.error("Error in consuming message: {}", e);
                    replayEventProducer.replayMessageIfApplicable(visitEvent, topic);
                }
            });

    }

    private Predicate<String> isExistingVisitor() {
        return visitorId -> StringUtils.hasLength(visitorId)
                && visitorService.getVisitorById(UUID.fromString(visitorId)).isPresent();
    }

}
