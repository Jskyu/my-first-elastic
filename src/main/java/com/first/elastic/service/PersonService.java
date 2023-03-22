package com.first.elastic.service;

import com.first.elastic.document.Person;
import com.first.elastic.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(final Person person) {
        return personRepository.save(person);
    }

    public Person findById(String id) throws NoSuchFieldException {
        return personRepository.findById(id).orElseThrow(() -> new NoSuchFieldException("Can Not Find Person, ID: " + id));
    }

    public List<Person> findAll() {
        Iterator<Person> iterator = personRepository.findAll().iterator();
        List<Person> list = new ArrayList<>();

        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
