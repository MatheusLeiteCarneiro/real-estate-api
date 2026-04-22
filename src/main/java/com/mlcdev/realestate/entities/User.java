package com.mlcdev.realestate.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.jspecify.annotations.NonNull;


import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder

@Getter
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    @Builder.Default
    private Set<Role> authorities = new HashSet<>();

    public void changeUsername(String newUsername){
        this.username= newUsername;
    }

    public void changePassword(String encodedPassword){
        this.password= encodedPassword;
    }

    public void addRole(Role role){
        this.authorities.add(role);
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
