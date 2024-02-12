package com.example.toy.common.event.matching;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MatchingEvent {
    private Long taskSeq;
}
