package com.astontech.virl.student.controllers;

import com.astontech.virl.student.domain.Mentee;
import com.astontech.virl.student.services.MenteeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public Mentee retrieveMenteeByName(@PathVariable String name) {
        return menteeService.findMenteeByName(name);
    }

    //CREATE
    @PostMapping("/")
    public Mentee createMentee(@RequestBody Mentee mentee) {
        return menteeService.saveMentee(mentee);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteMentee(@PathVariable String id) {
        menteeService.deleteMentee(id);
    }
}
