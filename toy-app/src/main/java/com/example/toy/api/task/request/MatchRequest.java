package com.example.toy.api.task.request;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.task.domain.task.dto.MatchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchRequest {
    private Long seq;
    private MatchingType matchingType;

    public MatchDto toMatchDto(){
        return MatchDto.builder()
                .seq(seq)
                .matchingType(matchingType)
                .build();
    }
}
