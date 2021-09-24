package com.choimory.helloelasticsearch.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonResponseCode {
    CONTINUE(100, "")

    , OK(200, "정상")
    , CREATED(201, "등록되었습니다")
    , NO_CONTENT(204, "값이 없습니다")

    , BAD_REQUEST(400, "잘못된 요청입니다")
    , UNAUTHORIZED(401, "인증이 필요합니다")
    , FORBIDDEN(403, "권한이 없습니다")
    , NOT_FOUND(404, "경로를 찾지 못함")
    , NOT_ALLOWED(405, "해당 메소드로는 요청할 수 없습니다")
    , REQUEST_TIMEOUT(408, "요청시간 초과")
    , TOO_MANY_REQUESTS(429, "시간당 요청제한 초과")

    , INTERNAL_SERVER_ERROR(500, "서버 오류")
    , BAD_GATEWAY (502, "");

    private final Integer code;
    private final String message;
}
