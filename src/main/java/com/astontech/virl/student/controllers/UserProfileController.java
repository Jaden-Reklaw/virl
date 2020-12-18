package com.astontech.virl.student.controllers;

import com.astontech.virl.student.domain.UserProfile;
import com.astontech.virl.student.services.UserProfileService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.astontech.virl.student.configuration.CustomSuccessHandler.removeEmailSignature;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    //Bring in logger for logging issues
    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    //region Inject service
    UserProfileService userProfileService;
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
    //endregion

    //region Endpoints for GET and POST
    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getAllProfiles(@PathVariable String username) {
        return profileSearch(username);
    }

    @GetMapping("/")
    public ResponseEntity<UserProfile> getSessionProfile(HttpSession session) {
        if(session.getAttribute("username") != null) {
            String username = removeEmailSignature(session.getAttribute("username").toString());
            if(username == null) {
                logger.error("Error getting username from Session.");
                return ResponseEntity.status(403).body(null);
            } else {
                return profileSearch(username);
            }
        } else {
            logger.error("Error no Session!");
            return ResponseEntity.status(420).body(null);
        }
    }

    public ResponseEntity<UserProfile> profileSearch(String username) {
        //retrieve profile from DB
        Optional<UserProfile> searchedProfile = userProfileService.findByUsername(username);
        //what to do if profile does not exist
        if(!searchedProfile.isPresent()) {
            //log issue to logger file
            logger.info("User Profile not found" + username);
            //information to send back to the like using a status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //what to do if profile does exist
        return ResponseEntity.ok().body(searchedProfile.get());
    }

    @PostMapping("/")
    public ResponseEntity<UserProfile> saveUserProfile(@RequestBody UserProfile userProfile) {
        //Save the Users profile to the database
        UserProfile savedProfile = userProfileService.saveProfile(userProfile);

        //what to do if you did not successfully save the profile
        if(savedProfile.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(userProfile);
        }

        //what to do if you did save user profile
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
    }
    //endregion
}
