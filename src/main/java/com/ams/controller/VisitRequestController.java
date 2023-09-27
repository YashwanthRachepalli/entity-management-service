package com.ams.controller;

import com.ams.model.VisitRequestDto;
import com.ams.model.VisitRequestStatus;
import com.ams.service.VisitRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/visit-requests")
@Tag(name = "visitor", description = "api to manage visit requests")
public class VisitRequestController {

    @Autowired
    private VisitRequestService visitRequestService;

    @Operation(
            summary = "creates visit requests",
            description = "creates visit requests"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<VisitRequestStatus> createVisitRequest(@Valid @RequestBody VisitRequestDto request)
            throws Exception {
        log.info("create visit-request: {}", request);
        VisitRequestStatus response = visitRequestService.createVisitRequest(request);

        if(ObjectUtils.isEmpty(response)) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }

}
