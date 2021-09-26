package com.choimory.helloelasticsearch.board.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardFields {
    IDX("idx"),
    TITLE("title"),
    WRITER("writer"),
    CONTENT("content"),
    CATEGORY("category"),
    CREATED_TIME("created_time"),
    UPDATED_TIME("updated_time");

    private final String value;
}
