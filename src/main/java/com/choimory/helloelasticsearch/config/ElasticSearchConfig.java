package com.choimory.helloelasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ElasticSearchConfig {
    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;
    @Value("${spring.elasticsearch.rest.username}")
    private String username;
    @Value("${spring.elasticsearch.rest.password}")
    private String password;
    @Value("${spring.elasticsearch.rest.port}")
    private int port;


    // 1. ElasticsearchRestTemplate은 RestHighLevelClient를 감싼 객체이다.
    // 2. Spring Data Elasticsearch에서 사용하는 객체이다.
    // 3. 고로 Spring Data Elasticsearch가 아닌 RestHighlevelClient를 직접 사용할땐 사용되지 않는 객체이다.
    @Bean
    public ElasticsearchOperations elasticsearchOperations(){
        return new ElasticsearchRestTemplate(restHighLevelClient());
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return RestClients.create(ClientConfiguration.builder()
                .connectedTo(uris + ":" + port)
                .build())
                .rest();
    }

    @Bean
    @Profile("ssl")
    public ElasticsearchOperations elasticsearchOperationsSSL(){
        return new ElasticsearchRestTemplate(restHighLevelClientSSL());
    }

    //https, basic auth가 적용된 ES CLoud
    @Bean
    @Profile("ssl")
    public RestHighLevelClient restHighLevelClientSSL(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(uris, port, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });

        return new RestHighLevelClient(restClientBuilder);
    }

}
