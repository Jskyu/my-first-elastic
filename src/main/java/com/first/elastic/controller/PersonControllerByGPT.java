package com.first.elastic.controller;

import com.first.elastic.document.Person;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/person/gpt")
@RequiredArgsConstructor
public class PersonControllerByGPT {

    private final RestClient restClientByGPT;
    private final String INDEX = "person";


    @GetMapping("/")
    public String findAll_GPT() throws Exception {
        Response response = requestPost_GPT("{\"query\":{\"match_all\":{}}}");

        return EntityUtils.toString(response.getEntity());
    }

    @GetMapping("/{id}")
    public String findById_GPT(@PathVariable String id) throws Exception {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.existsQuery(id));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        Response response = requestPost_GPT(searchSourceBuilder.toString());

        return EntityUtils.toString(response.getEntity());
    }

    @PostMapping("")
    public String save_GPT(@RequestBody Person person) throws Exception{
        String json = person.toJsonString();
        HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

        Request request = new Request(
                "POST",
                "/" + INDEX + "/_doc"
        );

        request.setEntity(entity);

        Response response = restClientByGPT.performRequest(request);

        return "Document saved with id: " + EntityUtils.toString(response.getEntity());
    }

    private Response requestPost_GPT(String searchSourceBuilder) throws IOException {
        Request request = new Request(
                "POST",
                "/" + INDEX + "/_search"
        );

        request.setJsonEntity(searchSourceBuilder);
        return restClientByGPT.performRequest(request);
    }
}
