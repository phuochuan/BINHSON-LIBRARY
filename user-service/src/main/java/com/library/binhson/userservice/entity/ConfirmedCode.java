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
@Table(name = "tbConfirmedCode")
public class ConfirmedCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Date publishDate;
    private Date expireDate;
    private  boolean isLocked;
    private CodeType type;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
