package com.astontech.virl.student.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("userprofile")
public class UserProfile {
    //region properties
    @Id
    private String id;

    @Indexed
    String username;

    String role;
    String landing;
    //endregion

    //region constructors
    public UserProfile() {}
    public UserProfile(String username, String role) {
        this.username = username;
        this.role = role;
    }
    //endregion

}
