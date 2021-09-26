package com.choimory.helloelasticsearch.board.repository;

import com.choimory.helloelasticsearch.board.dto.request.BoardRequestDto;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.lang.Nullable;

public interface BoardRepository {
    BoolQueryBuilder searchBoardsDynamically(@Nullable BoolQueryBuilder boolQueryBuilder, BoardRequestDto param);
}
