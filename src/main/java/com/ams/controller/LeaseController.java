package com.ams.controller;

import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.service.LeaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lease")
@Slf4j
@Tag(name = "lease", description = "api to lease apartments in a building")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @Operation(summary = "creates lease for an apartment", description = "creates lease for an apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<LeaseDto> createLease(@Valid @RequestBody LeaseRequest request) throws Exception {
        LeaseDto leaseDto = leaseService.saveOrUpdate(request);
        if (ObjectUtils.isEmpty(leaseDto)) {
            log.info("Unable to create lease for the request: {}", request);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(leaseDto);
    }

}
