package com.jldata.smartframe.core.simple.controller;

import com.jldata.smartframe.core.common.RestResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonRestController {
    private static final List<Person> persons;

    static {
        persons = new ArrayList<>();
        persons.add(new Person("Hello", "World"));
        persons.add(new Person("Foo", "Bar"));
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public static RestResult getPersons() {
        return new RestResult(persons);
    }

    @RequestMapping(path = "/persons/{name}", method = RequestMethod.GET)
    public static RestResult getPerson(@PathVariable("name") String name) {
        return new RestResult(persons.stream()
                .filter(person -> name.equalsIgnoreCase(person.getName()))
                .findAny().orElse(null));
    }
}
