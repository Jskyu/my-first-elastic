package com.first.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.TransportOptions;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    public String host;

    @Value("${elasticsearch.port}")
    public String port;

    @Value("${elasticsearch.username}")
    public String username;

    @Value("${elasticsearch.password}")
    public String password;

    @Override
    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(host + ":" + port)
                .usingSsl()
//                .withProxy("http://proxy:port")
//                .withPathPrefix("data")
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(5))
//                .withDefaultHeaders(new HttpHeaders())
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//                    headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
//                    headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
                    return headers;
                })
//                .withClientConfigurer(ElasticsearchClients.ElasticsearchClientConfigurationCallback
//                        .from(clientBuilder -> {
//                            // 설정
//                            return clientBuilder;
//                }))
                .withBasicAuth(username, password)
                .build();
    }

    @Override
    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter, ElasticsearchClient elasticsearchClient) {
        ElasticsearchTemplate template = new ElasticsearchTemplate(elasticsearchClient, elasticsearchConverter);
        template.setRefreshPolicy(refreshPolicy());

        return template;
    }

    public ElasticSearchConfig() {
        super();
    }

    @Override
    @Bean
    public RestClient restClient(ClientConfiguration clientConfiguration) {
        return super.restClient(clientConfiguration);
    }

    @Override
    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        return super.elasticsearchClient(restClient);
    }

    @Override
    @Bean
    public TransportOptions transportOptions() {
        return super.transportOptions();
    }
}
