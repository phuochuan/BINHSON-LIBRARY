package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbJsonWebToken")
public class JsonWebToken {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(length = 250)
    private String token;
    private Date publishedDate;
    private Date  expireDate;
    private Boolean isLocked;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
