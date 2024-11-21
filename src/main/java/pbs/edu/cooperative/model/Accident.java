package pbs.edu.cooperative.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name="Accident")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accident_id")
    private int accidentId;

    @Column(name="accident_date", nullable=false)
    private LocalDateTime accidentDate;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "is_resolved", nullable = false)
    private boolean isResolved;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    private Tenant tenant;
}
