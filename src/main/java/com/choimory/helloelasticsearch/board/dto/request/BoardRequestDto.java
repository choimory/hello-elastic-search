package com.choimory.helloelasticsearch.board.dto.request;

import com.choimory.helloelasticsearch.board.code.BoardSearchTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String target;
    private String query;

    private Long idx;
    private List<String> categories;
    private Long thumbsMin;
    private Long thumbsMax;
    private LocalDate createdTimeFrom;
    private LocalDate createdTimeTo;
    private LocalDate updatedTimeFrom;
    private LocalDate updatedTimeTo;
}
