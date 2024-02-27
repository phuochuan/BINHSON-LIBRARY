package com.library.binhson.documentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;

@Entity()
@Table(name = "tbPerson")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name =  "person_type", discriminatorType = DiscriminatorType.STRING)
public class Person {
    @Id
    private String id;
    private String username;
}
