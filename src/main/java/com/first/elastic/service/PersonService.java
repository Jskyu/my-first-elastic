package com.first.elastic.service;

import com.first.elastic.document.Person;
import com.first.elastic.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public void save(final Person person) {
        personRepository.save(person);
    }

    public Person findById(String id) throws NoSuchFieldException {
        return personRepository.findById(id).orElseThrow(() -> new NoSuchFieldException("Can Not Find Person, ID: " + id));
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
}
