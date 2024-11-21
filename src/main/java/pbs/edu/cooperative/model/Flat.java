package pbs.edu.cooperative.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Flat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flat_id")
    private int flatId;

    @Column(name = "flat_number", nullable = false)
    private int flatNumber;


    @Column(name = "surface", nullable = false)
    private float surface;

    @OneToOne(mappedBy = "flat")
    private Tenant tenant;

    @OneToOne(mappedBy = "flat")
    private Accident accident;

    @ManyToOne
    @JoinColumn(name = "apartment_staircase_id", nullable = false)
    private ApartmentStaircase apartmentStaircase;
}
