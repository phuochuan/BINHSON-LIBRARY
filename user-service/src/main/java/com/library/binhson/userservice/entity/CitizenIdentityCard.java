package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
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
    @Lob
    @Column(name = "faceId", columnDefinition="LONGBLOB")
    private byte[] faceId;
    @OneToOne(mappedBy ="citizenIdentityCard")
    private User user;

}
