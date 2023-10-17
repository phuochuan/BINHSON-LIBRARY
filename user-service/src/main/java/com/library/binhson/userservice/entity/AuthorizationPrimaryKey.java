package com.library.binhson.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AuthorizationPrimaryKey implements Serializable {
    @Column(name = "iCode")
    public String iCode;
    @Column(name = "account_id")
    public String accountId;
}
