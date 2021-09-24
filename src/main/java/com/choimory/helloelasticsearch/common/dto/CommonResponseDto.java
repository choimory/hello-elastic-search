package com.choimory.helloelasticsearch.common.dto;

import com.choimory.helloelasticsearch.common.code.CommonResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CommonResponseDto<T> {
    @Builder.Default
    private final Integer status = CommonResponseCode.OK.getCode();
    @Builder.Default
    private final String message = CommonResponseCode.OK.getMessage();
    private final T data;
}
