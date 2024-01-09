package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfirmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String token;
    private Boolean isNonExpired;
    private Date generativeDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
