package com.balirajahub.entity;

import com.balirajahub.entity.enums.CropStatus;
import com.balirajahub.entity.enums.Season;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "crops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private LocalDate sowingDate;

    @Column(nullable = false)
    private LocalDate expectedHarvestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_profile_id", nullable = false)
    private FarmerProfile farmerProfile;
}