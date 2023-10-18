package com.library.binhson.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "oauth2_client")
public class ClientDetails {
    @Id
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
}
