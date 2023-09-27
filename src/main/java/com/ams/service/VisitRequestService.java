package com.ams.service;

import com.ams.model.VisitRequestDto;
import com.ams.model.VisitRequestStatus;

public interface VisitRequestService {

    VisitRequestStatus createVisitRequest(VisitRequestDto visitRequestDto) throws Exception;
}
