package com.choimory.helloelasticsearch.board.repository;

import com.choimory.helloelasticsearch.board.code.BoardFields;
import com.choimory.helloelasticsearch.board.code.BoardSearchTarget;
import com.choimory.helloelasticsearch.board.dto.request.BoardRequestDto;
import com.choimory.helloelasticsearch.common.util.ElasticSearchUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class BoardRepositoryImpl implements BoardRepository{
    @Override
    public BoolQueryBuilder searchBoardsDynamically(BoolQueryBuilder boolQueryBuilder, BoardRequestDto param) {
        if(boolQueryBuilder == null){
            boolQueryBuilder = new BoolQueryBuilder();
        }

        //제목 || 내용 || 글쓴이 || 제목+내용
        if(StringUtils.hasText(param.getQuery())) {
            if (StringUtils.pathEquals(param.getTarget(), BoardSearchTarget.TITLE.getValue())) {
                boolQueryBuilder.must(QueryBuilders.matchQuery(
                        ElasticSearchUtil.toField(BoardFields.TITLE.getValue(), ElasticSearchUtil.Analyzer.NORI),
                        param.getQuery()));
            } else if (StringUtils.pathEquals(param.getTarget(), BoardSearchTarget.CONTENT.getValue())) {
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(
                        ElasticSearchUtil.toField(BoardFields.CONTENT.getValue(), ElasticSearchUtil.Analyzer.NORI),
                        param.getQuery()));
            } else if (StringUtils.pathEquals(param.getTarget(), BoardSearchTarget.WRITER.getValue())) {
                boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery(
                        ElasticSearchUtil.toField(BoardFields.WRITER.getValue(), ElasticSearchUtil.Analyzer.KEYWORD),
                        param.getQuery()));
            } else if (StringUtils.pathEquals(param.getTarget(), BoardSearchTarget.TITLE_AND_CONTENT.getValue())) {
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(
                        ElasticSearchUtil.toField(BoardFields.TITLE.getValue(), ElasticSearchUtil.Analyzer.NORI),
                        param.getQuery()));
                boolQueryBuilder.must(QueryBuilders.matchQuery(
                        ElasticSearchUtil.toField(BoardFields.CONTENT.getValue(), ElasticSearchUtil.Analyzer.NORI),
                        param.getQuery()));
            }
        }

        //카테고리 복수선택
        if(!CollectionUtils.isEmpty(param.getCategories())){
            boolQueryBuilder.must(QueryBuilders.termsQuery(
                    ElasticSearchUtil.toField(BoardFields.CATEGORY.getValue(), null),
                    param.getCategories()));
        }

        return boolQueryBuilder;
    }
}
