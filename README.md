# 개요

- Elastic search를 사용하는 목록 검색 API를 작성해보자

# 스택

- Java 11
- Spring boot 2.5.3
- ~~Spring Data Elastic search~~
- Spring MVC
- Spring Hateoas
- JUnit 5
- Spring REST Docs

# 다룰 내용

- Kibana
- JavaRestHighClient
  - Basic Auth
- SearchRequest
- SearchSource
- RestHighClient.search(), RestHighClient.multiSearch()
- BoolQuery - must, must_not, should, filter
- Match, MatchPhrase, MatchPhrasePrefix, MultiMatch, Wildcard
- Terms
- Range
- Nested
- Analyzer - ngram, nori, keyword
- Sort
