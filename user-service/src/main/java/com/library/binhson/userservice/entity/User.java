package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "tbUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    @Lob
    @Column(name = "avatar", columnDefinition="LONGBLOB")
    private byte[] avatar;
    private String address;
    private Boolean isNonLocked;
    private String phone;
    private String email;
    private String lastname;
    private String firstname;
    private String biography;
    private Date dateOfBirth;
    private Date dateOfAccountSignUp;
    private String password;
    @OneToOne
    @JoinColumn(name = "citizen_identity_id",referencedColumnName = "noDot")
    private CitizenIdentityCard citizenIdentityCard;


}
