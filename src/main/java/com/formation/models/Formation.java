package com.formation.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.validation.constraints.Size;
import javax.validation.constraints.Future;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "formations")
@EntityListeners(AuditingEntityListener.class)
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100)
    private String titre;

    @NotNull(message = "Le niveau est obligatoire")
    @Enumerated(EnumType.STRING)
    private NiveauFormation niveau;

    private String prerequis;

    @Min(value = 1, message = "La capacité minimale doit être supérieure à 0")
    private int capaciteMin;

    @Min(value = 1, message = "La capacité maximale doit être supérieure à 0")
    private int capaciteMax;

    @NotNull(message = "La date de début est obligatoire")
    @Future
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    @Future
    private LocalDateTime dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formateur_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Formateur formateur;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "formation_apprenant", joinColumns = @JoinColumn(name = "formation_id"), inverseJoinColumns = @JoinColumn(name = "apprenant_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Apprenant> apprenants = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut est obligatoire")
    private FormationStatus statut;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
