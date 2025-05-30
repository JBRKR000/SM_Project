package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Apartment_Staircase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"flatList"})  // Ignorowanie relacji z Block
public class ApartmentStaircase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_staircase_id")
    private int apartmentStaircaseId;

    @Column(name = "shared_surface")
    private float sharedSurface;

    @Column(name = "staircase_number")
    private int staircaseNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)  // Zmieniamy CascadeType na PERSIST
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    @OneToMany(mappedBy = "apartmentStaircase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Flat> flatList = new ArrayList<>();

}

