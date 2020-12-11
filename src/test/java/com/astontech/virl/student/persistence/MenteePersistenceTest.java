package com.astontech.virl.student.persistence;

import com.astontech.virl.student.Application;
import com.astontech.virl.student.domain.Mentee;
import com.astontech.virl.student.repositories.MenteeRepository;
import com.astontech.virl.student.services.MenteeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MenteePersistenceTest {
    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private MenteeService menteeService;

    @Test
    public void testMenteeRepo() {
        //CREATE
        Mentee mentee = new Mentee();
        mentee.setName("Bob");
        mentee.setBu("Chef");
        mentee.setSite("Coney Island");
        mentee.setAssignedVirlInstance("vir101");
        Mentee saveMentee = menteeRepository.save(mentee);
        Assert.assertNotNull(saveMentee.getId());

        //RETRIEVE
        Mentee foundMentee = menteeRepository.findById(saveMentee.getId()).orElse(null);
        Assert.assertNotNull(foundMentee);

        //UPDATE
        foundMentee.setSite("VA");
        Mentee updatedMentee = menteeRepository.save(foundMentee);
        Assert.assertEquals("VA", updatedMentee.getSite());

        //Found By Name//Will fail unless you put @Indexed next to the object field name
        Mentee foundByName = menteeRepository.findByName("Bob");
        Assert.assertEquals("Bob", foundByName.getName());

        //DELETE
        String menteeToDelete = updatedMentee.getId();
        menteeRepository.deleteById(menteeToDelete);
        Assert.assertEquals(Optional.empty(), menteeRepository.findById(menteeToDelete));

        System.out.println(menteeRepository.findAll().toString());
    }

    @Test
    public void testMenteeServ() {
        Mentee mentee = new Mentee();
        mentee.setName("Linda");
        mentee.setBu("Server");
        mentee.setSite("Coney Island");
        mentee.setAssignedVirlInstance("vir102");

        menteeService.saveMentee(mentee);

        System.out.println(menteeService.findAllMentees());
    }
}
