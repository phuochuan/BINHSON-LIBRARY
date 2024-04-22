package com.library.binhson.borrowingservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Data

@DiscriminatorValue("Librarian")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Librarian  extends Person {
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "person_id"))
    private List<String> roles;
    public Librarian(String id, String username, String roles) {
        super(id, username);
        this.roles = new ArrayList<>(Arrays.asList(roles));
    }
}
