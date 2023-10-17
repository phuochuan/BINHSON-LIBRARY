package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbAuthorization")
public class Authorization {
    @EmbeddedId
    private AuthorizationPrimaryKey primaryKey;
    @OneToOne
    @MapsId("accountId")
    private Account account;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Permission> permissions;

    public List<? extends GrantedAuthority> getAuthority(){
        List<SimpleGrantedAuthority> authorities= new ArrayList<>(this.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(this.role.name() + "_" + permission.name())).toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        return authorities;
    }

}
