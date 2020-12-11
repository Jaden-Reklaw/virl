package com.astontech.virl.student.controllers;

import com.astontech.virl.student.domain.Mentee;
import com.astontech.virl.student.services.MenteeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;

@RestController
@RequestMapping("/api/mentee")
public class MenteeController {
    private static final Logger log = LoggerFactory.getLogger(MenteeController.class);

    //Inject service
    MenteeService menteeService;

    //New way of autowiring
    public MenteeController(MenteeService menteeService) {
        this.menteeService = menteeService;
    }

    //CRUD FOR REST
    //RETRIEVE
    @GetMapping("/")
    public List<Mentee> retrieveAllMentees() {
        return menteeService.findAllMentees();
    }

    @GetMapping("/{id}")
    public Mentee retrieveMenteeById(@PathVariable String id) {
        return menteeService.findMenteeById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Mentee> retrieveMenteeByName(@PathVariable String name) {
        //Adding Headers to the api call//they are hash maps with key value pairs
        HttpHeaders headers = new HttpHeaders();
        headers.add("Company", "Aston Technologies");
        headers.add("Business-Unit", "Software Development");

        Mentee searchedMentee = menteeService.findMenteeByName(name);
        if(searchedMentee == null) {
            log.info("Mentee " + name + " not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return ResponseEntity.ok().headers(headers).body(searchedMentee);
    }

    //CREATE
    @PostMapping("/")
    public ResponseEntity<Mentee> createMentee(@RequestBody Mentee mentee) {
        Mentee savedMentee = menteeService.saveMentee(mentee);
        if(savedMentee.getId() == null) {
            // HTTP STATUS CODE 422 //Controls the status code the api spits out
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(savedMentee);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMentee);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteMentee(@PathVariable String id) {
        menteeService.deleteMentee(id);
    }
}
