package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(name = "accident_date", nullable = false, updatable = false)
    private LocalDateTime accidentDate;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "is_resolved", nullable = false)
    private boolean isResolved;

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    private Flat flat;
}
