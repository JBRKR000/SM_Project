package pbs.edu.cooperative.model;

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
public class ApartmentStaircase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_staircase_id")
    private int apartmentStaircaseId;

    @OneToMany(mappedBy = "apartmentStaircase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flat> flatList = new ArrayList<>();
}
