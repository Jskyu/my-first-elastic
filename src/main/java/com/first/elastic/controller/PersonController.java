package com.first.elastic.controller;

import com.first.elastic.document.Person;
import com.first.elastic.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public void save(@RequestBody final Person person) {
        personService.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final String id) throws NoSuchFieldException {
        return personService.findById(id);
    }

    @GetMapping
    public Iterable<Person> findAll(){
        return personService.findAll();
    }
}
