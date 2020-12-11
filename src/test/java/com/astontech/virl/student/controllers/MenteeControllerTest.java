package com.astontech.virl.student.controllers;

import com.astontech.virl.student.Application;
import com.astontech.virl.student.domain.Mentee;
import com.astontech.virl.student.repositories.MenteeRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MenteeControllerTest {

    private String testName = "Mentee1";

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverter(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    //Runs before the test starts
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        menteeRepository.deleteAll();

        //Where to generate data
        generateTestMentees(7);
    }

    @Test
    public void readSingleByName() throws Exception {
        System.out.println("Testing Mentee By Name");
        //Test Get route for checking name
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/mentee/name/" + testName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(testName)));
    }

    @Test
    public void createMentee() throws Exception {
        //Create new Mentee
        Mentee bill = new Mentee();
        bill.setName("Bill Gates");
        bill.setSite("WA");
        bill.setBu("Microsoft");

        //Use helper method to convert to JSON
        String billJSON = convertObjIntoJSON(bill);

        //Test post route for creating a user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/mentee/").contentType(contentType).content(billJSON))
                        //Check if status code is 200
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        //check if content type is application/json
                        .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                        //fields to check
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Bill Gates")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.site", Matchers.is("WA")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.bu", Matchers.is("Microsoft")));
    }

    //Helper method to turn object into JSON format
    protected String convertObjIntoJSON(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(obj, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


    //Method to create some mentees
    public void generateTestMentees(int numberOfMentees) {
        IntStream.range(1, numberOfMentees).forEach(i -> {
            Mentee mentee = new Mentee();
            mentee.setName("Mentee" + i);
            mentee.setBu("Cisco");
            mentee.setSite((i % 2 == 0) ? "CA": "MN");
            menteeRepository.save(mentee);
        });
    }
}
