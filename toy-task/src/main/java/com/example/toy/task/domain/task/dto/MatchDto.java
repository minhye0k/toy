package com.example.toy.task.domain.task.dto;

import com.example.toy.common.constant.MatchingType;
import lombok.Builder;

@Builder
public record MatchDto(Long seq, MatchingType matchingType) {
}
