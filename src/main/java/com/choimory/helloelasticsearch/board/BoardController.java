package com.choimory.helloelasticsearch.board;

import com.choimory.helloelasticsearch.board.dto.request.BoardRequestDto;
import com.choimory.helloelasticsearch.board.service.BoardService;
import com.choimory.helloelasticsearch.common.dto.CommonResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class BoardController {
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<CommonResponseDto<?>> getBoards(final BoardRequestDto param, Pageable pageable) throws Exception{
        return ResponseEntity.ok(CommonResponseDto.builder()
                .data(boardService.getBoards(param, pageable))
                .build());
    }
}
