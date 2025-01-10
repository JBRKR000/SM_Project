package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Flat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"tenant", "accident"})  // Ignorowanie relacji z Tenant i Accident
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flat_id")
    private int flatId;

    @Column(name = "flat_number", nullable = false)
    private int flatNumber;

    @Column(name = "surface", nullable = false)
    private float surface;

    @OneToOne(mappedBy = "flat", cascade = CascadeType.ALL)
    private Tenant tenant;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Accident> accidents = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_staircase_id", nullable = false)
    private ApartmentStaircase apartmentStaircase;

}


