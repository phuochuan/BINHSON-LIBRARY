package com.library.binhson.borrowingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity()
@Table(name = "tbPerson")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name =  "person_type", discriminatorType = DiscriminatorType.STRING)
public class Person {
    @Id
    private String id;
    private String username;
}
