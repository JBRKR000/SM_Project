package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meter_reading")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private int readingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "reading_date", nullable = false)
    private LocalDate readingDate;

    @Column(name = "meter_reading", nullable = false)
    private float meterReading;

    @OneToMany(mappedBy = "meterReading")
    @JsonManagedReference("meterReading-waterConsumptionLogs")
    private List<WaterConsumptionLog> waterConsumptionLogs;
}