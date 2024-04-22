package com.library.binhson.borrowingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("Member")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member extends Person {
    private String fullName;

    public Member(String id, String username, String fullName) {
        super(id, username);
        this.fullName = fullName;
    }

    public Member(String fullName) {
        this.fullName = fullName;
    }
}
