package com.choimory.helloelasticsearch.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CommonResponseDto<T> {
    @Builder.Default
    private final Integer status = 200;
    @Builder.Default
    private final String message = "";
    private final T data;
}
