package com.library.binhson.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbUser")
public class User {

    @Id
    private String id;
    @OneToOne(mappedBy = "user")
    private Account account;
    private String firstname;
    private String lastname;
    private String phone;
    private String otherInfo;
}
