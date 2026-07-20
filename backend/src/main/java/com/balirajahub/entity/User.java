package com.balirajahub.entity;


import com.balirajahub.entity.enums.Language;
import com.balirajahub.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //personal info
    private String firstName;
    private String lastName;
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    //Enums
    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Role role;

    //Account Status
    private boolean enabled;
    private boolean emailVerified;

    //Audit field
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
