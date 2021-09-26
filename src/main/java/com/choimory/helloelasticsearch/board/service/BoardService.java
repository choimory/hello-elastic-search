package com.choimory.helloelasticsearch.board.service;

import com.choimory.helloelasticsearch.board.dto.request.BoardRequestDto;
import com.choimory.helloelasticsearch.board.dto.response.BoardResponseDto;
import com.choimory.helloelasticsearch.board.repository.BoardRepository;
import com.choimory.helloelasticsearch.common.annotation.ExecuteTimeLog;
import com.choimory.helloelasticsearch.common.util.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final RestHighLevelClient client;
    @Value("${spring.elasticsearch.rest.board-index}")
    private String boardIndex;

    @ExecuteTimeLog
    public BoardResponseDto getBoards(final BoardRequestDto param, Pageable pageable) throws IOException {
        BoolQueryBuilder boardBool = boardRepository.searchBoardsDynamically(null, param);
        SearchSourceBuilder searchSourceBuilder = ElasticSearchUtil.getSearchSourceBuilder(boardBool, pageable);
        Page<Map<String, Object>> result = ElasticSearchUtil.search(client, boardIndex, searchSourceBuilder, pageable);
        return null;
    }
}
