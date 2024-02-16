package com.library.binhson.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_citizen_identity_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenIdentityCard {
    @Id
    private String noDot;
    private String nationality;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String personalIdentification;
    @OneToOne(mappedBy ="citizenIdentityCard")
    private User user;

}
