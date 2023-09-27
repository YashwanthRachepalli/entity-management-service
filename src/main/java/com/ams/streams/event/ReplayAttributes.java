package com.ams.streams.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplayAttributes {

    @Builder.Default
    private int replayCount = 0;

    @Builder.Default
    private LocalDateTime latestPublishedTimeStamp = LocalDateTime.MIN;

}
