package com.choimory.helloelasticsearch.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElasticSearchUtil {
    @AllArgsConstructor
    @Getter
    public enum Analyzer{
        KEYWORD("keyword"),
        NORI("nori"),
        NGRAM("ngram");

        private final String value;
    }

    /**
     * 필드명+아날라이져 결합 문자열을 반납하는 메소드
     * @param fieldName - 필드명
     * @param analyzer - ES 아날라이져
     * @return - 필드명+아날라이져 문자여
     */
    public static String toField(String fieldName, @Nullable Analyzer analyzer){
        if(analyzer == null){
            return fieldName;
        }

        return String.format("%s.%s", fieldName, analyzer.getValue());
    }

    //7.10.2 이상에서만 SearchSourceBuilder에 List<SortBuilder>를 인자로 건내줄수 있음
    //고로 이하버전에서는 동적정렬이 다소 상이할 수 있다
    public static List<SortBuilder<?>> parseSort(Pageable pageable){
        List<SortBuilder<?>> sortBuilders = new ArrayList<>();

        pageable.getSort().forEach(order -> {
            SortOrder direction = StringUtils.pathEquals(order.getDirection().name(), "ASC") ? SortOrder.ASC : SortOrder.DESC;
            sortBuilders.add(SortBuilders.fieldSort(order.getProperty()).order(direction));
        });

        return sortBuilders;
    }

    /**
     *
     * @param queryBuilder -
     * @param pageable -
     * @return -
     */
    public static SearchSourceBuilder getSearchSourceBuilder(QueryBuilder queryBuilder, Pageable pageable){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if(queryBuilder != null){
            searchSourceBuilder.query(queryBuilder);
        }

        if(pageable != null){
            searchSourceBuilder.from((pageable.getPageNumber()-1) * pageable.getPageSize())
                    .size(pageable.getPageSize())
                    .sort(parseSort(pageable));
        }

        return searchSourceBuilder;
    }

    /**
     *
     * @param client -
     * @param index -
     * @param searchSourceBuilder -
     * @return -
     */
    public static Page<Map<String, Object>> search(RestHighLevelClient client, String index, SearchSourceBuilder searchSourceBuilder, Pageable pageable) throws IOException {
        SearchRequest request = new SearchRequest(index);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> resultList = new ArrayList<>();
        response.getHits().forEach(documentFields -> {
            resultList.add(documentFields.getSourceAsMap());
        });

        return new PageImpl<>(resultList, pageable, response.getHits().getTotalHits().value);
    }
}
