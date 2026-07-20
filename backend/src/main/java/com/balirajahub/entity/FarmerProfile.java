package com.balirajahub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmer_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One farmer profile belongs to one user
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String village;

    private String taluka;

    private String district;

    private String state;

    private String pinCode;

    private Double farmSize;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToMany(
            mappedBy = "farmerProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Crop> crops = new ArrayList<>();
}