package pbs.edu.cooperative.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Tenant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private int tenantId;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "is_backlog", nullable = false)
    private Boolean isBacklog = false;

    @Column(name = "tenants_number", nullable = false)
    private int tenantsNumber;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "water_consumption", nullable = false)
    private float waterConsumption;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flat_id", referencedColumnName = "flat_id", nullable = false)
    private Flat flat;

    @OneToOne(mappedBy = "tenant")
    private Invoice invoice;

    //To DO invoice list, flat number
}