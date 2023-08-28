package com.ams.controller;

import com.ams.model.VisitorDto;
import com.ams.service.VisitorService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/visitors")
@Tag(name = "visitor", description = "api to manage visitors")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Operation(
            summary = "fetches visitors by page and size",
            description = "fetches visitors by page and size"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public ResponseEntity<List<VisitorDto>> getAllVisitors(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching visitors for page: {} and size: {}", page, size);
        List<VisitorDto> visitorDtoList = visitorService.getAllVistors(page,size);

        log.info("Visitors: {}", visitorDtoList);

        return ResponseEntity.ok(visitorDtoList);
    }

    @Operation(
            summary = "adds visitor to apartment management system",
            description = "adds visitor to apartment management system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<VisitorDto> saveOrUpdateVisitor(@Valid @RequestBody VisitorDto visitorDto) {
        log.info("create visitor: {}", visitorDto);
        VisitorDto response = visitorService.createVisitor(visitorDto);

        if(ObjectUtils.isEmpty(response)) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }

}
