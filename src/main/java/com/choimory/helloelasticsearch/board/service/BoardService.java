package com.choimory.helloelasticsearch.board.service;

import com.choimory.helloelasticsearch.board.dto.request.BoardRequestDto;
import com.choimory.helloelasticsearch.board.dto.response.BoardResponseDto;
import com.choimory.helloelasticsearch.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto getBoards(final BoardRequestDto param, Pageable pageable){
        return null;
    }
}
