package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pbs.edu.cooperative.User.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Tenant")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"flat", "user", "invoice"})
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

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("tenant-waterConsumptionLogs")
    private List<WaterConsumptionLog> waterConsumptionLogs = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flat_id", referencedColumnName = "flat_id", nullable = false)
    @JsonManagedReference
    private Flat flat;

    @OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;


    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();
}

