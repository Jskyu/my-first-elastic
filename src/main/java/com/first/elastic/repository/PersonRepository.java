package com.first.elastic.repository;


import com.first.elastic.document.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface PersonRepository extends ElasticsearchRepository<Person, String> {
}
