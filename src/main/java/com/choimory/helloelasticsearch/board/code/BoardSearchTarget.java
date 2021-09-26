package com.choimory.helloelasticsearch.board.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardSearchTarget {
    TITLE("title"),
    CONTENT("content"),
    WRITER("writer"),
    TITLE_AND_CONTENT("titleAndContent");

    private final String value;
}
